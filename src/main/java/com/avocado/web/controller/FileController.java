package com.avocado.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.avocado.web.entity.CommunityDTO;
import com.avocado.web.service.CommunityService;
import com.avocado.web.service.CommunityServiceImpl;
import com.avocado.web.util.Util;

import net.coobird.thumbnailator.Thumbnailator;
@Controller
public class FileController {
	
	@Autowired
	private Util util;
	
	@Autowired
	private CommunityService communityService;
	
	
	@GetMapping("/community") // 커뮤니티
	public String community(Model model) {
		model.addAttribute("message", "커뮤니티");
		return "community";
	}
	
	
	@PostMapping("/fileUp")
	public String fileUp(@RequestParam("fileUp") MultipartFile file) {
		System.out.println("파일 타입: " + file.getName());
		System.out.println("파일 사이즈: " + file.getSize());
		System.out.println("파일 이름: " + file.getOriginalFilename());
		
		//서버의 물리적 경로
		//String url = util.req().getServletContext().getRealPath("/upload");
		File url = new File(util.req().getServletContext().getRealPath("/upload"));
		//디렉터리가 존재하지 않으면 생성
		if (!url.exists()) {
	            url.mkdirs();
	        }
		url.mkdirs();
		
		//UUID 생성
		UUID uuid = UUID.randomUUID();
		System.out.println("원본파일명 : " + file.getOriginalFilename());
		System.out.println("UUID파일명 : " + uuid.toString()+file.getOriginalFilename());
		
		String newFileName = uuid.toString()+ "-" + file.getOriginalFilename();
		System.out.println("새로 만들어진 파일이름 : " + newFileName);
		
		//날짜를 뽑아서 파일명 변경하기
		LocalDateTime Idt = LocalDateTime.now();
		String IdtFormat = Idt.format(DateTimeFormatter.ofPattern("YYYYMMddHHmmSS"));
		System.out.println("날짜 파일명 : " +IdtFormat + file.getOriginalFilename());
				
		//실제 경로
		File upFileName = new File(url, file.getOriginalFilename());
		System.out.println("실제 경로 : " + url);
				
		try {
			//서버에 파일저장
			file.transferTo(upFileName);
			
			//썸네일 만들기
			FileOutputStream thumbnail = new FileOutputStream(new File(url,"S_"+newFileName));
			Thumbnailator.createThumbnail(file.getInputStream(), thumbnail,100,100);
			
			thumbnail.close();
			
			//dto 객체 생성
			CommunityDTO communityDTO = new CommunityDTO();
			
			//DB에 파일 정보 저장
			communityService.saveFileUpload(communityDTO);
			System.out.println("확인컨트롤러");
			
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/community";
		
	}
}
