package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailException;

import com.avocado.web.entity.GroupDTO;
import com.avocado.web.entity.MyinfoDTO;

public interface MyInfoService {

	void sendMail(String email, String title, String content) throws EmailException;

	public List<MyinfoDTO> getMyinfo(int uno);
	public List<MyinfoDTO> getMyinfo(String uno);

	public List<MyinfoDTO> myinfo(int pageNo, int post, int uno);

	public int count(int uno);

	public List<MyinfoDTO> getMyinfo(Map<String, Integer> uno);

	void sendEmail(String email, String key) throws EmailException;

	public List<GroupDTO> reservationList(String stud_no);

}
