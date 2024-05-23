package com.avocado.web.controller;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avocado.web.entity.CounselorDTO;
import com.avocado.web.entity.CslSearchDTO;
import com.avocado.web.entity.PersonalDTO;
import com.avocado.web.service.CounselService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jakarta.annotation.Resource;

@RestController
public class CounselDataController {
	
	@Resource(name = "counselService")
	private CounselService counselService;

	@PostMapping("/cs-schedule")
	public String bringSchedule(@RequestBody CounselorDTO cs) {
		//System.out.println(cs.getCns_no());
		String cns_no = String.valueOf(cs.getCns_no());
		JsonObject json = new JsonObject();
		List<Map<String, Object>> list;
		
		if(cs.getCns_no() == 0) {
			list = counselService.findAllSchedule();
		} else {
			list = counselService.findEachSchedule(cns_no);
		}
		
		JsonArray jsonArr = new JsonArray();
		for(Map<String, Object> map : list) {
			JsonObject obj = new JsonObject();
			obj.addProperty("scheduleNo", map.get("sch_no").toString());
			obj.addProperty("counselorNo", map.get("cns_no").toString());
			obj.addProperty("cslName", map.get("cns_nm").toString());
			obj.addProperty("cslOffice", map.get("ofc_no").toString());
			obj.addProperty("cslField", map.get("cns_field").toString());
			obj.addProperty("scheduleDate", map.get("sch_ymd").toString());
			obj.addProperty("scheduleTime", map.get("sch_hr").toString());
			// 자바 null 검사는 귀찮구나..
			if(map.get("stud_nm") != null) {
				obj.addProperty("stName", map.get("stud_nm").toString());
			}
			if(map.get("sch_state") != null) {
				obj.addProperty("scheduleState", map.get("sch_state").toString());
			}
			jsonArr.add(obj);
		}
		json.add("schedules", jsonArr);
		
		return json.toString();
	}
	
	@PostMapping("/cs-info")
	public String bringCsInfo(@RequestBody CounselorDTO cs) {
		Map<String, Object> csInfo;
		csInfo = counselService.findCsInfo(cs.getUser_no());
		Gson gson = new Gson();
		String json = gson.toJson(csInfo);
		return json;
	}
	
	@PostMapping("/add-cschedule")
	public String addCsSchedule(@RequestBody PersonalDTO ps) {
//		System.out.println("상담사 번호: " + ps.getCns_no());
//		System.out.println("상담 날짜: " + ps.getSch_ymd());
//		System.out.println("상담 시간: " + ps.getSch_hr());
		JsonObject json = new JsonObject();
		
		// 중복 스케줄 있는지 조회도...
		int isThereSchedule = counselService.findSchedule(ps);
//		System.out.println(isThereSchedule);
		if (isThereSchedule == 1) {
			json.addProperty("result", 0);
			json.addProperty("message", "이미 등록된 스케줄");
		} else {
			int result = counselService.addSchedule(ps);
			json.addProperty("result", result);
			json.addProperty("message", "스케줄 추가 성공");
		}
		return json.toString();
	}
	
	@PostMapping("/delete-cschedule")
	public String deleteCsSchedule(@RequestBody PersonalDTO ps) {
		JsonObject json = new JsonObject();
		// 이미 예약된 스케줄은 삭제 불가
		int result = counselService.deleteSchedule(ps);
		
		String message = result == 0 ? "스케줄 삭제 실패" : "스케줄 삭제 성공";
		json.addProperty("result", result);
		json.addProperty("message", message);
		
		return json.toString();
	}
	
	@PostMapping("/apply-schedule")
	public String applyCsSchedule(@RequestBody PersonalDTO ps) {
		JsonObject json = new JsonObject();
		
		int result = counselService.applySchedule(ps);
		String message = result == 0 ? "상담 신청 실패" : "상담 신청 성공";
		
		json.addProperty("result", result);
		json.addProperty("message", message);
		
		return json.toString();
	}
	
	@PostMapping("/cs-schedule-list")
	public List<PersonalDTO> csScheduleList(@RequestBody CounselorDTO cs) {
		
		CslSearchDTO searchDTO = new CslSearchDTO();
		
		List<PersonalDTO> list;
		if (cs.getCns_no() == 0) {
			searchDTO.setCns_no(cs.getCns_no());
		}
		
		list = counselService.findCslScheduleList(searchDTO);
		
		return list;
	}
}
