package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import com.avocado.web.entity.GroupDTO;

public interface GroupService {

	List<GroupDTO> programList();

	void registProgram(GroupDTO dto);

	List<GroupDTO> adminPRGList();

	void approvePRG(int prg_no);

	void disApprovePRG(int prg_no);

	void createSchedule(GroupDTO dto);

	int getProgramNo(String string);

	void changeReqOpen(Map<String, Object> status);

	GroupDTO programDetail(String no);

	List<Integer> getSchedulNo(String no);

	int apply(Map<String, Object> map);

	int checkSchedul(Map<String, Object> check);

	String getfield(String cns_no);

	String showContent(int no);

	List<Map<String, Object>> scheduleList();

	void closePRG(int prg_no);

	GroupDTO adminDetail(int no);

	List<GroupDTO> programEntry();

	List<Map<String, Object>> entryList(String no);

	String aplyNo();

	void sendApplyEmail(String email);
	

}
