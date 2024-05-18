package com.avocado.web.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.CommunityDTO;

@Repository
@Mapper
public class CommunityDAO {

	public void inserFile(CommunityDTO communityDTO) {
		System.out.println("확인다오");
		
		
	}

}
