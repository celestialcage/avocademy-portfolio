package com.avocado.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.OnlineDTO;
import com.avocado.web.entity.TestDTO;
import com.avocado.web.service.OnlineService;
import com.avocado.web.service.TestService;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/online")
public class OnlineController {

	@Resource(name="onlineService")
	private OnlineService onlineService;
	
	@GetMapping("/detail")
	public String detail(@RequestParam(name="bno", required= false, defaultValue = "1") int bno, Model model) {
		List<OnlineDTO> list = onlineService.online();
		model.addAttribute("list",list);
		return "online/detail";
		
	}
	
	@GetMapping("/write")
	public String write(Model model) {
		List<OnlineDTO> list = onlineService.online();
		model.addAttribute("list", list);
		return "online/write";
	}
}
