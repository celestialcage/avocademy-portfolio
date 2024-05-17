package com.avocado.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.repository.UserDAO;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SecureInfo secureInfo;
	
	@Override
	public Map<String, Object> login(Map<String, Object> map) {
		map.put("encryptKey", secureInfo.getEncryptkey());
		return userDAO.login(map);
	}

}
