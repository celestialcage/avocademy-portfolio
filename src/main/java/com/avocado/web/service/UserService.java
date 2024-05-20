package com.avocado.web.service;

import java.util.Map;


public interface UserService {

	Map<String, Object> login(Map<String, Object> map);

	Map<String, Object> getStudentInfo(String uno);

	String getCounselorInfo(String uno);







}
