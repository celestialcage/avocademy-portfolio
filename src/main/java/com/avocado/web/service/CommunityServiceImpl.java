package com.avocado.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.CommunityDTO;
import com.avocado.web.repository.CommunityDAO;
@Service("CommunityService")
public class CommunityServiceImpl implements CommunityService{

	@Autowired
	private CommunityDAO communityDAO;
	
	@Override
	public void saveFileUpload(CommunityDTO communityDTO) {
		System.out.println("확인서비스");
		communityDAO.inserFile(communityDTO);
		
	}

}
