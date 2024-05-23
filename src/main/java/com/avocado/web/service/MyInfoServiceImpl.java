package com.avocado.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avocado.web.entity.CslSearchDTO;
import com.avocado.web.entity.MyinfoDTO;
import com.avocado.web.entity.UserDTO;
import com.avocado.web.repository.MyinfoDAO;
import com.avocado.web.util.SecureInfo;
import com.avocado.web.util.Util;

@Service
public class MyInfoServiceImpl implements MyInfoService {

	@Autowired
	private SecureInfo secureInfo;

	@Autowired
	private MyinfoDAO myinfoDAO;
	
	@Autowired
	private Util util;

	@Override
	public List<MyinfoDTO> myinfo(int pageNo, int post, int uno) {
		// 1페이지면 글이 0번부터 ~ 9번까지
		// 2페이지면 10번부터 ~ 19번까지 => 10개씩
		// 기본 pageNo는 1로 시작
		// limit 을 설정 할 수 있게
		pageNo = (pageNo - 1) * post;
		// System.out.println("pageNO : " + pageNo + " / post : " + post);
		Map<String, Integer> pageMap = new HashMap<>();
		pageMap.put("pageNo", pageNo);
		pageMap.put("post", post);
		pageMap.put("uno", uno);
		
		List<MyinfoDTO> list = myinfoDAO.myinfo(pageMap);

		return list;
	}

//	@Override
//	public List<MyinfoDTO> getMyinfo(String uno) {

	/*
	 * @Override public void sendMail(String email, String title, String content)
	 * throws EmailException {
	 * 
	 * SimpleEmail mail = new SimpleEmail();// 전송할 메인 mail.setCharset("UTF-8");
	 * mail.setDebug(true); mail.setHostName((String)
	 * secureInfo.mailInfo().get("smtp-mail.outlook.com"));// 보내는 서버 설정 = 고정
	 * mail.setAuthentication((String) secureInfo.mailInfo().get("emailAddr"),
	 * (String) secureInfo.mailInfo().get("pw"));// 보내는 사람 인증 = 고정
	 * mail.setSmtpPort(587);// 사용할 port번호 mail.setStartTLSEnabled(true);// 인증방법 =
	 * 고정 mail.setFrom((String) secureInfo.mailInfo().get("emailAddr"), (String)
	 * secureInfo.mailInfo().get("name")); mail.addTo(email);// 받는사람
	 * mail.setSubject(title);// 제목 mail.setMsg(content);// 내용 text
	 * 
	 * mail.send();
	 * 
	 * }
	 */
	
	@Override
	public List<MyinfoDTO> getMyinfo(int uno) {
		return myinfoDAO.getMyinfo(uno);
	}

	@Override
	public List<MyinfoDTO> getMyinfo(Map<String, Integer> uno) {
		return myinfoDAO.getMyinfo(uno);
	}

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
		return myinfoDAO.getEmail(email);
	}
	@Override
	public void setKey(UserDTO dto) {
		System.out.println("emailAuth>setKey 서비스");
		 myinfoDAO.setKey(dto); // 데이터베이스에 키 저장
		
	}

	@Override
	public boolean verifyCode(String inputCode, String uid) {
		System.out.println("verifyCode 서비스 오는지 보자" + inputCode);
	
		System.out.println("verifyCode 서비스 오는지 보자" + uid);
		 UserDTO user = myinfoDAO.verifyCode(uid);
		 
        if (user != null && user.getUkey().equals(inputCode)) {
        	 return true; // 코드가 일치하면 true 반환
        } else {
            return false; // 코드가 일치하지 않으면 false 반환
        }
	}



	public boolean resetPassword(String uid, String newPassword) {

		UserDTO user = myinfoDAO.resetPassword(newPassword,uid);
		if (user != null) {
            // 사용자를 찾은 경우 새로운 비밀번호로 업데이트합니다.
            user.setUpw(newPassword);
            myinfoDAO.save(user);
            return true;
        } else {
            // 사용자를 찾지 못한 경우 비밀번호 변경에 실패합니다.
            return false;
        }
	}
}
