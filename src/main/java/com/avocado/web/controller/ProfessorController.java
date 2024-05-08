package com.avocado.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.avocado.web.service.ProfessorService;

import jakarta.annotation.Resource;

@Controller
public class ProfessorController {
	@Resource (name = "professorService")
	private ProfessorService professorService;
	
	@GetMapping("/professor") // 지도교수상담
	public String professor() {
		
		return "program/professor";
	}
	
	@GetMapping("/professorCSR") // 지도교수상담신청
	public String professorCSR() {
		return "program/professorCSR";
	}
}
