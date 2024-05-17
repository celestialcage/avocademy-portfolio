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
			@RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo) {
		// 한번에 보여주고 싶은 글 개수
		int post = 10;
		
		// 총 글 갯수 확인해서 페이지 개수 계산
		int totalCount = onlineService.count();
		int totalPage = 1;
		if (totalCount % post == 0) {
			totalPage = totalCount / post;
		} else {
			totalPage = (totalCount / post) + 1;
		}
		model.addAttribute("totalPage", totalPage);
		
		
		List<OnlineDTO> list = onlineService.online(pageNo, post);
		
		
		// List<OnlineDTO> list = onlineService.online();
		
		//System.out.println(list.get(0).getCommentYN());
		
		model.addAttribute("list", list);
		model.addAttribute("pageNo", pageNo);
		
		return "online";
	}
	

	

}
