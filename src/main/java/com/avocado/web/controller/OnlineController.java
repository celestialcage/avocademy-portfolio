package com.avocado.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.OnlineDTO;
import com.avocado.web.service.OnlineService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/online")
public class OnlineController {

	@Resource(name="onlineService")
	private OnlineService onlineService;
	
	@GetMapping("/detail")
	public String detail(@RequestParam(name="bno", required= false, defaultValue = "1") int bno, Model model) {
		System.out.println(bno);
		OnlineDTO detail = onlineService.detail(bno);
		model.addAttribute("detail",detail);
		return "online/detail";
		
	}
	@GetMapping("/write")
	  public String write() {
	      return "online/write";
	   }
	@PostMapping("/write")
	public String write(			
			@RequestParam(name="btitle") String btitle,
			@RequestParam(name="bcontent") String bcontent
			) {	
		System.out.println(btitle + bcontent);
		//글 작성 로직 실행
		Map<String, String> map = new HashMap<String, String>();
		map.put("btitle", btitle);
		map.put("bcontent", bcontent);
		int result = onlineService.write(map);
		//로그인 검사해주세요
		
		 
		// 성공시 목록 페이지로 리디렉션
		String url = "online";
		return "redirect:/online";
	}
	
	@GetMapping("/online")
	public String getOnlinePage(@RequestParam(defaultValue="1")int page, Model model) {
		
		 int totalPages = 10;
	       

	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", totalPages);

		return "online";
	}
	
	
	

}
	

