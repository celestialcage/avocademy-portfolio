package com.avocado.web.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.avocado.web.entity.CommunityDTO;
import com.avocado.web.entity.FilesDTO;
import com.avocado.web.repository.CommunityDAO;

import net.coobird.thumbnailator.Thumbnailator;

@Service("communityService")
public class CommunityServiceImpl implements CommunityService {

	@Autowired
	private CommunityDAO communityDAO;
	private String fileDTO;

	@Override
	public int count() {

		return communityDAO.count();
	}

	@Override
	public List<CommunityDTO> community(int pageNo, int post) {
		// 1페이지면 글이 0번부터 ~ 9번까지
		// 2페이지면 10번부터 ~ 19번까지 => 10개씩
		// 기본 pageNo는 1로 시작
		// limit 을 설정 할 수 있게
		pageNo = (pageNo - 1) * post;
		System.out.println("pageNO : " + pageNo + " / post : " + post);
		Map<String, Integer> pageMap = new HashMap<>();
		pageMap.put("pageNo", pageNo);
		pageMap.put("post", post);

		List<CommunityDTO> list = communityDAO.community(pageMap);

		// System.out.println("커뮤니티서비스 확인 :" + list);
		return list;
	}

	@Override
	public CommunityDTO detail(int cno) {
		return communityDAO.detail(cno);
	}

	@Override
	public Map<String, Object> write(Map<String, Object> map, FilesDTO dto, MultipartFile file) {
		// System.out.println("서비스 맵"+map);
		// System.out.println("서비스_파일 타입: " + file.getName());
		// System.out.println("서비스_파일 사이즈: " + file.getSize());
		// System.out.println("서비스_파일 이름: " + file.getOriginalFilename());

		// 서버의 물리적 경로
		String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/files"; // 업로드 디렉토리 경로

		// 파일이 비어 있지 않은 경우에만 파일 처리 로직을 수행
		if (!file.isEmpty()) {

			String filePath = uploadDir + "/" + file.getOriginalFilename(); // 파일 경로 설정
			// 디렉터리가 존재하지 않으면 생성
			File url = new File(uploadDir);
			if (!url.exists()) {
				url.mkdirs();
			}

			// UUID 생성
			UUID uuid = UUID.randomUUID();
			// System.out.println("원본파일명 : " + file.getOriginalFilename());
			// System.out.println("UUID파일명 : " + uuid.toString() +
			// file.getOriginalFilename());

			String newFileName = uuid.toString() + "-" + file.getOriginalFilename();
			// System.out.println("새로 만들어진 파일이름 : " + newFileName);

			// 날짜를 뽑아서 파일명 변경하기
			LocalDateTime Idt = LocalDateTime.now();
			String IdtFormat = Idt.format(DateTimeFormatter.ofPattern("YYYYMMddHHmmSS"));
			// System.out.println("날짜 파일명 : " + IdtFormat + file.getOriginalFilename());

			// 실제 경로
			File upFileName = new File(url, file.getOriginalFilename());
			System.out.println("실제 경로 : " + url);

			try {
				// 서버에 파일저장
				file.transferTo(upFileName);

				// 썸네일 만들기
				FileOutputStream thumbnail = new FileOutputStream(new File(url, "S_" + newFileName));
				Thumbnailator.createThumbnail(file.getInputStream(), thumbnail, 100, 100);

				thumbnail.close();

				// 파일 정보 설정
				dto.setFsn(file.getOriginalFilename());
				dto.setActl_fnm(newFileName);
				dto.setUuid(uuid.toString());
				dto.setFsize(file.getSize());
				dto.setFpath(upFileName.getAbsolutePath());

			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

		}
		Map<String, Object> result = new HashMap<String, Object>();

		// 파일이 비어 있지 않은 경우에만 파일 정보를 데이터베이스에 저장
		if (!file.isEmpty()) {
			int fileUp = communityDAO.fileUp(dto);
			result.put("fileUp", fileUp);
			
		//파일 번호 보내기
			int fileNo = communityDAO.getFileNo();
			map.put("fno", fileNo);
			System.out.println("파일번호" +fileNo);
		}
		// 글 작성 정보 저장
		int write = communityDAO.write(map);

		result.put("write", write);

		return result;
	}

	@Override
	public int deletecd(String cno) {
		return communityDAO.deletecd(cno);
	}
	
	// 파일 번호를 반환하는 메서드 추가
    public int getFileNo() {
        return communityDAO.getFileNo();
    }

	@Override
	public String getFsn(int fno) {		
		return communityDAO.getFsn(fno);
	}
    

}
