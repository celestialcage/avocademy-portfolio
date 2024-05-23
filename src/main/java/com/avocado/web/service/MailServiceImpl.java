package com.avocado.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.UserDTO;
import com.avocado.web.repository.MailDAO;
import com.avocado.web.util.Util;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
	private Util util;
	
	@Autowired
	private MailDAO mailDAO;

//	@Override
//	public int sendEmail() {
//		System.out.println("emailAuth_");
//		if(util.getSession().getAttribute("uid") != null) {
//			String email = getEmail((String)util.getSession().getAttribute("uid"));
//			String key = util.createKey();
//
//			UserDTO dto = new UserDTO();			
//			dto.setUemail(email);
//			dto.setUkey(key);
//			dto.setUid((String) util.getSession().getAttribute("uid"));
//			mailDAO.setKey(dto);//db에 키저장
//			
//			return 1;
//		}else {
//			return 0;
//		}
//	}
//
//	private String getEmail(String email) {		
//		return mailDAO.getEmail(email);
//	}
}
