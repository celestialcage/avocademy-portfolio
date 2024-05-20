package com.avocado.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	// 지도교수일정등록 들어갔을때 교수 정보 및 스케쥴 가져오기
	@GetMapping("/registPsCounsel")
	private String registPsCounsel(Model model) {
		
		List<ProfessorDTO> list = new ArrayList<ProfessorDTO>();
		HttpSession session = util.getSession();
		String uname = (String)(session.getAttribute("uname"));
		list = professorService.professorInfo(uname);
		
		List<ProfessorDTO> list2 = new ArrayList<ProfessorDTO>();
		list2 = professorService.psSchedule(uname);
		
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		
		return "/admin/professor/registPsCounsel";
	}
	
	@PostMapping("/registPsCounsel")
	private String registPsCounsel(@RequestParam("psName") String psName,
									@RequestParam("scsbjt") String scsbjt,
									@RequestParam("selectedDate") String selectedDate,
									@RequestParam("time") List<String> time ) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		for (String times : time) {
			map.put("psName", psName);
			map.put("scsbjt", scsbjt);
			map.put("selectedDate", selectedDate);
			map.put("times", times);
			int result = professorService.registPsCounsel(map);
		}
		
		
		return "redirect:registPsCounsel";
	}
}
