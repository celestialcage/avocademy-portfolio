package com.avocado.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CounselController {
	
	@GetMapping("/personal")
	public String personal() {
		return "program/personal";
	}
	
	@GetMapping("/personal/apply")
	public String personalApply() {
		return "program/personalApply";
	}
	
	@GetMapping("/test")
	public String test() {
		return "program/testPage";
	}
}
