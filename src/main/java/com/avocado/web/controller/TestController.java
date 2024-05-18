package com.avocado.web.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avocado.web.service.TestService;
import com.avocado.web.util.SecureInfo;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private SecureInfo info;

	@Resource(name="testService")
	private TestService testService;
	
	@GetMapping("/test1")
	public String test1(Model model) throws IOException, ParseException {
		StringBuilder urlBuilder = new StringBuilder("https://www.career.go.kr/inspct/openapi/test/questions");
		urlBuilder.append("?apikey=" + URLEncoder.encode(info.getTestApiKey(), "UTF-8"));
		urlBuilder.append("&q=19");
//		System.out.println(urlBuilder.toString());
		URL url = new URL(urlBuilder.toString());
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		
		// 접속 결과 왔으면
//		BufferedReader rd;
//		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//		} else {
//			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//		}
//		
//		StringBuilder sb = new StringBuilder();
//		String line;
//		while((line = rd.readLine())!= null) {
//			sb.append(line);
//		}
//		
//		rd.close();
//		conn.disconnect();
		// System.out.println(sb.toString());
		
		// 데이터 왔으면 파싱
		 JSONParser parser = new JSONParser();
		 JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(url.openStream()));
		 
		 Map<String, Object> map = (Map<String, Object>) jsonObject;
		 System.out.println(map);
		
		return "program/test1";
	}
}
