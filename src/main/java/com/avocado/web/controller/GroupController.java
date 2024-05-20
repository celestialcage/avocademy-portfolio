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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avocado.web.entity.GroupDTO;
import com.avocado.web.service.GroupService;
import com.avocado.web.util.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/group")
public class GroupController {
	
	@Resource(name="groupService")
	private GroupService groupService;
	
	@Autowired
	private Util util;
	
	//프로그램 확인 페이지 (학생)
	@GetMapping("")
	public String groupMain() {
		return "program/group";
	}
	
	//프로그램 목록 보기
	@GetMapping("/programList")
	public String programList(Model model) {		
		List<GroupDTO> list = groupService.programList();
		model.addAttribute("list", list);
		return "program/programList";
	}
	
	//프로그램 설명 자세히 보기
	@GetMapping("/programDetail")
	public String programDetail(@RequestParam("no") String no, Model model, GroupDTO dto) {
		dto = groupService.programDetail(no);
		model.addAttribute("detail", dto);
		return "program/programDetail";
	}
	
	//프로그램 신청(학생)
	
	//중복 스케줄 검사
	@GetMapping("/programApply")
	public @ResponseBody String programApply(@RequestParam("no") String no) {
		
		HttpSession session = util.getSession();
		String stud_no = (String) session.getAttribute("stud_no");
		Map<String, Object> check = new HashMap<String, Object>();
		check.put("prg_no", no);
		check.put("stud_no", stud_no);
		
		int count = groupService.checkSchedul(check);
		
		JsonObject json = new JsonObject();
		json.addProperty("count", count);
		
		return json.toString();
	}
	
	//신청
	@PostMapping("/programApply")
	public @ResponseBody Map<String, Object> programApply(@RequestParam("no") String no, HttpSession session) {
		
		String stud_no = (String) session.getAttribute("stud_no");
		if (stud_no == null) {
			return errorResponse("세션이 만료되었습니다.");
	    }
		
		try {
			//스케줄번호 찾아오기
			List<Integer> schdlNo = groupService.getSchedulNo(no);
			
			//신청테이블에 넣기
			for(int i = 0; i < schdlNo.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("schdno", schdlNo.get(i));
				map.put("stud_no", stud_no);
				int result = groupService.apply(map);
				if (result == 0) {
					return errorResponse("스케줄 번호 " + schdlNo + "에 대한 신청이 실패하였습니다.");
				}
			}
			//성공시
			return successResponse("신청이 완료되었습니다.");
			
		} catch (Exception e) {
			return errorResponse("통신 오류가 발생했습니다: " + e.getMessage());
		}

	}
	
	private Map<String, Object> successResponse(String message){
		Map<String, Object> response = new HashMap<String, Object>();	
		response.put("status", "success");
		response.put("message", message);
		return response;
	}
	
	private Map<String, Object> errorResponse(String message){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", "error");
		response.put("message", message);
		return response;
	}
	
}
