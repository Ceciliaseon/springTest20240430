package com.ezen.test.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.test.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component
public class FileHandler {
	
	// 실제 파일이 저장되는 경로
	private final String UP_DIR="C:\\Users\\mangs\\Desktop\\_myProject\\_java\\_fileUpload";

	public List<FileVO> uploadFiles(MultipartFile[] files) {
		// 리턴객체 생성
		List<FileVO> flist = new ArrayList<FileVO>();
		
		// MultipartFile[] 받아서 FileVO 형태의 List로 생성 후 리턴
		// 오늘 날짜로 경로 생성 (가변형태로 저장) 년 월 일 폴더를 구성
		
		// 오늘날짜 경로 생성
		LocalDate date = LocalDate.now();
		String today = date.toString();
		log.info(">>> today >>> {}",today);
		
		// 오늘 날짜를 폴더형식으로 구성
		today = today.replace("-", File.separator);
		
		// C:\\Users\\mangs\\Desktop\\_myProject\\_java\\_fileUpload\\2024\\04\\29
		File folders = new File(UP_DIR, today);
		
		// 폴더생성 >> mkdir(폴더 1개 생성) , mkdirs(하위폴더까지 같이(구조) 생성)
		// exists : 있는지 없는지 확인
		if(!folders.exists()) {
			folders.mkdirs(); // 폴더생성 명령
		}
				
		// 리스트 설정 => 저장
		for(MultipartFile file : files) {
			FileVO fvo = new FileVO();
			fvo.setSave_dir(today);
			fvo.setFile_size(file.getSize()); //return long
			
			//getOriginalFilename() : 경로+파일명 / 파일 경로를 포함하는 케이스도 있음
			String originalFileName = file.getOriginalFilename();
			String onlyFileName = originalFileName.substring
					(originalFileName.lastIndexOf(File.separator)+1); // 실제 파일명만 추출
			fvo.setFile_name(onlyFileName);
			
			// UUID 생성
			UUID uuid = UUID.randomUUID();
			String uuidStr = uuid.toString(); //uuid를 String화
			fvo.setUuid(uuidStr);
			
			//<--------------- ▲ 여기까지가 fvo Setting 완료 ▲ ---------------> 
			// bno, file_type 남음
			
			// 디스크에 저장
			// 디스크에 저장할 파일 객체 생성
			// uuid_fileName / uuid_th_fileName
			String fullFileName = uuidStr+"_"+onlyFileName; // 전체 파일명
			File storeFile = new File(folders, fullFileName);
			
			// 저장 => 저장 경로 또는 파일 없다면 IOException 발생 (try~catch 필수)
			try {
				file.transferTo(storeFile); // 저장
				
				// 파일 타입 결정 => 이미지만 썸네일 저장
				if(isImageFile(storeFile)) {
					fvo.setFile_type(1);
					
					// 썸네일 생성
					File thumbNail = new File(folders, uuidStr+"_th_"+onlyFileName );
					Thumbnails.of(storeFile).size(75, 75).toFile(thumbNail);
				}
				
			} catch (Exception e) {
				log.info(">> file 저장 에러 >>");
				e.printStackTrace();
			}
			//list 에 fvo 내용 추가
			flist.add(fvo);
			
		}
		
		return flist;
	}
	
	// tika를 활용한 파일 형식 체크 => 이미지 파일이 맞는지 확인하는 작업 
	// detect 파일의 형식을 체크
	public boolean isImageFile(File storeFile) throws IOException {
		String mimeType = new Tika().detect(storeFile); // image/png  or image/jpg
		return mimeType.startsWith("image")? true : false;
	}
		
}