package com.avocado.web.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class SessionController {
	
	@GetMapping("/sessionInfo")
	public String getSessionInfo(HttpSession session, Model model) {
		//세션 만료 시간을 계산
		long lastAccessedTime = session.getLastAccessedTime();
		int maxInactiveInterval = session.getMaxInactiveInterval();
		long expireTimeMillis = lastAccessedTime + (maxInactiveInterval + 1000L);
		LocalDateTime expireTime= LocalDateTime.ofInstant(Instant.ofEpochMilli(expireTimeMillis), ZoneId.systemDefault());
		
		
		//포맷터
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 
		 //모델에 추가
		 model.addAttribute("expireTime", expireTime.format(formatter));
		 return "index";

	}

}
