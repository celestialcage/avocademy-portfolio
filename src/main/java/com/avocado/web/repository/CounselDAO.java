package com.avocado.web.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.PersonalDTO;

@Repository
@Mapper
public interface CounselDAO {

	List<Map<String, Object>> findAllSchedule();

	List<Map<String, Object>> findEachSchedule(String cns_no);

	List<Map<String, Object>> findAllCounselors();

	Map<String, Object> findCsInfo(int user_no);

	List<Map<String, Object>> findAllTimes();

	int addSchedule(PersonalDTO ps);

	int findSchedule(PersonalDTO ps);

	int deleteSchedule(PersonalDTO ps);

	int applySchedule(PersonalDTO ps);

	List<PersonalDTO> findCslAppointments(int cns_no);

	void deleteExpiredSchedule(int hourNow);

	void changeExpiredSchedule(int hour);

	void changeExpiredAppointment(int hour);

}
