package com.ezen.test.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.test.domain.CommentVO;
import com.ezen.test.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/comment/*")
@RestController //비동기방식
public class CommentController {
	
	@Inject
	private CommentService csv;
	
	// 비동기 방식에서 사용할 옵션들
	// ResponseEntity 객체 사용 : body에 대한 내용 + httpStatus에 대한 상태를 같이 보냄
	// @RequestBody : body에서 가져온 값을 추출 
	// consumes : @RequestBody에서 가져오는 테이더의 형태 (옵션)
	// produces : @RequestBody에서 보내는 데이터의 형식 / 나가는 타입 : MediaType
	// json : application/json // text : text_plain
	
	// 3 
	@PostMapping(value = "/post", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> post(@RequestBody CommentVO cvo){
		log.info(">>> cvo >>> {}", cvo);
		int isOk = csv.post(cvo);
		return isOk > 0 ? new ResponseEntity<String>("1", HttpStatus.OK):
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 6
	// value >> 내가 js에서 받으려고 했던 부분을 기입
	@GetMapping(value = "/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommentVO>> list(@PathVariable("bno")int bno){
		log.info(">>> bno >>> {}", bno);
		List<CommentVO> list = csv.getList(bno);
		return new ResponseEntity<List<CommentVO>>(list, HttpStatus.OK);
	}
	
	//consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE >> 생략가능 
	@PutMapping(value = "/modify", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> modify(@RequestBody CommentVO cvo){
		log.info(">>> cvo >>> {}", cvo);
		int isOk = csv.modify(cvo);
		return isOk>0 ? new ResponseEntity<String>("1", HttpStatus.OK) :
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value = "/{cno}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> remove(@PathVariable("cno")int cno){
		int isOk = csv.remove(cno);
		return isOk > 0 ? new ResponseEntity<String>("1", HttpStatus.OK):
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}