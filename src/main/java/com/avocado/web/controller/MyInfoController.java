package com.avocado.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.CslSearchDTO;
import com.avocado.web.entity.GroupDTO;
import com.avocado.web.entity.MyinfoDTO;
import com.avocado.web.entity.OnlineDTO;
import com.avocado.web.entity.UserDTO;
import com.avocado.web.service.MyInfoServiceImpl;
import com.avocado.web.util.SecureInfo;
import com.avocado.web.util.Util;

import jakarta.servlet.http.HttpSession;

@Controller

public class MyInfoController {

	@Autowired
	private Util util;
	
	@Autowired
	private SecureInfo secureInfo;
	
	@Autowired
	private MyInfoServiceImpl myInfoService;

	
	//마이페이지 이동
	@GetMapping("/myInfo")
	public String myInfo(Model model,
			 @RequestParam(name = "bno", required = false, defaultValue = "1") int bno,
			 @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
             @RequestParam(name = "post",defaultValue = "10") int post) {
		
		
		HttpSession session = util.getSession();
		
		int uno =  (int) (session.getAttribute("uno"));
	
		//System.out.println(session.getAttribute("uname"));
		//System.out.println(session.getAttribute("uno"));

		//총 글 갯수 확인해서 페이지 개수 계산!!!
		int totalCount = myInfoService.count(uno);
		// System.out.println("totalcount : " + totalCount);
		int totalPage = 1;

		if (totalCount % post == 0) {
			totalPage = totalCount / post;
		} else {
			totalPage = (totalCount / post) + 1;
		}

		// 페이지가 1보다 작으면 0이거나 음수면 1로 돌리기
		if (pageNo < 1) {
			pageNo = 1;
			System.out.println(pageNo  + ": pageNo");
		}
		// 페이지가 글의 총 개수보다 커지면 페이지는 글 최대 개수로 제한
		if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageNo", pageNo);
		
		List<MyinfoDTO> list = myInfoService.myinfo(pageNo, post, uno);
		
		// System.out.println(list);
		
		model.addAttribute("list",list );
		//model.addAttribute("pageNo",pageNo );
		
		return "myinfo";
	}


	
	@GetMapping("/profileEdit")
	public String profileEdit() {
		return "myinfo/profileEdit";
	}
	
	@GetMapping("/reservationList")
	   public String reservationList(Model model) {
	      
			// 로그인확인 조건문 쓰기 세션에 로그인 되어있는지 확인
			HttpSession session = util.getSession();

			String stud_no = (String) session.getAttribute("stud_no");
			
			// if문으로
			if (session.getAttribute("stud_no") == null) {
				return "redirect:/login";
			}
					
			List<GroupDTO> dto = new ArrayList<GroupDTO>();
				dto = myInfoService.reservationList(stud_no);
				model.addAttribute("dto", dto);
				//dto.setStud_no(stud_no)';
				//System.out.println(stud_no);
				return "myinfo/reservationList";

		}
	
	@GetMapping("/testList")
	public String testList() {
		return "myinfo/testList";
	}
}

