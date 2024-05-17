package com.avocado.web.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.coobird.thumbnailator.Thumbnailator;

@Component
public class Util {
	public int str2Int(String str) {
		try {
			return Integer.parseInt(str);

		} catch (Exception e) {

			return 0;
		}

	}

	public HttpServletRequest req() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		return request;
	}

	public HttpSession getSession() {
		HttpSession session = req().getSession();
		return session;
	}

	public int str2Int(Object obj) {

		return str2Int(String.valueOf(obj));
	}

	public String createKey() {
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		String key = "" + r.nextInt(10) + r.nextInt(10) + r.nextInt(10) + r.nextInt(10);
		return key;
	}

	// 파일에 저장 (uuid+파일명)
	public String fileUpload(MultipartFile upFile) {

		// 경로 저장
		String root = req().getSession().getServletContext().getRealPath("/"); // 루트 경로
		String upfilePath = root + "resources\\upfile\\"; // 파일 경로

		// uuid 뽑기
		UUID uuid = UUID.randomUUID();
		// uuid 를 포함한 파일명
		String newFileName = uuid.toString() + upFile.getOriginalFilename();

		// 실제 업로드
		File file = new File(upfilePath, newFileName);

		if (file.exists() == false) {
			file.mkdirs(); // 만약 내가 설정한 경로에 예를 들어 upfile 이라는 폴더가 없다면 얘기 폴더까지 만들어줌.
			// s 없으면 해당 폴더만 만들어주고, s 있으면 상위 폴더까지 다 만들어줌
		}

		try {
			FileOutputStream thumbnail = new FileOutputStream(new File(upfilePath, "s_" + newFileName));
			Thumbnailator.createThumbnail(upFile.getInputStream(), thumbnail, 100, 100);
			thumbnail.close();

			upFile.transferTo(file);

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newFileName;
	}
	
	
}
