package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import com.avocado.web.entity.ProfessorDTO;

public interface ProfessorService {

	List<ProfessorDTO> studentInfo(String uname);


	int savePs(Map<String, Object> map);


	List<ProfessorDTO> psSchedule(String uname);

	int pscReserved(Map<String, Object> map2);


	List<Map<String, Object>> getAll(Map<String, Object> map);


	List<ProfessorDTO> professorInfo(String uname);


	int registPsCounsel(Map<String, Object> map);


	List<ProfessorDTO> psCounselList(String uname);





}
