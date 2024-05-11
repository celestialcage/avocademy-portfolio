package com.avocado.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.ProfessorDTO;
import com.avocado.web.service.ProfessorService;

import jakarta.annotation.Resource;

@Controller
public class ProfessorController {
	@Resource(name = "professorService")
	private ProfessorService professorService;

	@GetMapping("/professor") // 지도교수상담안내
	public String professor() {

		return "program/professor";
	}

	@GetMapping("/professorCSR") // 지도교수 상담 신청 시 학생 개인정보(이름, 학번, 전화번호) 가져오기
	public String professorCSR(Model model) {
		List<ProfessorDTO> list = professorService.studentInfo();
		model.addAttribute("list", list);
		return "program/professorCSR";
	}

	@GetMapping("/test1") // 지도교수 상담 신청 시 학생 개인정보(이름, 학번, 전화번호) 가져오기
	public String professorCSR1(Model model) {
		List<ProfessorDTO> list = professorService.studentInfo();
		model.addAttribute("list", list);
		return "program/test1";
	}

	@PostMapping("/professorSCR")
	public String savePs(@RequestParam("studentNumber") String studentNumber,
			@RequestParam("radio-stacked") String value, @RequestParam("content") String content) {
		System.out.println(studentNumber + value + content);
		Map<String, String> map = new HashMap<String, String>();
		map.put("studentNumber", studentNumber);
		map.put("value", value);
		map.put("content", content);
		int result = professorService.savePs(map);
		return "redirect:/";
	}

	@PostMapping("/test1")
	public String savePs1(@RequestParam("studentNumber") String studentNumber,
			@RequestParam("radio-stacked") String value, @RequestParam("content") String content) {
		System.out.println(studentNumber + value + content);
		Map<String, String> map = new HashMap<String, String>();
		map.put("studentNumber", studentNumber);
		map.put("value", value);
		map.put("content", content);
		int result = professorService.savePs(map);
		return "redirect:/";
	}
}
