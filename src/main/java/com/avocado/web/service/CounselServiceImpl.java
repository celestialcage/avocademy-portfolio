package com.avocado.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.CslSearchDTO;
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

	@Override
	public List<PersonalDTO> findCslAppointments(int cns_no) {
		return counselDAO.findCslAppointments(cns_no);
	}

	@Override
	public List<PersonalDTO> findAllScheduleList(CslSearchDTO searchDTO) {
		return counselDAO.findAllScheduleList(searchDTO);
	}

	@Override
	public List<PersonalDTO> findCslScheduleList(CslSearchDTO searchDTO) {
		return counselDAO.findCslScheduleList(searchDTO);
	}

	@Override
	public int findCsAppTotalCount(CslSearchDTO searchDTO) {
		return counselDAO.findCsAppTotalCount(searchDTO);
	}

	@Override
	public int updateComment(PersonalDTO ps) {
		
		// 지난 회차(aply_no_old) 존재하는지 확인, 상담 회차도 가져오기
		PersonalDTO lastPs = counselDAO.checkLastCs(ps);
//		System.out.println(ps.getAply_no());
//		System.out.println(lastPs.getDscsn_nmtm());
		
		// 그러고보니 이미 상담 회차가 있으면 넣으면 안됨.
		if(ps.getDscsn_nmtm() == 0) {
			// 없으면 1 세팅, 있으면 이전 회차 + 1 (없을때 0이어서 그냥,, 하면될듯)
			if(ps.getAply_no_old() == 0) {
				ps.setDscsn_nmtm(1);
			} else {
				ps.setDscsn_nmtm(lastPs.getDscsn_nmtm() + 1);
			}
			
//			System.out.println(ps.getAply_no_old());
//			System.out.println(lastPs.getDscsn_nmtm());
			int result = counselDAO.setDscsn_nmtm(ps);
			System.out.println("상담 회차 세팅 결과: " + result);
		}
		
		return counselDAO.updateComment(ps);
	}

	@Override
	public PersonalDTO findCslSchedule(int aply_no) {
		return counselDAO.findCslSchedule(aply_no);
	}

	@Override
	public int confirmApply(PersonalDTO ps) {
		return counselDAO.confirmApply(ps);
	}

	@Override
	public int cancelReservation(PersonalDTO ps) {
		return counselDAO.cancelReservation(ps);
	}

	@Override
	public int statusApply(PersonalDTO ps) {
		return counselDAO.statusApply(ps);
	}

	@Override
	public int completeAppointment(PersonalDTO ps) {
		return counselDAO.completeAppointment(ps);
	}

	@Override
	public int skipAppointment(PersonalDTO ps) {
		return counselDAO.skipAppointment(ps);
	}
	
}
