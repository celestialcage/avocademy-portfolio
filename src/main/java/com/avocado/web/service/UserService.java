package com.avocado.web.service;

import java.util.Map;


public interface UserService {

	Map<String, Object> login(Map<String, Object> map);

	String getStudentInfo(String uno);

	String getCounselorInfo(String uno);







}
