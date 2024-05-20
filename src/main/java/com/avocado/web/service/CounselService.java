package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import com.avocado.web.entity.PersonalDTO;

public interface CounselService {

	List<Map<String, Object>> findAllSchedule();

	List<Map<String, Object>> findEachSchedule(String cns_no);

	List<Map<String, Object>> findAllCounselors();

	Map<String, Object> findCsInfo(int user_no);

	List<Map<String, Object>> findAllTimes();

	int addSchedule(PersonalDTO ps);

	int findSchedule(PersonalDTO ps);

	int deleteSchedule(PersonalDTO ps);

}
