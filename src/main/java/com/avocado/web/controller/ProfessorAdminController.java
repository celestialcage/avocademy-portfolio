package com.avocado.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avocado.web.entity.ProfessorDTO;
import com.avocado.web.service.ProfessorService;
import com.avocado.web.util.Util;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class ProfessorAdminController {
	@Resource(name = "professorService")
	private ProfessorService professorService;
	
	@Autowired
	private Util util;
	
	// 교수 관리자페이지 메인
	@GetMapping ("/professorAdmin")
	private String professorAdmin() {
		
		
		return "/admin/professor/professorAdmin";
	}
	
	// 지도교수일정등록 들어갔을때 교수 정보 가져오기
	@GetMapping("/registPsCounsel")
	private String registPsCounsel(Model model) {
		
		List<ProfessorDTO> list = new ArrayList<ProfessorDTO>();
		HttpSession session = util.getSession();
		String uname = (String)(session.getAttribute("uname"));
		list = professorService.professorInfo(uname);
		model.addAttribute("list", list);
		
		return "/admin/professor/registPsCounsel";
	}
}
