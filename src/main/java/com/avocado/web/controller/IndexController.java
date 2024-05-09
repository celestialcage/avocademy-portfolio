package com.avocado.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.OnlineDTO;
import com.avocado.web.service.IndexService;
import com.avocado.web.service.OnlineService;
import com.avocado.web.service.TestService;
import com.avocado.web.service.UserService;
import com.avocado.web.util.Util;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	
	@Autowired
	private Util util;
	
	@Autowired
	private UserService userService;
	
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
	public String online(@RequestParam(name="bno", required= false, defaultValue = "1") int bno, Model model) {
		List<OnlineDTO> list = onlineService.online(bno);
		//System.out.println(list.get(0).getCommentYN());
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
	public String login(@RequestParam Map<String, Object> map, HttpServletResponse response) {
	    
		
		System.out.println(map);
		String id = (String) map.get("id");
		String pw = (String) map.get("pw");
		map.put("uid", id);
		map.put("pw", pw);
	    
		 Map<String, Object> result = userService.login(map);
		    
		 
		 if (util.str2Int(result.get("count")) == 1) { // mapper 에서 오는 count(*) 의 별칭
		     HttpSession session = util.getSession();
		     session.setAttribute("uid", result.get("uid"));
		     session.setAttribute("uname", result.get("uname"));
		     session.setAttribute("uno", result.get("uno"));
		     

		     // 쿠키 생성
		     Cookie loginCookie = new Cookie("loginCookie", session.getId());
		     loginCookie.setMaxAge(60 * 60 * 24); // 쿠키 유효 시간 설정 (예: 24시간)
		     loginCookie.setPath("/"); // 쿠키의 유효 경로 설정
		     response.addCookie(loginCookie);

		     return "redirect:/main";
		     
		 } else {
		     return "redirect:/login";
		 }
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
