package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import com.avocado.web.entity.CommunityDTO;

public interface CommunityService {

	void saveFileUpload(CommunityDTO communityDTO);

	int count();

	List<CommunityDTO> community(int pageNo, int post);

	CommunityDTO detail(int cno);

	int write(Map<String, Object> map);

	int deletecd(String cno);

}
