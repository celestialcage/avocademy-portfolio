package com.avocado.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.SurveyDTO;
import com.avocado.web.repository.SurveyDAO;

@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {
	
	@Autowired
	private SurveyDAO surveyDAO;

	@Override
	public int insertResponse(SurveyDTO response) {
		return surveyDAO.insertResponse(response);
	}

	@Override
	public List<SurveyDTO> getAllResponses() {
		return surveyDAO.getAllResponses();
	}
	


	



}
