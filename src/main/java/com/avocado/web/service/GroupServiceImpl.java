package com.avocado.web.service;

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
		return groupDAO.adminPRGList();
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
	public void openPRG(int prg_no) {
		groupDAO.openPRG(prg_no);
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
	public void apply(Map<String, Object> map) {
		groupDAO.apply(map);		
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



	



}
