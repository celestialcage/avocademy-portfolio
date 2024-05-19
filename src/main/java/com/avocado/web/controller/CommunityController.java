package com.avocado.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.avocado.web.entity.CommunityDTO;
import com.avocado.web.entity.FilesDTO;
import com.avocado.web.service.CommunityService;
import com.avocado.web.util.Util;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/community")
@Controller
public class CommunityController {

	@Autowired
	private Util util;

	@Resource(name = "communityService")
	private CommunityService communityService;

	@GetMapping("/detail")
	public String detail(Model model, @RequestParam(name = "cno", required = false, defaultValue = "1") int cno,
			HttpSession session) {
		// System.out.println(cno);
		CommunityDTO detail = communityService.detail(cno);
		// System.out.println(detail.toString());

		// 세션에 로그인이 안되어있는 상태에서 session.get 하면 null 반환되고
		// null 이랑 equals 연산을 하다보니 null 오류가 발생
		// 로그인이 안되어있으면 다시 게시판 페이지로 로딩이되거나 로그인으로 이동시키거나 골라서
		if (session.getAttribute("uname") == null) {
			// return "redirect:/community";
			return "redirect:/community";
		} else if (detail.getUname().equals(session.getAttribute("uname"))
				|| (int) session.getAttribute("ugrade") == 5) {
			model.addAttribute("detail", detail);
			//System.out.println("디테일 컨트롤러 :" + detail);
			return "community/detail";
		} else {
			return "redirect:/community";
		}
	}

	@GetMapping("/write")
	public String write() {

		HttpSession session = util.getSession();

		if (session.getAttribute("uname") != null) {

			return "community/write";
		} else {
			return "redirect:/login";
		}
	}

	@PostMapping("/write")
	public String write(@RequestParam(name = "ctitle") String ctitle, @RequestParam(name = "ccontent") String ccontent,
			HttpSession session, @RequestParam("fileUp") MultipartFile file) {
		//System.out.println(ctitle + ccontent);
		// 글 작성 로직 실행

		// 로그인 검사해주세요

		String uname = (String) session.getAttribute("uname");

		if (uname != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ctitle", ctitle);
			map.put("ccontent", ccontent);
			map.put("uname", uname);
			map.put("uno", session.getAttribute("uno"));

			//System.out.println(map);
			
			// dto 객체 생성
			FilesDTO dto = new FilesDTO();

			
			communityService.write(map, dto, file);

			// 성공시 목록 페이지로 리디렉션
			/* String url = "online"; */
			return "redirect:/community";

		} else {
			return "redirect:/login";
		}

	}
	
	

	@PostMapping("/deletecd")
	public String deletecd(@RequestParam(name = "cno") String cno) {
		System.out.println("삭제 : " + cno);
		int result = communityService.deletecd(cno);
		return "redirect:/community";
	}

//	// 파일 업로드
//	@PostMapping("/fileUp")
//	public String fileUp(@RequestParam("fileUp") MultipartFile file) {
//		System.out.println("파일 타입: " + file.getName());
//		System.out.println("파일 사이즈: " + file.getSize());
//		System.out.println("파일 이름: " + file.getOriginalFilename());
//
//		// 서버의 물리적 경로
//		// String url = util.req().getServletContext().getRealPath("/upload");
//		File url = new File(util.req().getServletContext().getRealPath("/upload"));
//		// 디렉터리가 존재하지 않으면 생성
//		if (!url.exists()) {
//			url.mkdirs();
//		}
//		url.mkdirs();
//
//		// UUID 생성
//		UUID uuid = UUID.randomUUID();
//		System.out.println("원본파일명 : " + file.getOriginalFilename());
//		System.out.println("UUID파일명 : " + uuid.toString() + file.getOriginalFilename());
//
//		String newFileName = uuid.toString() + "-" + file.getOriginalFilename();
//		System.out.println("새로 만들어진 파일이름 : " + newFileName);
//
//		// 날짜를 뽑아서 파일명 변경하기
//		LocalDateTime Idt = LocalDateTime.now();
//		String IdtFormat = Idt.format(DateTimeFormatter.ofPattern("YYYYMMddHHmmSS"));
//		System.out.println("날짜 파일명 : " + IdtFormat + file.getOriginalFilename());
//
//		// 실제 경로
//		File upFileName = new File(url, file.getOriginalFilename());
//		System.out.println("실제 경로 : " + url);
//
//		try {
//			// 서버에 파일저장
//			file.transferTo(upFileName);
//
//			// 썸네일 만들기
//			FileOutputStream thumbnail = new FileOutputStream(new File(url, "S_" + newFileName));
//			Thumbnailator.createThumbnail(file.getInputStream(), thumbnail, 100, 100);
//
//			thumbnail.close();
//
//			// dto 객체 생성
//			FilesDTO dto = new FilesDTO();
//
//			// 파일 정보 설정
//			dto.setFsn(file.getOriginalFilename());
//			dto.setActl_fnm(newFileName);
//			dto.setUuid(uuid.toString());
//			dto.setFsize(String.valueOf(file.getSize()));
//			dto.setFpath(upFileName.getAbsolutePath());
//
//			// DB에 파일 정보 저장
//			communityService.fileUp(dto);
//			System.out.println("파일_컨트롤러확인");
//
//		} catch (IllegalStateException | IOException e) {
//			e.printStackTrace();
//		}
//
//		return "redirect:/community";
//
//	}
		

	// 파일 다운로드
	@GetMapping("/download/{fileName}")
	public void downloadFile(@PathVariable String fileName, HttpServletResponse response) {
		// 파일 경로 설정
		String filePath = "/경로/" + fileName; // 실제 파일이 저장된 경로로 수정

		// 파일 다운로드 설정
		File file = new File(filePath);
		response.setContentType("application/octet-stream");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		// 파일을 읽어와 응답으로 전송
		try (FileInputStream fis = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
