package com.avocado.web.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.GroupDTO;
import com.avocado.web.repository.GroupDAO;

@Service("groupService")
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupDAO groupDAO;

	@Override
	public List<GroupDTO> programList() {
		return groupDAO.programList();
	}

	@Override
	public void registProgram(GroupDTO dto) {
		groupDAO.registProgram(dto);		
	}

	@Override
	public List<GroupDTO> adminPRGList() {
		List<GroupDTO> list = groupDAO.adminPRGList();
		for (GroupDTO groupDTO : list) {
			LocalDate endDate = LocalDate.parse(groupDTO.getPrg_end(), java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalDate reqEnd = LocalDate.parse(groupDTO.getReq_end(), java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
			LocalDate today = LocalDate.now();
			
			if(endDate.isBefore(today)) {
				groupDTO.setPrg_aprv("3");
			} else if(reqEnd.isBefore(today)) {
				groupDTO.setPrg_aprv("2");
			}
		}
		
		return list;
	}

	@Override
	public void approvePRG(int prg_no) {
		groupDAO.approvePRG(prg_no);
		
	}

	@Override
	public void disApprovePRG(int prg_no) {
		groupDAO.disApprovePRG(prg_no);
		
	}

	@Override
	public void createSchedule(GroupDTO dto) {
		groupDAO.createSchedule(dto);
	}

	@Override
	public int getProgramNo(String cns_no) {
		return groupDAO.getProgramNo(cns_no);
	}

	@Override
	public void changeReqOpen(Map<String, Object> status) {
		groupDAO.changeReqOpen(status);
	}

	@Override
	public GroupDTO programDetail(String no) {
		return groupDAO.programDetail(no);
	}

	@Override
	public List<Integer> getSchedulNo(String no) {
		return groupDAO.getSchedulNo(no);
	}

	@Override
	public int apply(Map<String, Object> map) {
		return groupDAO.apply(map);		
	}

	@Override
	public int checkSchedul(Map<String, Object> check) {
		return groupDAO.checkSchedul(check);
	}

	@Override
	public String getfield(String cns_no) {
		return groupDAO.getfield(cns_no);
	}

	@Override
	public String showContent(int no) {
		return groupDAO.showContent(no);
	}

	@Override
	public List<Map<String, Object>> scheduleList() {
		return groupDAO.scheduleList();
	}

	@Override
	public void closePRG(int prg_no) {
		groupDAO.closePRG(prg_no);		
	}



	



}
