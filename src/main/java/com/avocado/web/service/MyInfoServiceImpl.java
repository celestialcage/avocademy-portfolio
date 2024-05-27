package com.avocado.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.GroupDTO;
import com.avocado.web.entity.MyinfoDTO;
import com.avocado.web.entity.UserDTO;
import com.avocado.web.repository.MyinfoDAO;
import com.avocado.web.util.SecureInfo;
import com.avocado.web.util.Util;

@Service
public class MyInfoServiceImpl implements MyInfoService {

	@Autowired
	private SecureInfo secureInfo;

	@Autowired
	private MyinfoDAO myinfoDAO;

	@Autowired
	private Util util;

	@Override
	public List<MyinfoDTO> myinfo(int pageNo, int post, int uno) {
		// 1페이지면 글이 0번부터 ~ 9번까지
		// 2페이지면 10번부터 ~ 19번까지 => 10개씩
		// 기본 pageNo는 1로 시작
		// limit 을 설정 할 수 있게
		pageNo = (pageNo - 1) * post;
		// System.out.println("pageNO : " + pageNo + " / post : " + post);
		Map<String, Integer> pageMap = new HashMap<>();
		pageMap.put("pageNo", pageNo);
		pageMap.put("post", post);
		pageMap.put("uno", uno);

		List<MyinfoDTO> list = myinfoDAO.myinfo(pageMap);

		return list;
	}



	@Override
	public List<MyinfoDTO> getMyinfo(int uno) {
		return myinfoDAO.getMyinfo(uno);
	}

	@Override
	public List<MyinfoDTO> getMyinfo(Map<String, Integer> uno) {
		return myinfoDAO.getMyinfo(uno);
	}

	@Override
	public List<GroupDTO> reservationList (String stud_no) {
	     return myinfoDAO.reservationList(stud_no);
	    }

	@Override
	public int count(int uno) {
		return myinfoDAO.count(uno);
	}
}
