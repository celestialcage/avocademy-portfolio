package com.avocado.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.repository.UserDAO;
import com.avocado.web.util.SecureInfo;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SecureInfo secureInfo;
	
	@Override
	public Map<String, Object> login(Map<String, Object> map) {
		map.put("encryptKey", secureInfo.getEncryptKey());
		return userDAO.login(map);
	}

	@Override
	public Map<String, Object> getStudentInfo(String uno) {
		return userDAO.getStudentInfo(uno);
	}

	@Override
	public String getCounselorInfo(String uno) {
		return userDAO.getCounselorInfo(uno);
	}



}
