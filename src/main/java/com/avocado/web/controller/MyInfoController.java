package com.avocado.web.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.avocado.web.util.Util;
@Controller
public class MyInfoController {
	
	@Autowired
	private Util util;

	//마이페이지 이동
	@GetMapping("/myInfo")
	public String myInfo() {
		return "myInfo";   
	}
	
	
	//파일업로드
	@GetMapping("/fileUp")
	public String fileUp() {
		return "fileUp";
		}
	
	@PostMapping("/fileUp")
	public String fileUp(@RequestParam("fileUp") MultipartFile file) {
		System.out.println(file.getName());
		System.out.println(file.getSize());
		System.out.println(file.getOriginalFilename());
		
		//String url = util.req().getServletContext().getRealPath("/upload");
		File url = new File(util.req().getServletContext().getRealPath("/upload"));
		url.mkdirs();
		
		File upFileName = new File(url, file.getOriginalFilename());
				
		try {
			file.transferTo(upFileName);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("실제 경로 : " + url);
		
		return "redirect:/fileUp";
		
	}
}
