package com.avocado.web.controller;

import java.util.List;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avocado.web.entity.CounselorDTO;
import com.avocado.web.service.CounselService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jakarta.annotation.Resource;

@RestController
public class CounselDataController {
	
	@Resource(name = "counselService")
	private CounselService counselService;

	@PostMapping("/cs-schedule")
	public String bringSchedule(@RequestBody CounselorDTO cs) {
		System.out.println(cs.getCns_no());
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
			obj.addProperty("scheduleDate", map.get("sch_ymd").toString());
			obj.addProperty("scheduleTime", map.get("sch_hr").toString());
			jsonArr.add(obj);
		}
		json.add("schedules", jsonArr);
		
		return json.toString();
	}
}
