package com.avocado.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.GroupDTO;
import com.avocado.web.service.GroupService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/group")
public class GroupController {
	
	@Resource(name="groupService")
	private GroupService groupService;
	
	//프로그램 확인 페이지 (학생)
	@GetMapping("")
	public String groupMain() {
		return "program/group";
	}
	
	@GetMapping("/programList")
	public String programList(Model model) {		
		List<GroupDTO> list = groupService.programList();
		model.addAttribute("list", list);
		return "program/programList";
	}
	
	//프로그램 신청(학생)
	@GetMapping("/programApply")
	public String groupApply(@RequestParam("no") String no) {
		
		return "program/programApply";
	}
	
	
}
