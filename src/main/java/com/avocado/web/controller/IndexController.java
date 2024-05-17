package com.avocado.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.OnlineDTO;
import com.avocado.web.service.IndexService;
import com.avocado.web.service.OnlineService;
import com.avocado.web.service.TestService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	
	@Resource(name="indexService")
	private IndexService indexService;

	@Resource(name="testService")
	private TestService testService;
	
	@Resource(name="onlineService")
	private OnlineService onlineService;
	
	@GetMapping({"/", "/main"})
	public String main(Model model) {
		model.addAttribute("message", "메인 페이지");
		return "index";
	}

	@GetMapping("/centerInfo") // 센터 소개
	public String centerInfo() {
		return "introduce";
	}
	
	@GetMapping("/location")
	public String location() {
		return "location";
	}
	
	@GetMapping("/counselingGuide") // 상담 안내
	public String counselingGuide(Model model) {
		model.addAttribute("message", "상담 안내");
		return "redirect:/personal";
	}
	
	@GetMapping("/program") // 교육 프로그램
	public String program(Model model) {
		model.addAttribute("message", "교육 프로그램");
		return "program";
	}
	
	@GetMapping("/online") // 온라인 상담
	public String online(Model model, HttpSession session,
			@RequestParam(name="page", defaultValue = "1") int page,
			@RequestParam(name="size", defaultValue = "10") int size) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("page", page);
		map.put("size", size);
		List<OnlineDTO> list = onlineService.online();
		//List<OnlineDTO> list = onlineService.findAll(map);
		
		//System.out.println(list.get(0).getCommentYN());
		
		int total = onlineService.count();
		model.addAttribute("list", list);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", (total + size - 1) / size);
		
		return "online";
	}
	

	

}
