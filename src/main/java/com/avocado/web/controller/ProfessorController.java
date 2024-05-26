package com.avocado.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avocado.web.entity.ProfessorDTO;
import com.avocado.web.service.ProfessorService;
import com.avocado.web.util.Util;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfessorController {
	@Resource(name = "professorService")
	private ProfessorService professorService;
	
	@Autowired
	private Util util;
	
	@GetMapping("/professor") // 지도교수상담안내 페이지
	public String professor() {

		return "program/professor";
	}

	@GetMapping("/professorCSR") // 지도교수 상담 신청 시 학생 개인정보(이름, 학번, 전화번호) 가져오기
	public String professorCSR(Model model) {
		
		List<ProfessorDTO> list = new ArrayList<ProfessorDTO>();
		
		HttpSession session = util.getSession();
		
		if(session.getAttribute("uname") == null) {
			return "redirect:/login";
		}
		
		String uname = (String)(session.getAttribute("uname"));
		list = professorService.studentInfo(uname);
		
		model.addAttribute("list", list);

		return "program/professorCSR";
	}
	
	
	@PostMapping("/professorCSR") // 지도교수상담 예약하기
	public String savePs(@RequestParam("studentNumber") String studentNumber,
						@RequestParam("radio-stacked") String value, 
						@RequestParam("content") String content,
						@RequestParam("selectedDate") String date,
						@RequestParam("selectedTime") String time,
						@RequestParam("selectedPscNo") int psc_no) {
		System.out.println("학번: " + studentNumber + " 문제: " + value + " 상담내용: " + content + " 신청날짜: " + date + " 시간: " + time + " 스케쥴 넘버 : " + psc_no);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("studentNumber", studentNumber);
		map.put("value", value);
		map.put("content", content);
		map.put("date", date);
		map.put("time", time);
		map.put("psc_no", psc_no);
		int result = professorService.savePs(map);
		System.out.println("스케쥴 저장됬나요? " + result);
		
		// 저장됬으면 교수스케쥴 테이블에서 예약완료로 바꾸기 (0->1)
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("date", date);
		map2.put("time", time);
		map2.put("psc_no", psc_no);
		System.out.println("date : " + date + " time : " + time + " psc_no: " + psc_no );
		int result2 = professorService.pscReserved(map2);
		System.out.println("교수테이블 바꼈나요 " + result2);
		
		return "redirect:/professor";
	}
	
	
		@ResponseBody
		@PostMapping("/timeList") // ajax 달력 누르면 상담시간 가져오기
		  public List<Map<String, Object>> timeList(@RequestParam ("selectedDate") String selectedDate) {
			System.out.println(selectedDate);
			
			//세션 값 불러오기
			HttpSession session = util.getSession();
			String uname = (String)(session.getAttribute("uname"));
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("selectedDate", selectedDate);
			map.put("uname", uname);
			System.out.println(selectedDate + uname);
			List<Map<String, Object>> maps = professorService.getAll(map);
			System.out.println(maps);
			
			return maps;
		}
	 
		
}
