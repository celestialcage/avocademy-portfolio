package com.avocado.web.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.GroupDTO;

@Mapper
@Repository
public interface GroupDAO {
	
	List<GroupDTO> programList();

	void registProgram(GroupDTO dto);

	List<GroupDTO> adminPRGList();

	void approvePRG(int prg_no);

	void disApprovePRG(int prg_no);

	void createSchedule(GroupDTO dto);

	int getProgramNo(String cns_no);

	void openPRG(int prg_no);

	GroupDTO programDetail(String no);

	List<Integer> getSchedulNo(String no);

	void apply(Map<String, Object> map);

	int checkSchedul(Map<String, Object> check);

}
