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
	public List<OnlineDTO> online() {
		List<OnlineDTO> list = onlineDAO.online();
		return list;
	}

	@Override
	public OnlineDTO detail(int bno) {
		return onlineDAO.detail(bno);
	}

	@Override
	public int write(Map<String, String> map) {
		return onlineDAO.write(map);
	}

	@Override
	public List<OnlineDTO> findAll(Map<String, Integer> map) {
		return onlineDAO.findAll(map);
	}

	@Override
	public int count() {
		return onlineDAO.count();
	}


}
