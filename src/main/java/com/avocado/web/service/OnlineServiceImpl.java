package com.avocado.web.service;

import java.util.List;

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
	
}
