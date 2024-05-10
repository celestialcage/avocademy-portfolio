package com.avocado.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.avocado.web.entity.GroupDTO;
import com.avocado.web.service.GroupService;

import jakarta.annotation.Resource;

@Controller
public class GroupController {
	
	@Resource(name="groupService")
	private GroupService groupService;
	
	//프로그램 확인 페이지 (학생)
	@GetMapping("/group")
	public String groupMain(Model model) {
		List<GroupDTO> list = groupService.programList();
		model.addAttribute("list", list);
		return "program/group";
	}
	
	//프로그램 신청(학생)
	@GetMapping("/groupApply")
	public String groupApply() {
		
		return "program/groupApply";
	}
	
	
}
