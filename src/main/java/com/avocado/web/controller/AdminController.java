package com.avocado.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping({"/", "/index"})
	public String admin() {
		return "admin/index";
	}
	
	@GetMapping("/index2")
	public String admin2() {
		return "admin/index2";
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
	
	@GetMapping("/calendar")
	public String calendar() {
		return "admin/pages-calendar";
	}
	
}
