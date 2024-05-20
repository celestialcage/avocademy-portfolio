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
	public List<ProfessorDTO> psSchedule(String uname) {
		return professorDAO.psSchedule(uname);
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
	public List<ProfessorDTO> professorInfo(String uname) {
		return professorDAO.professorInfo(uname);
	}

	@Override
	public int registPsCounsel(Map<String, Object> map) {
		return professorDAO.registPsCounsel(map);
	}

	






	

	

	

	
	
}
