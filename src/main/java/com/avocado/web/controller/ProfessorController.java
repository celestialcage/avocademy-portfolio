package com.avocado.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avocado.web.entity.ProfessorDTO;
import com.avocado.web.service.ProfessorService;

import jakarta.annotation.Resource;

@Controller
public class ProfessorController {
	@Resource(name = "professorService")
	private ProfessorService professorService;

	@GetMapping("/professor") // 지도교수상담안내 페이지
	public String professor() {

		return "program/professor";
	}

	@GetMapping("/professorCSR") // 지도교수 상담 신청 시 학생 개인정보(이름, 학번, 전화번호) 가져오기
	public String professorCSR(Model model) {
		List<ProfessorDTO> list = professorService.studentInfo();
		List<ProfessorDTO> list2 = professorService.psSchedule();
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		return "program/professorCSR";
	}
	
	@GetMapping("/test1") // 지도교수 상담 신청 시 학생 개인정보(이름, 학번, 전화번호) 가져오기
	public String professorCSR1(Model model) {
		List<ProfessorDTO> list = professorService.studentInfo();
		model.addAttribute("list", list);
		return "program/test1";
	}
	
	
	@PostMapping("/professorCSR") // 지도교수상담 예약하기
	public String savePs(@RequestParam("studentNumber") String studentNumber,
						@RequestParam("radio-stacked") String value, 
						@RequestParam("content") String content,
						@RequestParam("selectedDate") String date,
						@RequestParam("selectedTime") String time) {
		System.out.println("학번: " + studentNumber + " 문제: " + value + " 상담내용: " + content + " 신청날짜: " + date + " 시간: " +time);
		Map<String, String> map = new HashMap<String, String>();
		map.put("studentNumber", studentNumber);
		map.put("value", value);
		map.put("content", content);
		map.put("date", date);
		map.put("time", time);
		int result = professorService.savePs(map);
		System.out.println("스케쥴 저장됬나요? " + result);
		
		// 저장됬으면 교수스케쥴 테이블에서 예약완료로 바꾸기
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("date", date);
		map2.put("time", time);
		int result2 = professorService.pscReserved(map2);
		System.out.println("교수테이블 바꼈나요 " + result2);
		
		return "redirect:/professor";
	}
	
	
		@ResponseBody
		@PostMapping("/timeList") // ajax 달력 누르면 상담시간 가져오기
		  public List<Map<String, Object>> timeList(@RequestParam ("selectedDate") String selectedDate) {
			System.out.println(selectedDate);
			List<Map<String, Object>> maps = professorService.getAll(selectedDate);
			
			return maps;
		}
	 

	@PostMapping("/test1")
	public String savePs1(@RequestParam("studentNumber") String studentNumber,
			@RequestParam("radio-stacked") String value, @RequestParam("content") String content) {
		System.out.println(studentNumber + value + content);
		Map<String, String> map = new HashMap<String, String>();
		map.put("studentNumber", studentNumber);
		map.put("value", value);
		map.put("content", content);
		int result = professorService.savePs(map);
		return "redirect:/";
	}
}
