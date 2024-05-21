package com.avocado.web.service;

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
		// counselDAO.deleteExpiredSchedule(LocalTime.now().getHour());
	}
}
