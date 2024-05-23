package com.avocado.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avocado.web.entity.CslSearchDTO;
import com.avocado.web.entity.PersonalDTO;
import com.avocado.web.service.CounselService;
import com.avocado.web.util.Util;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private Util util;
	
	@Resource(name = "counselService")
	private CounselService counselService;
	
	@GetMapping("")
	public String redirectAdmin() {
		return "redirect:admin/";
	}

	@GetMapping({"/", "/index"})
	public String admin() {
		return "admin/index";
	}
	
	@GetMapping("/index2")
	public String admin2() {
		return "admin/index2";
	}
	
	@GetMapping("/charts")
	public String charts() {
		return "admin/charts";
	}
	
	@GetMapping("/tables")
	public String tables() {
		return "admin/tables";
	}
	
	@GetMapping("/full-width")
	public String fullWidth() {
		return "admin/grid";
	}
	
	@GetMapping("/form-basic")
	public String form1() {
		return "admin/form-basic";
	}
	
	@GetMapping("/form-wizard")
	public String form2() {
		return "admin/form-wizard";
	}
	
	@GetMapping("/calendar")
	public String fullCalendar() {
		return "admin/pages-calendar";
	}
	
	@GetMapping("/csl-schedule")
	public String cslSchedule(Model model) {
		// 세션에서 관리자 이상만 데려가기...
		
		// 임시로 서비스로 가져오고 캐싱하기~!
		List<Map<String, Object>> times = counselService.findAllTimes();
		model.addAttribute("times", times);
		
		// 이 페이지는 상담사 중 개인 상담하는 사람만 갈 수 있도록
		return "admin/tui-calendar";
	}
	
	@GetMapping("/appointment")
	public String appointment(@RequestParam(name="page", defaultValue = "1") String page, 
			@RequestParam(name="stud_no", required = false) String stud_no, 
			Model model) {
		HttpSession session = util.getSession();
		List<PersonalDTO> list;
		int totalCount = 0;
		CslSearchDTO searchDTO = new CslSearchDTO();
		
		searchDTO.setPage(util.str2Int(page));
		
		// 학생 검색했을 시
		if(stud_no != null) {
			searchDTO.setStud_no(stud_no);
		}
		
		// 상담사번호 있을 때 -> 상담사 로그인일 때
		if(session.getAttribute("cns_no") != null) {
			searchDTO.setCns_no(Integer.valueOf(session.getAttribute("cns_no").toString()));
			// List<PersonalDTO> list = counselService.findCslAppointments(cns_no);
		} 
//		else {
//			// list = counselService.findAllScheduleList(searchDTO);
//		}
		
		list = counselService.findCslScheduleList(searchDTO);
		totalCount = counselService.findCsAppTotalCount(searchDTO);
		
		model.addAttribute("applyList", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);
		model.addAttribute("stud_no", stud_no);
//		System.out.println(session.getAttribute("uno"));
//		System.out.println(session.getAttribute("uid"));
		
		return "admin/appointment";
	}
	
	@GetMapping("/appointment-detail")
	@ResponseBody
	public PersonalDTO appointmentDetail(@RequestParam(name="aply_no") String aply_no) {
		PersonalDTO ps = counselService.findCslSchedule(util.str2Int(aply_no));
		return ps;
	}
	
	@GetMapping("/comment@{aply_no}")
	public String commentAppointment(@PathVariable(name="aply_no") String aply_no, Model model) {
		System.out.println(aply_no);
		
		model.addAttribute("aply_no", aply_no);
		
		return "admin/appointment-comment";
	}
	
	@PostMapping("/comment")
	public String writeAppComment(@RequestParam(name="aply_no") String aply_no, @RequestParam(name="dscsn_cn") String dscsn_cn) {
		// ajax로 dto.. 받아올것같다 아니었다
		PersonalDTO ps = new PersonalDTO();
		ps.setAply_no(util.str2Int(aply_no));
		ps.setDscsn_cn(dscsn_cn);
		int result = counselService.updateComment(ps);
		
		return "redirect:/admin/appointment@"+aply_no;
	}
	
}
