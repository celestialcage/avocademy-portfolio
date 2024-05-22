package com.avocado.web.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.avocado.web.repository.CounselDAO;

@Service
public class SchedulerService {
	
	@Autowired
	private CounselDAO counselDAO;

	@Scheduled(cron = "0 0 9-18 ? * 1-5")
	public void performTask() {
		// 평일 9시~18시 1시간마다 실행
		System.out.println(LocalDateTime.now() + "에 지정된 스케줄 메서드");
		 counselDAO.deleteExpiredSchedule(LocalTime.now().getHour()); // 신청 안들어온 스케줄 삭제
		 counselDAO.changeExpiredSchedule(LocalTime.now().getHour()); // 신청했지만 승인되지 않은 스케줄 취소로 변경
		 counselDAO.changeExpiredAppointment(LocalTime.now().getHour()); // 예약됐지만 완료되지 않은 스케줄 미진행으로 변경
		 System.out.println(LocalDateTime.now() + "에 실행된 스케줄 메서드");
		 
		 // 지도교수 스케줄 관리...
	}
}
