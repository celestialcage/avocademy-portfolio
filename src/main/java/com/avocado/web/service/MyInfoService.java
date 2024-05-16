package com.avocado.web.service;

import org.apache.commons.mail.EmailException;

public interface MyInfoService {

	void sendMail(String email, String title, String content) throws EmailException;


}
