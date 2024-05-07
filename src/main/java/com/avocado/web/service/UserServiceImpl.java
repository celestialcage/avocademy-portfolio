package com.avocado.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.repository.UserDAO;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDAO;
	@Override
	public Map<String, Object> login(Map<String, Object> map) {
		System.out.println("로그인 서비스");
		return userDAO.login(map);
	}

}
