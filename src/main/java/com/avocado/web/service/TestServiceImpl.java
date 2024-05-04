package com.avocado.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.TestDTO;
import com.avocado.web.repository.TestDAO;

@Service("testService")
public class TestServiceImpl implements TestService {
	@Autowired
	private TestDAO testDAO;

	@Override
	public List<TestDTO> staff() {
		List<TestDTO> list = testDAO.staff();
		return list;
	}
	
}
