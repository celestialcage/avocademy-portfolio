package com.avocado.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.service.UserService;
import com.avocado.web.util.Util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	private Util util;

	@Autowired
	private UserService userService;

	@GetMapping("/login") // 로그인
	public String loginPage(Model model) {
		model.addAttribute("message", "로그인 페이지");
		return "login";
	}

	@PostMapping("/login") // 로그인 post
	public String login(@RequestParam Map<String, Object> map, HttpServletResponse response) {


		System.out.println(map);
		String id = (String) map.get("id");
		String pw = (String) map.get("pw");
		map.put("uid", id);
		map.put("pw", pw);
		System.out.println("활용하자 "  );
		
		Map<String, Object> result = userService.login(map);
		System.out.println("활용하자 "  + result);

		if (util.str2Int(result.get("count")) == 1) { // mapper 에서 오는 count(*) 의 별칭
			HttpSession session = util.getSession();
			session.setAttribute("uid", result.get("uid"));
			session.setAttribute("uname", result.get("uname"));
			session.setAttribute("uno", result.get("uno"));
			session.setAttribute("ugrade", result.get("ugrade"));
		
			
			// 출력하여 uid 확인
		    System.out.println("UID: " + result.get("uid"));

			// 쿠키 생성
			Cookie loginCookie = new Cookie("loginCookie", session.getId());
			loginCookie.setMaxAge(60 * 60 * 24); // 쿠키 유효 시간 설정 (예: 24시간)
			loginCookie.setPath("/"); // 쿠키의 유효 경로 설정
			response.addCookie(loginCookie);
			
			// 사용자 등급 변수를 맵에서 추출
	        String role = (String) result.get("ugrade");
	        System.out.println("사용자 등급: " + role);
	        System.out.println("쨘");

			// 사용자의 등급에 따라 리다이렉트할 페이지 결정
			if ("5".equals(role)) {
			    return "redirect:/admin/index"; // 관리자 페이지로 리다이렉트
			} else if ("1".equals(role)) {
			    return "redirect:/main"; // 일반 사용자 페이지로 리다이렉트
			}
			else {
			return "redirect:/login"; // 만약 role 값이 1이나 5가 아니면 오류 페이지나 기본 페이지로 리다이렉트
			}
		}
		// 로그인 실패시 로그인 페이지로 다시 리다이렉트
	    return "redirect:/login"; 
	}
		
		
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if (session.getAttribute("uid") != null) {
			session.removeAttribute("uid");
		}
		if (session.getAttribute("uname") != null) {
			session.removeAttribute("uname");

		}
		session.invalidate();

		return "redirect:/login";
	}
}
