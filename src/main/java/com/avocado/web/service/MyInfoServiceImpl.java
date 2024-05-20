package com.avocado.web.service;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.util.SecureInfo;

@Service
public class MyInfoServiceImpl implements MyInfoService {
	
	@Autowired
	private SecureInfo secureInfo;

	@Override
	
		public void sendMail(String email, String title, String content) throws EmailException {	
		
		SimpleEmail mail = new SimpleEmail();// 전송할 메인
			mail.setCharset("UTF-8");
			mail.setDebug(true);
			mail.setHostName((String) secureInfo.mailInfo().get("smtp-mail.outlook.com"));// 보내는 서버 설정 = 고정
			mail.setAuthentication((String) secureInfo.mailInfo().get("emailAddr"), (String) secureInfo.mailInfo().get("pw"));// 보내는 사람 인증 = 고정
			mail.setSmtpPort(587);// 사용할 port번호
			mail.setStartTLSEnabled(true);// 인증방법 = 고정
			mail.setFrom((String) secureInfo.mailInfo().get("emailAddr"), (String) secureInfo.mailInfo().get("name"));
			mail.addTo(email);// 받는사람
			mail.setSubject(title);// 제목
			mail.setMsg(content);// 내용 text
			
			
			
			mail.send();	
		
	
		}

}
