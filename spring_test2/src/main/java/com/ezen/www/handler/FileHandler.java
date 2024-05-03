package com.ezen.www.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.www.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component //사용자 클래스를 bean으로 등록
public class FileHandler {
	
	//파일등록하는 내 위치
	private final String UP_DIR = "C:\\Users\\mangs\\Desktop\\_myProject\\_java\\_fileUpload";
	
	public List<FileVO> uploadFiles(MultipartFile[] files){
		
		List<FileVO> flist = new ArrayList<>();
		//FileVO에 맞춰서 VO 생성 / 파일 저장 / 썸네일 저장
		//날짜로 폴더 생성하여 업로드 파일을 관리
		
		LocalDate date = LocalDate.now(); //오늘날짜 입력 2024-05-03
		String today = date.toString(); //날짜를 String 형태로 변환
		today = today.replace("-", File.separator); // -를 파일 경로 표시로 변환 (\ win) 
		
		// C:\\Users\\mangs\\Desktop\\_myProject\\_java\\_fileUpload\\2024\\05\\03
		File folders = new File(UP_DIR, today);
		
		// 폴더 생성
		if(!folders.exists()) { //없다면...
			folders.mkdirs();// 하위에 있는 모든 폴더 생성
		}
		
		// files를 가지고 객체 설정
		for(MultipartFile file: files) {
			FileVO fvo = new FileVO();
			fvo.setSaveDir(today);
			fvo.setFileSize(file.getSize());
			
			String originalFileName = file.getOriginalFilename();
			String fileName = originalFileName.substring(
								originalFileName.lastIndexOf(File.separator)+1);
			fvo.setFileName(fileName);
			
			UUID uuid = UUID.randomUUID();
			String uuidStr = uuid.toString();
			fvo.setUuid(uuidStr);
			
			/*---------기본적인 세팅 완료 ---------*/
			
			// 디스트에 저장 => 저장 객체를 생성
			String fullFileName = uuidStr+"_"+fileName;
			
			// C:\\Users\\mangs\\Desktop\\_myProject\\_java\\_fileUpload\\2024\\05\\03\\uuid_name.jpg
			// 실제 파일이 저장되려면 첫 경로부터 모두 설정되어 있어야 저장이 가능함 
			File storeFile = new File(folders, fullFileName);
			
			try {
				file.transferTo(storeFile);
				
				// 썸네일 저장 => 이미지만 썸네일 생성 가능
				// 이미지인지 확인
				if(isImageFile(storeFile)) {
					fvo.setFileType(1);
					// 썸네일 생성
					File thumbNail = new File(folders, uuidStr+"_th_"+fileName);
					Thumbnails.of(storeFile).size(75, 75).toFile(thumbNail);
				}
			} catch (Exception e) {
				log.info("파일 저장 오류");
				e.printStackTrace();
			}
			flist.add(fvo);
			
		}
		
		
		return flist;
	}
	
	private boolean isImageFile(File storeFile) throws IOException {
		String mimeType = new Tika().detect(storeFile); // type image/jpg
		return mimeType.startsWith("image")?true:false;
	}
	
}
