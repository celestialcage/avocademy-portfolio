package com.avocado.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<MyinfoDTO> myinfo(int pageNo, int post) {
		// 1페이지면 글이 0번부터 ~ 9번까지
		// 2페이지면 10번부터 ~ 19번까지 => 10개씩
		// 기본 pageNo는 1로 시작
		// limit 을 설정 할 수 있게
		pageNo = (pageNo - 1) * post;
		System.out.println("pageNO : " + pageNo + " / post : " + post);
		Map<String, Integer> pageMap = new HashMap<>();
		pageMap.put("pageNo", pageNo);
		pageMap.put("post", post);

		List<MyinfoDTO> list = myinfoDAO.myinfo(pageMap);

		return list;
	}
	
	@Override
	public int count(int uno) {
		return myinfoDAO.count(uno);
	}

	@Override
	public List<MyinfoDTO> getMyinfo(String uno) {
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
