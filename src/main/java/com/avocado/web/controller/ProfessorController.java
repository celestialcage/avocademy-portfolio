package com.avocado.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.avocado.web.service.ProfessorService;

import jakarta.annotation.Resource;

@Controller
public class ProfessorController {
	@Resource (name = "professorService")
	private ProfessorService professorService;
	
	@GetMapping("/professor")
	public String professor() {
		
		return "program/professor";
	}
	
	@GetMapping("/professorCSR")
	public String professorCSR() {
		return "program/professorCSR";
	}
}
