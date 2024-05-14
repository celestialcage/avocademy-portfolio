package com.avocado.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.GroupDTO;
import com.avocado.web.service.GroupService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/admin")
public class GroupAdminController {

	@Resource(name="groupService")
	private GroupService groupService;
	
	
	//프로그램 관리 페이지 (상담사)
	@GetMapping("/groupAdmin")
	private String groupAdmin() {		
		return "admin/group/groupAdmin";
	}
	
	
	//프로그램 등록 페이지(상담사)
	@GetMapping("/registProgram")
	private String registProgram(Model model, GroupDTO groupDto) {
		model.addAttribute("groupDto", groupDto);
		return "admin/group/registProgram";
	}
	
	@PostMapping("/registProgram")
	private String registProgram(@ModelAttribute("groupDto") GroupDTO dto) {
		//System.out.println("제목 : " + dto.getPrg_nm());
		//System.out.println("분류 : " + dto.getPrg_cd());
		dto.setPrg_schdl(dto.getPrg_start() + " - " + dto.getPrg_end());
		dto.setCns_no(1);
		dto.setPrg_place("201호");
		groupService.registProgram(dto);
		
		//dto.setGroupSCHDL(null)

		return "redirect:/admin/registProgram";
	}
	
	
	//등록된 프로그램 확인
	@GetMapping("/programList")
	private String programList(Model model) {
		List<GroupDTO> list = groupService.adminPRGList();
		model.addAttribute("list", list);
		return "admin/group/programList";
	}
	
	//승인
	@RequestMapping(value = "/approveProgram", method = {RequestMethod.POST})
	private String approveProgram(Model model, @RequestParam Map<String, Object> map) {
		System.out.println(map.get("no"));
		System.out.println(map.get("val"));
				
		int prg_no = Integer.parseInt((String) map.get("no"));
		String approv = (String) map.get("val");
		
		if(approv.equals("0")) {
			System.out.println("승인하기");
			groupService.approvePRG(prg_no);
		} else if (approv.equals("1")) {
			System.out.println("승인취소하기");
			groupService.disApprovePRG(prg_no);			
		} else {
			System.out.println("땡");
		}
		
		GroupDTO dto = groupService.updateprg(prg_no);
		
		model.addAttribute("list", dto);
		return "/admin/group/programList :: #prgTable";
	}
	
}
