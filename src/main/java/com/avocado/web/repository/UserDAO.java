package com.avocado.web.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.CounselorDTO;
import com.avocado.web.entity.StudentDTO;

@Repository
@Mapper
public interface UserDAO {

	Map<String, Object> login(Map<String, Object>map);

	Map<String, Object> getStudentInfo(String uno);

	String getCounselorInfo(String uno);

	
}

