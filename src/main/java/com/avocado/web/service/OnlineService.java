package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import com.avocado.web.entity.OnlineDTO;

public interface OnlineService {

	public List<OnlineDTO> online();

	public OnlineDTO detail(int bno);

	public int write(Map<String, String> map);

	public int count();

	public List<OnlineDTO> findAll(Map<String, Integer> map);
	
}
