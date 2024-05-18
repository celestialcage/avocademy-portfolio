package com.avocado.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.CommunityDTO;
import com.avocado.web.repository.CommunityDAO;

@Service("communityService")
public class CommunityServiceImpl implements CommunityService{

	@Autowired
	private CommunityDAO communityDAO;
	
	@Override
	public void saveFileUpload(CommunityDTO communityDTO) {
		System.out.println("확인서비스");
		communityDAO.inserFile(communityDTO);
		
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return communityDAO.count();
	}

	@Override
	public List<CommunityDTO> community(int pageNo, int post) {
		// 1페이지면 글이 0번부터 ~ 9번까지
				// 2페이지면 10번부터 ~ 19번까지 => 10개씩
				// 기본 pageNo는 1로 시작
				// limit 을 설정 할 수 있게
				pageNo = (pageNo - 1) * post;
				System.out.println("pageNO : " + pageNo + " / post : " + post);
				Map<String, Integer> pageMap = new HashMap<>();
				pageMap.put("pageNo", pageNo);
				pageMap.put("post", post);
				
				List<CommunityDTO> list = communityDAO.community(pageMap);
				return list;
	}

	@Override
	public CommunityDTO detail(int cno) {
		return communityDAO.detail(cno);
	}

	@Override
	public int write(Map<String, Object> map) {
		return communityDAO.write(map);
	}

	@Override
	public int deletecd(String cno) {
		return communityDAO.deletecd(cno);
	}

}
