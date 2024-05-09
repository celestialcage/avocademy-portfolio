package com.avocado.web.service;

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

	

	

	

	
	
}
