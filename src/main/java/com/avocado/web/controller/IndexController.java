package com.avocado.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.CommunityDTO;
import com.avocado.web.entity.OnlineDTO;
import com.avocado.web.service.CommunityService;
import com.avocado.web.service.IndexService;
import com.avocado.web.service.OnlineService;
import com.avocado.web.service.TestService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	
	@Resource(name="indexService")
	private IndexService indexService;

	@Resource(name="testService")
	private TestService testService;
	
	@Resource(name="onlineService")
	private OnlineService onlineService;
	
	@Resource(name="communityService")
	private CommunityService communityService;
	
	@GetMapping({"/", "/main"})
	public String main(Model model) {
		model.addAttribute("message", "메인 페이지");
		return "index";
	}

	@GetMapping("/centerInfo") // 센터 소개
	public String centerInfo() {
		return "introduce";
	}
	
	@GetMapping("/location")
	public String location() {
		return "location";
	}
	
	@GetMapping("/counselingGuide") // 상담 안내
	public String counselingGuide(Model model) {
		model.addAttribute("message", "상담 안내");
		return "redirect:/personal";
	}
	
	@GetMapping("/program") // 교육 프로그램
	public String program(Model model) {
		model.addAttribute("message", "교육 프로그램");
		return "program";
	}
	
	@GetMapping("/online") // 온라인 상담
	public String online(Model model, HttpSession session,
			@RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo) {
		// 한번에 보여주고 싶은 글 개수
		int post = 10;
		
		// 총 글 갯수 확인해서 페이지 개수 계산
		int totalCount = onlineService.count();
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
		
		List<OnlineDTO> list = onlineService.online(pageNo, post);
		
		System.out.println(list);
		
		model.addAttribute("list", list);
		model.addAttribute("pageNo", pageNo);
		
		return "online";
	}
	
	@GetMapping("/community") // 온라인 상담 가져온것
	public String community(Model model, HttpSession session,
			@RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo) {
				
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
		
		 // 페이지 번호와 한 페이지당 글 개수를 이용하여 목록을 가져옴
		List<CommunityDTO> list = communityService.community(pageNo, post);
		
		//System.out.println("인덱스 컨트롤러 확인하자 : " +  list);
				
		model.addAttribute("list", list);
		model.addAttribute("pageNo", pageNo);
		
		// 각 게시물의 조회수 설정
	    for (CommunityDTO count : list) {
	        // 게시물의 조회수 설정
	        int cread = communityService.getCount(count.getCno());
	        count.setCread(cread);
	    }
				
		return "community";
	}
	
	
	@GetMapping("/survey")
	private String survey() {
		return "survey";
	}

}
