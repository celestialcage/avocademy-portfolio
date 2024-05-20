package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.avocado.web.entity.CommunityDTO;
import com.avocado.web.entity.FilesDTO;

public interface CommunityService {

	
	int count();

	List<CommunityDTO> community(int pageNo, int post);

	CommunityDTO detail(int cno);


	int deletecd(String cno);


	Map<String, Object> write(Map<String, Object> map, FilesDTO dto, MultipartFile file);

}
