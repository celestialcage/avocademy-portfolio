package com.avocado.web.controller;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.UserDTO;
import com.avocado.web.service.MailServiceImpl;
import com.avocado.web.util.SecureInfo;
import com.avocado.web.util.Util;

@Controller

public class MailController {

	@Autowired
	private Util util;
	
	@Autowired
	private SecureInfo secureInfo;
	
	@Autowired
	private MailServiceImpl mailService;

	
		@GetMapping("/mail")
	public String mail() {
		if (util.getSession().getAttribute("uid") != null) {
			System.out.println("get메일");
			return "mail";
		} else {
			return "redirect:/login?error=error";
		}
	}

	@PostMapping("/emailAuth")
	public ResponseEntity<String> emailAuth(@RequestParam("email") String email) throws EmailException {

		// 세션에서 사용자 id가져오기

		String uid = (String) util.getSession().getAttribute("uid");

		if (uid != null) {

			System.out.println("emailAuth 컨트롤러 email : " + email);

			// 이메일 및 인증 키 생성
			email = mailService.getEmail(uid);
			String key = util.createKey();

			//System.out.println("emailAuth 컨트롤러 key : " + key);

			// UserDTO 객체 생성 및 설정
			UserDTO dto = new UserDTO();
			dto.setUemail(email);
			dto.setUkey(key);
			dto.setUid(uid);

			// 인증키를 데이터베이스에 저장
			mailService.setKey(dto);

			// 이메일 전송
			secureInfo.sendEmail(email, key);

			return ResponseEntity.ok("1"); // 성공적으로 처리되었을 때 "1" 반환
		} else {
			return ResponseEntity.ok("0"); // 실패했을 때 "0" 반환
		}
	}

	// 인증번호 인증
	@PostMapping("/verifyCode")
	public ResponseEntity<Boolean> verifyCode(@RequestParam("inputCode") String inputCode) {
		//System.out.println("verifyCode 컨트롤러 오는지 보자" + inputCode);
		String uid = (String) util.getSession().getAttribute("uid");
		//System.out.println("verifyCode 컨트롤러 오는지 보자" + uid);
		boolean isCodeCorrect = mailService.verifyCode(inputCode, uid);
		//System.out.println(isCodeCorrect);
		if (isCodeCorrect) {
			return ResponseEntity.ok(true);

		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false); // 코드가 일치하지 않는 경우
		}
	}

	@PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam("newPassword") String newPassword) {
       
		String uid = (String) util.getSession().getAttribute("uid");
		String encryptKey = secureInfo.getEncryptKey(); // SecureInfo 클래스에서 암호화 키를 가져옵니다.
		  
		 //System.out.println("컨트롤러 Encrypt Key: " + encryptKey);

		 // 비밀번호 변경 서비스를 호출하여 암호화된 비밀번호를 데이터베이스에 저장합니다.
		boolean success = mailService.resetPassword(newPassword, uid);
		System.out.println("컨트롤러 새비번1  "+newPassword);
	    if (success) {
	        // 비밀번호 변경이 성공한 경우
	    	//System.out.println("컨트롤러 새비번 성공"+newPassword);
	        return ResponseEntity.ok("1");
	    } else {
	        // 비밀번호 변경이 실패한 경우
	    	//System.out.println("컨트롤러 새비번 실패"+newPassword);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("0");
	    }
	}

}

