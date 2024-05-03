package com.ezen.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.repository.CommentDAO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
	
	@Inject
	private CommentDAO cdao;

	@Override
	public int post(CommentVO cvo) {
		log.info("post service in >> ");
		return cdao.post(cvo);
	}

	@Override
	public PagingHandler getList(int bno, PagingVO pgvo) {
		log.info("getList service in >> ");
		// cmtList에 대한 값을 ph 객체 안에 삽입
		List<CommentVO> list = cdao.getList(bno, pgvo);
		// totalCount 구해오기
		int totalCount = cdao.getSelectOneBnoTotalCount(bno);
		PagingHandler ph = new PagingHandler(pgvo, totalCount, list);
		return ph;  //list인 값
	}

	@Override
	public int update(CommentVO cvo) {
		log.info("update service in >> ");
		return cdao.update(cvo);
	}

	@Override
	public int remove(int cno) {
		return cdao.delete(cno);
	}
}
