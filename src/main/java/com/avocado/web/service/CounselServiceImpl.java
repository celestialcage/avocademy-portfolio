package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.PersonalDTO;
import com.avocado.web.repository.CounselDAO;

@Service("counselService")
public class CounselServiceImpl implements CounselService {
	@Autowired
	private CounselDAO counselDAO;

	@Override
	public List<Map<String, Object>> findAllSchedule() {
		return counselDAO.findAllSchedule();
	}

	@Override
	public List<Map<String, Object>> findEachSchedule(String cns_no) {
		return counselDAO.findEachSchedule(cns_no);
	}

	@Override
	public List<Map<String, Object>> findAllCounselors() {
		return counselDAO.findAllCounselors();
	}

	@Override
	public Map<String, Object> findCsInfo(int user_no) {
		return counselDAO.findCsInfo(user_no);
	}

	@Override
	public List<Map<String, Object>> findAllTimes() {
		return counselDAO.findAllTimes();
	}

	@Override
	public int addSchedule(PersonalDTO ps) {
		return counselDAO.addSchedule(ps);
	}

	@Override
	public int findSchedule(PersonalDTO ps) {
		return counselDAO.findSchedule(ps);
	}

	@Override
	public int deleteSchedule(PersonalDTO ps) {
		return counselDAO.deleteSchedule(ps);
	}

	@Override
	public int applySchedule(PersonalDTO ps) {
		// 상담사한테 메일
		
		return counselDAO.applySchedule(ps);
	}

}
