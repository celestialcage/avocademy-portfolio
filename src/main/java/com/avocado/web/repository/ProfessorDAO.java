package com.avocado.web.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.ProfessorDTO;

@Repository
@Mapper

public interface ProfessorDAO {

	List<ProfessorDTO> studentInfo();


	int savePs(Map<String, String> map);
		
}
