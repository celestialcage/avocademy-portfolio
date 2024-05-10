package com.avocado.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.avocado.web.entity.TestDTO;
import com.avocado.web.service.TestService;

import jakarta.annotation.Resource;

@Controller
public class TestController {

	@Resource(name="testService")
	private TestService testService;
	
	@GetMapping("/test")
	public String test(Model model) {
		List<TestDTO> list = testService.staff();
		model.addAttribute("list", list);
		return "index";
	}
}
