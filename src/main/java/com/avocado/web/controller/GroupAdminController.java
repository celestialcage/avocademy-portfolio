package com.avocado.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		System.out.println(dto.getPrg_cd());
		System.out.println(dto.getPrg_nmtm());
		System.out.println(dto.getPrg_start());
		System.out.println(dto.getPrg_nm());
		System.out.println(dto.getPrg_content());
		return "redirect:/admin/registProgram";
	}
	
}
