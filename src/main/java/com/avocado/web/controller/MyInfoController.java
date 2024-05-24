package com.avocado.web.controller;

import java.util.List;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avocado.web.entity.CslSearchDTO;
import com.avocado.web.entity.MyinfoDTO;
import com.avocado.web.entity.UserDTO;
import com.avocado.web.service.MyInfoServiceImpl;
import com.avocado.web.util.SecureInfo;
import com.avocado.web.util.Util;

import jakarta.servlet.http.HttpSession;

@Controller

public class MyInfoController {

	@Autowired
	private Util util;
	
	@Autowired
	private SecureInfo secureInfo;
	
	@Autowired
	private MyInfoServiceImpl myInfoService;
	
	@Autowired
	private SecureInfo secureInfo;

	
	//마이페이지 이동
	@GetMapping("/myInfo")
	public String myInfo(Model model,
			 @RequestParam(name = "bno", required = false, defaultValue = "1") int bno,
			 @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
             @RequestParam(name = "post",defaultValue = "10") int post) {
		
		
		HttpSession session = util.getSession();
		
		int uno =  (int) (session.getAttribute("uno"));
	
		
		//System.out.println(session.getAttribute("uname"));
		//System.out.println(session.getAttribute("uno"));

		//총 글 갯수 확인해서 페이지 개수 계산!!!
		int totalCount = myInfoService.count(uno);
		// System.out.println("totalcount : " + totalCount);
		int totalPage = 1;

		if (totalCount % post == 0) {
			totalPage = totalCount / post;
		} else {
			totalPage = (totalCount / post) + 1;
		}

		// 페이지가 1보다 작으면 0이거나 음수면 1로 돌리기
		if (pageNo < 1) {
			pageNo = 1;
			System.out.println(pageNo  + ": pageNo");
		}
		// 페이지가 글의 총 개수보다 커지면 페이지는 글 최대 개수로 제한
		if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageNo", pageNo);
		
		List<MyinfoDTO> list = myInfoService.myinfo(pageNo, post, uno);
		
		// System.out.println(list);
		
		model.addAttribute("list",list );
		model.addAttribute("pageNo",pageNo );
		
		return "myinfo";
	}

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
			email = myInfoService.getEmail(uid);
			String key = util.createKey();

			System.out.println("emailAuth 컨트롤러 key : " + key);

			// UserDTO 객체 생성 및 설정
			UserDTO dto = new UserDTO();
			dto.setUemail(email);
			dto.setUkey(key);
			dto.setUid(uid);

			// 인증키를 데이터베이스에 저장
			myInfoService.setKey(dto);

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
		System.out.println("verifyCode 컨트롤러 오는지 보자" + inputCode);
		String uid = (String) util.getSession().getAttribute("uid");
		System.out.println("verifyCode 컨트롤러 오는지 보자" + uid);
		boolean isCodeCorrect = myInfoService.verifyCode(inputCode, uid);
		System.out.println(isCodeCorrect);
		if (isCodeCorrect) {
			return ResponseEntity.ok(true);

		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false); // 코드가 일치하지 않는 경우
		}
	}

	@PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam("newPassword") String newPassword) {
       
		String uid = (String) util.getSession().getAttribute("uid");
		// 비밀번호 변경 서비스 호출
		boolean success = myInfoService.resetPassword(newPassword, uid);
		System.out.println("컨트롤러 새비번1  "+newPassword);
	    if (success) {
	        // 비밀번호 변경이 성공한 경우
	    	System.out.println("컨트롤러 새비번 성공"+newPassword);
	        return ResponseEntity.ok("1");
	    } else {
	        // 비밀번호 변경이 실패한 경우
	    	System.out.println("컨트롤러 새비번 실패"+newPassword);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("0");
	    }
	}

	
	@GetMapping("/profileEdit")
	public String profileEdit() {
		return "myinfo/profileEdit";
	}
	
	@GetMapping("/reservationList")
	   public String reservationList(Model model) {
	      
			// 로그인확인 조건문 쓰기 세션에 로그인 되어있는지 확인
			HttpSession session = util.getSession();

			// if문으로
			session.getAttribute("stud_no");

			CslSearchDTO dto = new CslSearchDTO();

			if (session.getAttribute("stud_no") == null) {
				return "redirect:/login";

			} else {

				int stud_no = (int) session.getAttribute("stud_no");

				List<CslSearchDTO> reservationList = myInfoService.reservationList(stud_no);
				model.addAttribute("reservationList", reservationList);
				dto.setStud_no(session.getAttribute("stud_no").toString());
				return "myinfo/reservationList";

			}

		}
	
	@GetMapping("/testList")
	public String testList() {
		return "myinfo/testList";
	}
}

