package com.avocado.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avocado.web.entity.ProfessorDTO;

public interface ProfessorService {

	List<ProfessorDTO> studentInfo();


	int savePs(Map<String, String> map);


	List<ProfessorDTO> psSchedule();


	List<Map<String, Object>> getAll(String selectedDate);


	int pscReserved(Map<String, String> map2);





}
