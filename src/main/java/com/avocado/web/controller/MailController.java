package com.avocado.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avocado.web.service.MailService;
import com.avocado.web.util.Util;

@RestController
public class MailController {
	
	@Autowired
	private Util util;
	
	@Autowired
	private MailService mailService;
	
	@PostMapping("/emailAuth2")
	public int emailAuth() {
		System.out.println("컨트롤러_인증번호 요청하기");
		return mailService.sendEmail();
	}

}
