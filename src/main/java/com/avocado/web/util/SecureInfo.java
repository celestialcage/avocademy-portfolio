package com.avocado.web.util;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class SecureInfo {
	private String encryptKey = "keto";
	
	public String getEncryptkey() {
		return encryptKey;
	}
}
