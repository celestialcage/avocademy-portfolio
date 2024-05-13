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

	GroupDTO updateprg(int prg_no);

}
