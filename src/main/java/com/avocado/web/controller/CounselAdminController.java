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
import com.google.gson.JsonObject;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class CounselAdminController {

	@Autowired
	private Util util;
	
	@Resource(name = "counselService")
	private CounselService counselService;
	
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
		
		int pageNo = util.str2Int(page);
		searchDTO.setPage(Math.max(0, (pageNo - 1) * 10)); // 페이지 번호를 오프셋으로 변환, 오프셋이 음수가 되지 않도록 설정
		
		// 학생 검색했을 시
		if(stud_no != null) {
			searchDTO.setStud_no(stud_no);
		}
		
		// 상담사번호 있을 때 -> 상담사 로그인일 때
		if(session.getAttribute("cns_no") != null) {
			searchDTO.setCns_no(Integer.valueOf(session.getAttribute("cns_no").toString()));
		} 
		
		list = counselService.findCslScheduleList(searchDTO);
		totalCount = counselService.findCsAppTotalCount(searchDTO);
		
        int totalPage = (int) Math.ceil((double) totalCount / 10);
        int startPage = Math.max(1, pageNo - 5);
        int endPage = Math.min(startPage + 9, totalPage);
		
		model.addAttribute("applyList", list);
		// model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", util.str2Int(page));
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("stud_no", stud_no);
		
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
		
		PersonalDTO dto = counselService.findCslSchedule(util.str2Int(aply_no));
		int aply_no_old = dto.getAply_no_old();
//		System.out.println(aply_no_old);
		
		model.addAttribute("aply_no", aply_no);
		model.addAttribute("dscsn_nmtm", dto.getDscsn_nmtm());
		model.addAttribute("aply_no_old", aply_no_old);
		return "admin/appointment-comment";
	}
	@GetMapping("/update-comment@{aply_no}")
	public String updateComment(@PathVariable(name="aply_no") String aply_no, Model model) {
		
		PersonalDTO ps = new PersonalDTO();
		ps = counselService.findCslSchedule(util.str2Int(aply_no));
		
		model.addAttribute("aply_no", aply_no);
		model.addAttribute("dto", ps);
		
		return "admin/appointment-update-comment";
	}
	
	@PostMapping("/comment")
	public String writeAppComment(@RequestParam(name="aply_no") String aply_no, 
								@RequestParam(name="dscsn_cn") String dscsn_cn, 
								@RequestParam(name="dscsn_nmtm") String dscsn_nmtm, 
								@RequestParam(name="aply_no_old") String aply_no_old) {
		// ajax로 dto.. 받아올것같다 아니었다
//		System.out.println("post 메서드에서 ");
//		System.out.println(aply_no_old);
		PersonalDTO ps = new PersonalDTO();
		ps.setAply_no(util.str2Int(aply_no));
		ps.setDscsn_cn(dscsn_cn);
		ps.setDscsn_nmtm(util.str2Int(dscsn_nmtm));
		ps.setAply_no_old(util.str2Int(aply_no_old));
		int result = counselService.updateComment(ps);
		System.out.println("상담 코멘트 결과: " + result);
		
		return "redirect:/admin/appointment";
	}
	
	@GetMapping("/appointment-series")
	public String applySeries(@RequestParam(name="aply_no") String aply_no, Model model) {
		// 같은 학생 이번 신청건 일단 가져오고
		PersonalDTO ps = new PersonalDTO();
		ps = counselService.findCslSchedule(util.str2Int(aply_no));
		
		// model.addAttribute("aply_no", aply_no);
		model.addAttribute("detail", ps);
		
		return "admin/appointment-series";
	}
	
	// 잠깐 생각좀... 어차피 aply_no_old 하나 세팅되는 거 말곤 apply-schedule이랑 같음.
	// 그냥 apply-schedule로 해도 될듯. aply_no_old는 js에서 ajax body에 넣고
	@PostMapping("/apply-serial")
	public String applySerialAppointment(@RequestBody PersonalDTO ps) {
		JsonObject json = new JsonObject();
		
		int result = counselService.applySchedule(ps);
		String message = result == 0 ? "상담 신청 실패" : "상담 신청 성공";
		
		json.addProperty("result", result);
		json.addProperty("message", message);
		
		return json.toString();
	}
	
}
