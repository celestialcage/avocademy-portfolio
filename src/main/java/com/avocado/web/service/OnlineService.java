package com.avocado.web.service;

import java.util.List;

import com.avocado.web.entity.OnlineDTO;

public interface OnlineService {

	public List<OnlineDTO> online();

	public OnlineDTO detail(int bno);
	
}
