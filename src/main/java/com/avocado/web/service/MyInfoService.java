package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailException;

import com.avocado.web.entity.MyinfoDTO;
import com.avocado.web.entity.UserDTO;

public interface MyInfoService {

	public List<MyinfoDTO> getMyinfo(String uno);

	public List<MyinfoDTO> myinfo(int pageNo, int post);

	public int count(int uno);

	public List<MyinfoDTO> getMyinfo(Map<String, Integer> uno);

	String getEmail(String email);

	void setKey(UserDTO dto);

	boolean verifyCode(String inputCode, String uid);

	

//	void sendEmail(String email, String key) throws EmailException;

}
