package com.avocado.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avocado.web.service.CounselService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jakarta.annotation.Resource;

@RestController
public class CounselDataController {
	
	@Resource(name = "counselService")
	private CounselService counselService;

	@GetMapping("/cs-schedule")
	public String bringSchedule() {
		JsonObject json = new JsonObject();
		
		List<Map<String, Object>> list = counselService.findAllSchedule();
		
		JsonArray jsonArr = new JsonArray();
		for(Map<String, Object> map : list) {
			JsonObject obj = new JsonObject();
			obj.addProperty("scheduleNo", map.get("sch_no").toString());
			obj.addProperty("counselorNo", map.get("cns_no").toString());
			obj.addProperty("scheduleDate", map.get("sch_ymd").toString());
			obj.addProperty("scheduleTime", map.get("sch_hr").toString());
			jsonArr.add(obj);
		}
		json.add("schedules", jsonArr);
		
		return json.toString();
	}
}
