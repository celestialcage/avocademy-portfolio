package com.avocado.web.service;

import org.apache.commons.mail.EmailException;

import com.avocado.web.entity.MailDTO;
import com.avocado.web.entity.UserDTO;

public interface MailService {


	String getEmail(String email);

	void setKey(UserDTO dto);
	
//	void sendEmail(String email, String key) throws.. EmailException;

	boolean verifyCode(String inputCode, String uid);
	
	boolean resetPassword(String newPassword, String uid);
	
	void sendApplyEmail(MailDTO dto) throws EmailException;
}
