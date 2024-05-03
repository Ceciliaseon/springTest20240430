package com.ezen.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.FileVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.repository.BoardDAO;
import com.ezen.www.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDAO bdao;
	private final FileDAO fdao;

	@Transactional
	@Override
	public int insert(BoardDTO bdto) {
		log.info(">> insert service in >>");
		// bvo 저장 후 bno 세팅 후 file 저장
		int isOk = bdao.insert(bdto.getBvo());
		log.info(">> bdto >> {}", bdto);
		if(bdto.getFlist() == null) {
			return isOk;
		}
		if(isOk > 0 && bdto.getFlist().size() >0) {
			// bno settion
			log.info("1");
			int bno = bdao.selectOneBno(); // 가장 마지막에 등록된 bno 가지고 오기 
			for (FileVO fvo : bdto.getFlist()) {
				log.info("2");
				fvo.setBno(bno);
				log.info("3");
				isOk *= fdao.insertFile(fvo);
			}
		}
		return isOk;
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		log.info(">> getList service in >>");
		return bdao.list(pgvo);
	}

	@Override
	public BoardVO getDetail(int bno) {
		log.info(">> getDetail service in >>");
		return bdao.detail(bno);
	}

	@Override
	public int modify(BoardVO bvo) {
		log.info(">> modify service in >>");
		return bdao.modify(bvo);
	}

	@Override
	public void delete(int bno) {
		log.info(">> delete service in >>");
		bdao.delete(bno);
	}

	@Override
	public int getTotal(PagingVO pgvo) {
		// TODO Auto-generated method stub
		return bdao.getTotal(pgvo);
	}




}
