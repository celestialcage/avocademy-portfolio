package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import com.avocado.web.entity.ProfessorDTO;

public interface ProfessorService {

	List<ProfessorDTO> studentInfo();


	int savePs(Map<String, String> map);


}
