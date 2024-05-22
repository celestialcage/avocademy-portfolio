package com.avocado.web.service;

import java.util.List;

import com.avocado.web.entity.SurveyDTO;

public interface SurveyService {

	int insertResponse(SurveyDTO response);

	List<SurveyDTO> getAllResponses();

}
