 package com.avocado.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.ProfessorDTO;
import com.avocado.web.repository.ProfessorDAO;

@Service ("professorService")
public class ProfessorServiceImpl implements ProfessorService {

	@Autowired
	private ProfessorDAO professorDAO;
	
	@Override 
	public List<ProfessorDTO> studentInfo(String uname) {
		return professorDAO.studentInfo(uname);
	}

	@Override
	public int savePs(Map<String, Object> map) {
		return professorDAO.savePs(map);
	}

	@Override
	public List<ProfessorDTO> psSchedule(ProfessorDTO pf) {
		return professorDAO.psSchedule(pf);
	}


	@Override
	public int pscReserved(Map<String, Object> map2) {
		return professorDAO.pscReserved(map2);
	}

	@Override
	public List<Map<String, Object>> getAll(Map<String, Object> map) {
		return professorDAO.getAll(map);
	}

	@Override
	public List<ProfessorDTO> professorInfo(int uno) {
		return professorDAO.professorInfo(uno);
	}

	@Override
	public int registPsCounsel(Map<String, Object> map) {
		return professorDAO.registPsCounsel(map);
	}

	@Override
	public List<ProfessorDTO> psCounselList(String uname) {
		return professorDAO.psCounselList(uname);
	}

	@Override
	public int changeStatus(Map<String, Object> map) {
		return professorDAO.changeStatus(map);
	}

	@Override
	public List<Map<String, Object>> psTimeList(Map<String, Object> map) {
		return professorDAO.psTimeList(map);
	}

	@Override
	public List<ProfessorDTO> studentList(int uno) {
		return professorDAO.studentList(uno);
	}

	@Override
	public int checkSchedule(Map<String, Object> map) {
		return professorDAO.checkSchedule(map);
	}

	
	

	






	

	

	

	
	
}
