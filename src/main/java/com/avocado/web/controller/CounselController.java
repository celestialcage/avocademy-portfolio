package com.avocado.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.avocado.web.service.CounselService;

import jakarta.annotation.Resource;

@Controller
public class CounselController {
	
	@Resource(name = "counselService")
	private CounselService counselService;
	
	@GetMapping("/personal")
	public String personal() {
		return "program/personal";
	}
	
	@GetMapping("/personal/apply")
	public String personalApply(Model model) {
		
		List<Map<String, Object>> cslList = counselService.findAllCounselors();
		model.addAttribute("counselors", cslList);
		return "program/personalApply";
	}
	
	@GetMapping("/test")
	public String test() {
		return "program/testPage";
	}
}
