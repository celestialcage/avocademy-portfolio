package com.avocado.web.controller;

import java.util.HashMap;
import java.util.List;
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
import jakarta.servlet.http.HttpSession;

@RequestMapping("/community")
@Controller
public class CommunityController {

	@Autowired
	private Util util;
	

	@Resource(name = "communityService")
	private CommunityService communityService;


	private int fno;


	private int fsn;

	@GetMapping("/detail")
	public String detail(Model model, @RequestParam(name = "cno", required = false, defaultValue = "1") int cno,
			HttpSession session) {
		// System.out.println(cno);
		
		//게시글 상세 정보 조회
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
			System.out.println("디테일 컨트롤러 :" + detail);
			
			
			
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
			HttpSession session, @RequestParam("fileUp") MultipartFile file, Model model) {
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
	
	@GetMapping("/detail/{postId}")
	public String showPostDetail(@PathVariable Long postId, Model model) {
	    // 게시글 상세 정보 가져오기
	    FilesDTO file = communityService.getPostById(postId);
	    model.addAttribute("file", file);
	    
	    // 파일 목록 가져오기
	    List<FilesDTO> fileList = communityService.getFileByPostId(postId);
	    model.addAttribute("fileList", fileList);
	    
	    return "community/detail";
	}
	
		
}
