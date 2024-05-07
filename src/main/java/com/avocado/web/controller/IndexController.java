package com.avocado.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.OnlineDTO;
import com.avocado.web.entity.TestDTO;
import com.avocado.web.service.IndexService;
import com.avocado.web.service.OnlineService;
import com.avocado.web.service.TestService;

import jakarta.annotation.Resource;

@Controller
public class IndexController {

	@Resource(name="indexService")
	private IndexService indexService;

	@Resource(name="testService")
	private TestService testService;
	
	@Resource
	private OnlineService onlineService;
	
	@GetMapping({"/", "/main"})
	public String main(Model model) {
		model.addAttribute("message", "메인 페이지");
		return "index";
	}

	@GetMapping("/centerInfo") // 센터 소개
	public String centerInfo(Model model) {
		model.addAttribute("message", "센터 소개");
		return "index";
	}
	
	@GetMapping("/programGuide") // 상담 안내
	public String programGuide(Model model) {
		model.addAttribute("message", "상담 안내");
		return "index";
	}
	
	@GetMapping("/lecture") // 교육 프로그램
	public String lecture(Model model) {
		model.addAttribute("message", "교육 프로그램");
		return "index";
	}
	
	@GetMapping("/online") // 온라인 상담
	public String online(Model model) {
		List<OnlineDTO> list = onlineService.online();
		model.addAttribute("list", list);
		return "online";
	}
	
	@GetMapping("/community") // 커뮤니티
	public String community(Model model) {
		model.addAttribute("message", "커뮤니티");
		return "index";
	}
	
	@GetMapping("/login") // 로그인
	public String loginPage(Model model) {
		model.addAttribute("message", "로그인 페이지");
		return "login";
	}
	
	@PostMapping("/login") // 로그인 post
	public String login(@RequestParam Map<String, Object>map) {
		System.out.println(map);
		return "login";
	}
	
	@GetMapping("/group") //집단 상담
	public String group (Model model) {
		List<TestDTO> list = testService.staff();
		System.out.println(list);
		model.addAttribute("list", list);
		return "group";
	}
}
