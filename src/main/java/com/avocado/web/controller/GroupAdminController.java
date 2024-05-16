package com.avocado.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.avocado.web.util.Util;
import com.google.gson.JsonArray;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class GroupAdminController {

	@Resource(name="groupService")
	private GroupService groupService;
	
	@Autowired
	private Util util;
	
	
	//프로그램 관리 페이지 (상담사)
	@GetMapping("/groupAdmin")
	private String groupAdmin() {		
		return "admin/group/groupAdmin";
	}
	
	
	//프로그램 등록 페이지(상담사)
	@GetMapping("/registProgram")
	private String registProgram(Model model, GroupDTO groupDto, HttpSession session) {
		//System.out.println(session.getAttribute("cns_no"));
		groupDto.setCns_no((String) session.getAttribute("cns_no"));
		model.addAttribute("groupDto", groupDto);
		return "admin/group/registProgram";
	}
	
	@PostMapping("/registProgram")
	private String registProgram(@ModelAttribute("groupDto") GroupDTO dto) {
		
		//프로그램 진행기간
		dto.setPrg_schdl(dto.getGroupSCHDL().get(0) + " - " + dto.getGroupSCHDL().get(dto.getPrg_nmtm() -1));
		dto.setReq_end(dto.getGroupSCHDL().get(0));
		
		//프로그램 등록
		groupService.registProgram(dto);
		
		//상담사번호 기준 가장 최근 업로드 데이터 찾기
		int prgNO = groupService.getProgramNo(dto.getCns_no());
		
		//스케줄 업데이트
		dto.setPrg_no(prgNO);
		
		for(int i = 0; i < dto.getPrg_nmtm(); i++) {
			dto.setPrg_ymd(dto.getGroupSCHDL().get(i));
			groupService.createSchedule(dto);
		}		
		
		//groupService.createSchedule(prg_no);

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
			groupService.openPRG(prg_no);
			
		} else if (approv.equals("1")) {
			System.out.println("승인취소하기");
			groupService.disApprovePRG(prg_no);			
		} else {
			System.out.println("땡");
		}
		
		model.addAttribute("list", groupService.adminPRGList());

		return "admin/group/programList :: #prgList";
	}
	
}
