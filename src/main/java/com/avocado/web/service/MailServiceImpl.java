package com.avocado.web.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.MailDTO;
import com.avocado.web.entity.UserDTO;
import com.avocado.web.repository.MailDAO;
import com.avocado.web.util.SecureInfo;
import com.avocado.web.util.Util;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private SecureInfo secureInfo;

	@Autowired
	private MailDAO mailDAO;

	@Autowired
	private Util util;
	
//	//메일 보내기
//	@Override
//	public void sendEmail(String email, String key) {
//		
//		System.out.println("emailAuth>sendEmail 서비스 : " + email);
//		System.out.println("emailAuth>sendEmail 서비스 : " + key);
//		
//	}

	@Override
	public String getEmail(String email) {
		System.out.println("emailAuth>getEmail 서비스 : " + email);
		return mailDAO.getEmail(email);
	}

	@Override
	public void setKey(UserDTO dto) {
		System.out.println("emailAuth>setKey 서비스");
		mailDAO.setKey(dto); // 데이터베이스에 키 저장

	}

	@Override
	public boolean verifyCode(String inputCode, String uid) {
		System.out.println("verifyCode 서비스 오는지 보자" + inputCode);

		System.out.println("verifyCode 서비스 오는지 보자" + uid);
		UserDTO user = mailDAO.verifyCode(uid);

		if (user != null && user.getUkey().equals(inputCode)) {
			return true; // 코드가 일치하면 true 반환
		} else {
			return false; // 코드가 일치하지 않으면 false 반환
		}
	}

	@Override
	public boolean resetPassword(String newPassword, String uid) {
		
		
		 Map<String, Object> params = new HashMap<>();
		    params.put("newPassword", newPassword);
		    params.put("uid", uid);
		    System.out.println("서비스 새비번1"+newPassword);
		int rowsAffected  = mailDAO.resetPassword(params);
		if (rowsAffected  > 0) {
			System.out.println("서비스 새비번 성공"+newPassword);
			// 사용자를 찾은 경우 새로운 비밀번호로 업데이트합니다.

			return true;
		} else {
			System.out.println("서비스 새비번 실패"+newPassword);
			// 사용자를 찾지 못한 경우 비밀번호 변경에 실패합니다.
			return false;
		}
	}

	@Override
	public void sendApplyEmail(MailDTO dto) throws EmailException {
		secureInfo.sendAplyEmail(dto);
	}


}
