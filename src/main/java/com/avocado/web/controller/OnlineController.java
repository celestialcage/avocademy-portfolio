package com.avocado.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.OnlineDTO;
import com.avocado.web.service.OnlineService;
import com.avocado.web.util.Util;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/online")
public class OnlineController {

	@Autowired
	private Util util;

	@Resource(name = "onlineService")
	private OnlineService onlineService;

	@GetMapping("/detail")
	public String detail(Model model,
			@RequestParam(name = "bno", 
			required = false, defaultValue = "1") int bno, 
			HttpSession session) {
		System.out.println(bno);
		OnlineDTO detail = onlineService.detail(bno);
		model.addAttribute("detail", detail);

		return "online/detail";

	}

	@GetMapping("/write")
	public String write() {

		HttpSession session = util.getSession();

		if (session.getAttribute("uname") != null) {

			return "online/write";
		} else {
			return "redirect:/login";
		}
	}

	@PostMapping("/write")
	public String write(@RequestParam(name = "btitle") String btitle,
			HttpSession session,
			@RequestParam(name = "bcontent") String bcontent) {
		System.out.println(btitle + bcontent);
		// 글 작성 로직 실행

		// 로그인 검사해주세요

		if (session.getAttribute("uname") != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("btitle", btitle);
			map.put("bcontent", bcontent);
			map.put("uname", session.getAttribute("uname"));
			// map.put("uno", session.getAttribute("uno"));
			
			System.out.println(map);

			int result = onlineService.write(map);

			// 성공시 목록 페이지로 리디렉션
			String url = "online";
			return "redirect:/online";

		} else {
			return "redirect:/login";
		}

	}

	@PostMapping("/deletecd")
	public String deletecd(@RequestParam(name ="bno") String bno) {
		System.out.println("삭제 : " + bno);
		int result = onlineService.deletecd(bno);
		return "redirect:/online";
	}
}
