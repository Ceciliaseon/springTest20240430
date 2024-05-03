package com.ezen.www.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/comment/*")
public class CommentController {
	
	@Inject
	private CommentService csv;
	
	@PostMapping(value = "/post", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> post(@RequestBody CommentVO cvo){
		int isOk = csv.post(cvo);
		return isOk > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) :
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagingHandler> list(@PathVariable("bno")int bno, @PathVariable("page")int page){
		// pagingVo / PagingHandler 사용
		// 더보기를 눌렀을 떄 5개의 데이터를 누적해서 보여주기 위해 
		PagingVO pgvo = new PagingVO(page, 5);
//		List<CommentVO> list  = csv.getList(bno);
		PagingHandler ph = csv.getList(bno, pgvo);
		return new ResponseEntity<PagingHandler>(ph, HttpStatus.OK);		
	}
	
	// Entity 전체를 구성해서 보내는 방식 
//	@PutMapping(value = "/modify", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE )
//	public ResponseEntity<String> modify(@RequestBody CommentVO cvo){
//		log.info(">>> cvo >>> {}", cvo);
//		int isOk = csv.update(cvo);
//		return isOk > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) :
//			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
	// ResponseBody 으로 처리하는 방법 
	@ResponseBody
	@PutMapping("/modify")
	public String modify(@RequestBody CommentVO cvo) {
		log.info(">>> cvo >> {}", cvo);
		int isOk = csv.update(cvo);
		return isOk > 0 ? "1" : "0";
	}
	
	
//	@DeleteMapping(value = "/{cno}", produces = MediaType.TEXT_PLAIN_VALUE)
//	public ResponseEntity<String> remove(@PathVariable("cno")int cno) {
//		int isOk = csv.remove(cno);
//		return isOk > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) :
//			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
	@ResponseBody
	@DeleteMapping("/{cno}")
	public String remove(@PathVariable("cno")int cno) {
		int isOk = csv.remove(cno);
		return isOk > 0 ? "1" : "0";
	}
	
}
