package com.avocado.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avocado.web.entity.GroupDTO;
import com.avocado.web.entity.SurveyDTO;
import com.avocado.web.service.GroupService;
import com.avocado.web.service.SurveyService;
import com.avocado.web.util.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api")
public class SurveyController {
	
	@Resource(name="surveyService")
	private SurveyService surveyService;

	 @PostMapping("/webhook")
	 @ResponseBody
	 public String handleWebhook(@RequestBody Map<String, Object> payload) {
		 System.out.println(payload.toString());
	     //int result = surveyService.insertResponse(response);	     
	     return "Response saved";
	 }

    @GetMapping("/responses")
    public String getAllResponses(Model model) {
        List<SurveyDTO> responses = surveyService.getAllResponses();
        model.addAttribute("responses", responses);
        return "responses";
    }
}
