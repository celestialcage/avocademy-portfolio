package com.avocado.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CounselController {
	
	@GetMapping("/personal")
	public String personal() {
		return "program/personal";
	}
	
	@GetMapping("/testProgram")
	public String test() {
		return "program/test";
	}
}
