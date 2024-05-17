package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import com.avocado.web.entity.OnlineDTO;

public interface OnlineService {

	public List<OnlineDTO> online(int pageNo, int post);

	public OnlineDTO detail(int bno);

	public int write(Map<String, Object> map);

	public int count();

	public int deletecd(String bno);


}
