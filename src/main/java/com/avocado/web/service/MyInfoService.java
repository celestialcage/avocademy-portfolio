package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import com.avocado.web.entity.CslSearchDTO;
import com.avocado.web.entity.MyinfoDTO;

public interface MyInfoService {


	public List<MyinfoDTO> getMyinfo(int uno);
	public List<MyinfoDTO> getMyinfo(String uno);

	public List<MyinfoDTO> myinfo(int pageNo, int post, int uno);

	public int count(int uno);

	public List<MyinfoDTO> getMyinfo(Map<String, Integer> uno);

	List<CslSearchDTO> reservationList(int stud_no);


}
