package com.avocado.web.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.avocado.web.entity.CslSearchDTO;
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

	List<PersonalDTO> findAllScheduleList(CslSearchDTO searchDTO);

	List<PersonalDTO> findCslScheduleList(CslSearchDTO searchDTO);

	int findCsAppTotalCount(CslSearchDTO searchDTO);

	int updateComment(PersonalDTO ps);

	PersonalDTO findCslSchedule(int aply_no);
	
	int confirmApply(PersonalDTO ps);

	int cancelReservation(PersonalDTO ps);

	PersonalDTO checkLastCs(PersonalDTO ps);

	void setDscsn_nmtm(PersonalDTO ps);

	int statusApply(PersonalDTO ps);

	int completeAppointment(PersonalDTO ps);

	int skipAppointment(PersonalDTO ps);

}
