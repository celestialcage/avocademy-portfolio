package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.OnlineDTO;
import com.avocado.web.repository.OnlineDAO;

@Service("onlineService")
public class OnlineServiceImpl implements OnlineService {
	@Autowired
	private OnlineDAO onlineDAO;

	@Override
	public List<OnlineDTO> online(int pageNo, int post) {
		// 1페이지면 글이 0번부터 ~ 9번까지
		// 2페이지면 10번부터 ~ 19번까지 => 10개씩
		// 기본 pageNo는 1로 시작
		// limit 을 설정 할 수 있게
		pageNo = (pageNo - 1) * post;
		
		List<OnlineDTO> list = onlineDAO.online(pageNo, post);
		return list;
	}

	@Override
	public OnlineDTO detail(int bno) {
		return onlineDAO.detail(bno);
	}

	@Override
	public int write(Map<String, Object> map) {
		return onlineDAO.write(map);
	}

	@Override
	public int count() {
		return onlineDAO.count();
	}

	@Override
	public int deletecd(String bno) {
		return onlineDAO.deletecd(bno);

	}
}
