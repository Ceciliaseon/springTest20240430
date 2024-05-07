package com.ezen.www.handler;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ezen.www.domain.FileVO;
import com.ezen.www.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@EnableScheduling 
public class FileSweeper {
	// 직접 DB에 접속해서 DB 데이터를 받아와서 처리
	
	private final FileDAO fdao;
	
	// 삭제할 파일의 기본 경로
	private final String BASE_PATH = "C:\\Users\\mangs\\Desktop\\_myProject\\_java\\_fileUpload\\";
	
	// 매일 정해진 시간에 스케줄러를 실행
	// 매일 등록된 파일(DB)과 <-> 해당일의 폴더에 있는 파일이
	// 일치하는 파일은 남기고, 일치하지 않는 파일 삭제 
	
	//cron="" >> 초 분 시 일 월 요일 년도(생략 가능) 단위를 작성
	@Scheduled(cron="0 39 14 * * *") // 12시 정각
	public void fileSweeper() {
		log.info(">>> FIleSweeper Running Start! : {}", LocalDateTime.now());
		// 데이터 처리
		
		// DB에 등록된 파일 목록 가져오는 작업
		List<FileVO> dbList = fdao.selectListAllFile();
		
		// 저장소를 검색할 때 필요한 파일 경로 리스트(실제 존재해야하는 list)
		List<String> currFiles = new ArrayList<String>(); //파일에 대한 이름 / 경로만 가지고 있는 이름만 가지는
		for(FileVO fvo : dbList) {
			String filePath = fvo.getSaveDir()+File.separator+fvo.getUuid();
			String fileName = fvo.getFileName();
			currFiles.add(BASE_PATH+filePath+"_"+fileName);
			
			//이미지라면 썸네일 경로도 추가해야 함 
			if (fvo.getFileType() == 1) {
				currFiles.add(BASE_PATH+filePath+"_th_"+fileName);
			}
		}
		log.info(">>>> currFile >> {}", currFiles);
		
		// 오늘 날짜를 반영한 폴더구조 경로 만들는 과정
		LocalDate now = LocalDate.now();
		String today = now.toString();
		today = today.replace("-", File.separator);
		
		// 오늘날짜의 경로를 기잔으로 저장되어 있는 파일을 검색
		// 해당 날짜를 기반으로 하는 path로 변경 >> 그걸 file 형태로 변환 
		File dir = Paths.get(BASE_PATH+today).toFile();
		// listFiles() >> 경로에 해당하는 모든 파일을 배렬로 리턴 
		File[] allFileObj = dir.listFiles();
		
		// 리턴 받은 후, 실제 저장되어 있는 파일과 DB에 존재하는 파일을 비교하여
		// 없는 파일은 삭제를 진행하는 작업 
		for(File file : allFileObj) {
			String storedFileName = file.toPath().toString();
			if (!currFiles.contains(storedFileName)) { // DB에 같은 파일이 없으면... ! 꼭 넣어야 함
				file.delete(); // 파일 삭제 
				log.info(">>> delete files >> {}", storedFileName);
			}
		}
		
		
		log.info(">>> FIleSweeper Running End! : {}", LocalDateTime.now());
	}
	
}
