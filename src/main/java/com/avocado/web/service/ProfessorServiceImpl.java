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
	public List<ProfessorDTO> studentInfo() {
		return professorDAO.studentInfo();
	}

	@Override
	public int savePs(Map<String, String> map) {
		return professorDAO.savePs(map);
	}

	@Override
	public List<ProfessorDTO> psSchedule() {
		return professorDAO.psSchedule();
	}

	@Override
	public List<Map<String, Object>> getAll(String selectedDate) {
		return professorDAO.getAll(selectedDate);
	}

	@Override
	public int pscReserved(Map<String, String> map2) {
		return professorDAO.pscReserved(map2);
	}






	

	

	

	
	
}
