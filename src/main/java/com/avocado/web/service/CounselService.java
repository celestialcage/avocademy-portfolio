package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import com.avocado.web.entity.CslSearchDTO;
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

	int applySchedule(PersonalDTO ps);

	List<PersonalDTO> findCslAppointments(int cns_no);

	List<PersonalDTO> findAllScheduleList(CslSearchDTO searchDTO);

	List<PersonalDTO> findCslScheduleList(CslSearchDTO searchDTO);

	int findCsAppTotalCount(CslSearchDTO searchDTO);

	int updateComment(PersonalDTO ps);

	PersonalDTO findCslSchedule(int str2Int);
	
	int confirmApply(PersonalDTO ps);

	int cancelReservation(PersonalDTO ps);

	int statusApply(PersonalDTO ps);

	int completeAppointment(PersonalDTO ps);

	int skipAppointment(PersonalDTO ps);

}
