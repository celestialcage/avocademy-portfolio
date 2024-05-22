package com.avocado.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.SurveyDTO;


@Mapper
@Repository
public interface SurveyDAO {
	
    int insertResponse(SurveyDTO response);

    List<SurveyDTO> getAllResponses();

}
