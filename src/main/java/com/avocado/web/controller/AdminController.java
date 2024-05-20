package com.avocado.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avocado.web.service.CounselService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Resource(name = "counselService")
	private CounselService counselService;
	
	@GetMapping("")
	public String redirectAdmin() {
		return "redirect:admin/";
	}

	@GetMapping({"/", "/index"})
	public String admin() {
		return "admin/index";
	}
	
	@GetMapping("/index2")
	public String admin2() {
		return "admin/index2";
	}
	
	@GetMapping("/charts")
	public String charts() {
		return "admin/charts";
	}
	
	@GetMapping("/tables")
	public String tables() {
		return "admin/tables";
	}
	
	@GetMapping("/full-width")
	public String fullWidth() {
		return "admin/grid";
	}
	
	@GetMapping("/form-basic")
	public String form1() {
		return "admin/form-basic";
	}
	
	@GetMapping("/form-wizard")
	public String form2() {
		return "admin/form-wizard";
	}
	
	@GetMapping("/full-calendar")
	public String fullCalendar() {
		return "admin/pages-calendar";
	}
	
	@GetMapping("/calendar")
	public String calendar(Model model) {
		// 세션에서 관리자 이상만 데려가기...
		
		// 임시로 서비스로 가져오고 캐싱하기~!
		List<Map<String, Object>> times = counselService.findAllTimes();
		model.addAttribute("times", times);
		
		// 이 페이지는 상담사 중 개인 상담하는 사람만 갈 수 있도록
		return "admin/tui-calendar";
	}
	
}
