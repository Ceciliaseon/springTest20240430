package com.ezen.test.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.test.domain.BoardDTO;
import com.ezen.test.domain.BoardVO;
import com.ezen.test.domain.FileVO;
import com.ezen.test.domain.PagingVO;
import com.ezen.test.handler.FileHandler;
import com.ezen.test.handler.PagingHandler;
import com.ezen.test.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/board/*")
@Slf4j
@RequiredArgsConstructor  //생성자 주입을 할 때 사용되는 생성자
@Controller
public class BoardController {
	
	private final BoardService bsv;
	private final FileHandler fhd;
	
	@GetMapping("/register")
	public String register() {
		return "/board/register";
	}
	
	//@RequestParam("name") String name >> 파라미터를 받을 때 (하나만일때)
	//required : 필수여부 false : 파라미터가 없어도 예외가 발생하지 않음
	@PostMapping("/insert")
	public String insert(BoardVO bvo, @RequestParam(name="files", required = false)MultipartFile[] files) {
		log.info(">>> bvo in > {}", bvo);
		// 파일 핸들러 처리 
		// 파일 저장 처리 => fileList 리턴
		List<FileVO> flist = null; //파일이 없을 수도 있기 때문에...
		
		// file이 있을 경우에만 햄들러 호출 
		if(files[0].getSize() > 0) { //파일이 존재하는지를 체크
			// 핸들러 호출
			flist = fhd.uploadFiles(files);
			bvo.setHas_file(flist.size()); //들어가는 파일 갯수만큼 바로 set
			log.info(">>> files > {}", files);
		}
		
		BoardDTO bdto = new BoardDTO(bvo, flist);
		int isOk = bsv.insert(bdto);
		return "redirect:/board/list";
	}
	
	@GetMapping("/list")
	public String list(Model m, PagingVO pgvo) { // PagingVO 파라미터가 없으면 기본생성자 값이 뜸
		log.info(">>> pgvo >>> {}", pgvo);
		//리턴 타입 > 목적지 경로에 대한 타입(destPage가 리턴) 
		// Model 객체 >> request.setAttribute 역할을 하는 객체
		
		//cmt_qty, has_file update 후 리스트 가져오기
		bsv.cmtFileCountUpdate();

		List<BoardVO> list = bsv.getList(pgvo);
		int totalCount = bsv.getTotal(pgvo);
		PagingHandler ph= new PagingHandler(pgvo, totalCount);
		log.info(">> totalCount >> {}",totalCount);
		log.info(">> ph >> {}",ph);

		m.addAttribute("list", list);
		m.addAttribute("ph", ph);
		return "/board/list";
	}
	
//	@GetMapping("/list")
//	public String list(Model m, PagingVO pgvo, CommentVO cvo) { // PagingVO 파라미터가 없으면 기본생성자 값이 뜸
//		log.info(">>> pgvo >>> {}", pgvo);
////		log.info(">>> cvo >>> {}", cvo);
//		//리턴 타입 > 목적지 경로에 대한 타입(destPage가 리턴) 
//		// Model 객체 >> request.setAttribute 역할을 하는 객체
//		List<BoardVO> list = bsv.getList(pgvo);
//		int totalCount = bsv.getTotal(pgvo);
//		
////		int qty = cvo.getBno();
//		
////		log.info(">> qty >> {}", qty);
//
//		int cmtQty = bsv.getQty(cvo);
//		log.info(">> cmtQty >> {}", cmtQty);
//		PagingHandler ph= new PagingHandler(pgvo, totalCount);
//		log.info(">> totalCount >> {}",totalCount);
//		log.info(">> ph >> {}",ph);
//
//		m.addAttribute("list", list);
//		m.addAttribute("ph", ph);
//		return "/board/list";
//	}
	
	//detail => /board/detail => return /board/detail
	//modify => /board/modify => return /board/modify
	//controller 로 들어오는 경로와 jsp로 나가는 경로가 일치하면 void 처리 할 수 있음
	@GetMapping({"/detail", "/modify"})
	public void detail(Model m, @RequestParam("bno")int bno) {
		log.info(">>> bno >>> {}", bno);
		BoardDTO bdto = bsv.getDetail(bno);
		log.info(">>> bdto >> {}", bdto);
		m.addAttribute("bdto", bdto);
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO bvo, @RequestParam(name="files", required = false)MultipartFile[] files) {
		log.info(">>> modify bvo >>> {}", bvo);
		
		List<FileVO> flist = null;
		
		// fileHandler MultipartFile[] => flist
		if(files[0].getSize() > 0) { //파일이 존재하는지를 체크
			// 핸들러 호출s
			flist = fhd.uploadFiles(files);
			bvo.setHas_file(bvo.getHas_file()+flist.size());
			log.info(">>> files > {}", files);
		}
		BoardDTO bdto = new BoardDTO(bvo, flist);
		bsv.update(bdto);
		// board/detail.jsp로 가고 싶을 때 >> 새로운 데이터를 가지고 가야함 
		// param 이 필요하기 때문에 ?bno="bvo.getBno()를 사용하여 데이터를 가지고 감
		return "redirect:/board/detail?bno="+bvo.getBno();
	}

	@GetMapping("/remove")
	public String remove(@RequestParam("bno")int bno) {
		bsv.remove(bno);
		return "redirect:/board/list";
	}
	
	//ResponseEntity 비동기 시스템을 진행할떄 사용되는 응답 개체
	@DeleteMapping(value = "/{uuid}/{bno}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> removeFile(@PathVariable("uuid")String uuid, @PathVariable("bno")int bno){
		log.info(uuid);
		log.info(">>bno{}",bno);
		int isOk = bsv.removiFile(uuid, bno);
		return isOk > 0? new ResponseEntity<String>("1", HttpStatus.OK):
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
