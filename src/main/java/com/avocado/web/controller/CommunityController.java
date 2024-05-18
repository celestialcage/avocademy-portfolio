package com.avocado.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.CommunityDTO;
import com.avocado.web.service.CommunityService;
import com.avocado.web.util.Util;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/community")
public class CommunityController {
	
	@Autowired
	private Util util;

	@Resource(name = "communityService")
	private CommunityService communityService;
	
	@GetMapping("/community") // 온라인 상담 가져온것
	public String community(Model model, HttpSession session,
			@RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo) {
		System.out.println("커뮤니티");
		// 한번에 보여주고 싶은 글 개수
		int post = 10;
		
		// 총 글 갯수 확인해서 페이지 개수 계산
		int totalCount = communityService.count();
		int totalPage = 1;
		if (totalCount % post == 0) {
			totalPage = totalCount / post;
		} else {
			totalPage = (totalCount / post) + 1;
		}
		
		// 페이지가 1보다 작으면 0이거나 음수면 1로 돌리기
		if (pageNo < 1) {
			pageNo = 1;
		}
		// 페이지가 글의 총 개수보다 커지면 페이지는 글 최대 개수로 제한
		if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageNo", pageNo);
		
		List<CommunityDTO> list = communityService.community(pageNo, post);
		
		System.out.println(list);
		
		model.addAttribute("list", list);
		model.addAttribute("pageNo", pageNo);
		
		return "community";
	}
	

	@GetMapping("/detail")
	public String detail(Model model, 
			@RequestParam(name = "cno", required = false, defaultValue = "1") int cno,
			HttpSession session) {
		System.out.println(cno);
		CommunityDTO detail = communityService.detail(cno);
		System.out.println(detail.toString());

		// 세션에 로그인이 안되어있는 상태에서 session.get 하면 null 반환되고
		// null 이랑 equals 연산을 하다보니 null 오류가 발생
		// 로그인이 안되어있으면 다시 게시판 페이지로 로딩이되거나 로그인으로 이동시키거나 골라서
		if(session.getAttribute("uname") == null) {
			// return "redirect:/login";
			return "redirect:/community";
		} else if (detail.getUname().equals(session.getAttribute("uname")) || (int) session.getAttribute("ugrade") == 5) {
			model.addAttribute("detail", detail);
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
			HttpSession session) {
		System.out.println(ctitle + ccontent);
		// 글 작성 로직 실행

		// 로그인 검사해주세요

		String uname = (String) session.getAttribute("uname");

		if (uname != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ctitle", ctitle);
			map.put("ccontent", ccontent);
			map.put("uname", uname);
			map.put("cno", session.getAttribute("cno"));

			System.out.println(map);

			int result = communityService.write(map);

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
	

}
