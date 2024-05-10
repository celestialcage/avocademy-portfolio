package com.avocado.web.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
}
