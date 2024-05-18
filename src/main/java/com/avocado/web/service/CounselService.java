package com.avocado.web.service;

import java.util.List;
import java.util.Map;

public interface CounselService {

	List<Map<String, Object>> findAllSchedule();

	List<Map<String, Object>> findEachSchedule(String cns_no);

	List<Map<String, Object>> findAllCounselors();

}
