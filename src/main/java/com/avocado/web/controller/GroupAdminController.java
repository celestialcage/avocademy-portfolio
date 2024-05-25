package com.avocado.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avocado.web.entity.GroupDTO;
import com.avocado.web.service.GroupService;
import com.avocado.web.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class GroupAdminController {

	@Resource(name="groupService")
	private GroupService groupService;
	
	@Autowired
	private Util util;
	
	
	//프로그램 관리 페이지 (상담사)
	@GetMapping("/groupAdmin")
	private String groupAdmin() {		
		return "admin/group/groupAdmin";
	}
	
	
	//프로그램 등록 페이지(상담사)
	@GetMapping("/registProgram")
	private String registProgram(Model model, GroupDTO groupDto, HttpSession session) {
		//System.out.println(session.getAttribute("cns_no"));
		groupDto.setCns_no((String) session.getAttribute("cns_no"));
		groupDto.setPrg_cd(groupService.getfield(groupDto.getCns_no()));
		model.addAttribute("groupDto", groupDto);
		return "admin/group/registProgram";
	}
	
	@PostMapping("/registProgram")
	private String registProgram(@ModelAttribute("groupDto") GroupDTO dto, HttpSession session) {
		
		//프로그램 진행기간
		dto.setPrg_start(dto.getGroupSCHDL().get(0));
		dto.setPrg_end(dto.getGroupSCHDL().get(dto.getPrg_nmtm() -1));
		dto.setPrg_schdl(dto.getPrg_start() + " - " + dto.getPrg_end());
		
		if(dto.getCns_no().equals("4")) {
			dto.setPrg_place("진로상담실");
		} else if(dto.getCns_no().equals("5")) {
			dto.setPrg_place("명상실");			
		} else if(dto.getCns_no().equals("6")) {
			dto.setPrg_place("미니 회의실");
		}
		
		//프로그램 등록
		groupService.registProgram(dto);
		
		
		//상담사번호 기준 가장 최근 업로드 데이터 찾기
		int prgNO = groupService.getProgramNo(dto.getCns_no());
		
		//스케줄 업데이트
		dto.setPrg_no(prgNO);
		
		for(int i = 0; i < dto.getPrg_nmtm(); i++) {
			dto.setPrg_ymd(dto.getGroupSCHDL().get(i));
			groupService.createSchedule(dto);
		}		
		
		//groupService.createSchedule(prg_no);

		return "redirect:/admin/registProgram";
	}
	
	
	//등록된 프로그램 확인
	@GetMapping("/programList")
	private String programList(Model model) {
		List<GroupDTO> list = groupService.adminPRGList();
		model.addAttribute("list", list);
		return "admin/group/programList";
	}
	
	//프로그램 내용 불러오기
	@PostMapping("/programList")
	@ResponseBody
	private GroupDTO showContent(@RequestParam("no") int no, Model model){
		
		//String content = groupService.showContent(no);
		GroupDTO adminDetail = groupService.adminDetail(no);
		//System.out.println("내용 : " + content);
		
		//Map<String, Object> response = new HashMap<String, Object>();
		//response.put("content", content);
	
		return adminDetail;
	}
	
	
	//승인
	@PostMapping("/approveProgram")
	private @ResponseBody GroupDTO approveProgram(@RequestParam Map<String, Object> map) {
				
		int prg_no = Integer.parseInt((String) map.get("no"));
		String approv = (String) map.get("val");

		Map<String, Object> status = new HashMap<String, Object>();
		status.put("prg_no", prg_no);
		
		if(approv.equals("0")) {
			groupService.approvePRG(prg_no);
			status.put("req_open", 1);
			groupService.changeReqOpen(status);
			
		} else if (approv.equals("1")) {
			groupService.disApprovePRG(prg_no);
			status.put("req_open", 0);
			groupService.changeReqOpen(status);
		} else {
			System.out.println("땡");
		}		
		return groupService.adminDetail(prg_no);
	}
	

	/*
	@RequestMapping(value = "/approveProgram", method = {RequestMethod.POST})
	private String approveProgram(Model model, @RequestParam Map<String, Object> map) {
		System.out.println(map.get("no"));
		System.out.println(map.get("val"));
				
		int prg_no = Integer.parseInt((String) map.get("no"));
		String approv = (String) map.get("val");

		Map<String, Object> status = new HashMap<String, Object>();
		status.put("prg_no", prg_no);
		
		if(approv.equals("0")) {
			//System.out.println("승인하기");
			groupService.approvePRG(prg_no);
			status.put("req_open", 1);
			groupService.changeReqOpen(status);
			//approv = "1";
			
		} else if (approv.equals("1")) {
			//System.out.println("승인취소하기");
			groupService.disApprovePRG(prg_no);
			status.put("req_open", 0);
			groupService.changeReqOpen(status);
			//approv = "0";
		} else {
			System.out.println("땡");
		}
		
		model.addAttribute("detail", groupService.adminDetail(prg_no));
		return "admin/group/programList :: #contentModal";
	}*/
	
	
	//프로그램 스케줄확인
	@RequestMapping(value = "/grSchedule")
	private @ResponseBody List<Map<String, Object>> grSchedule() {
		List<Map<String, Object>> scheduleList = groupService.scheduleList();
		return scheduleList;
	}
	
	
	//프로그램 참여자 확인
	@GetMapping("/programEntry")
	private String programEntry(Model model) {
		List<GroupDTO> programEntry = groupService.programEntry();
		model.addAttribute("programEntry", programEntry);
		
		return "admin/group/programEntry";
	}
	
	//프로그램별 참여자 확인
	@RequestMapping(value = "/programEntry", method = {RequestMethod.POST})
	private @ResponseBody String programEntry(@RequestParam("no") String no){
		
		List<Map<String, Object>> entry = groupService.entryList(no);
		//System.out.println(entry);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(entry);
		System.out.println(json);
		return json;
	}
	
	
}
