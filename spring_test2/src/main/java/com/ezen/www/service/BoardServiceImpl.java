package com.ezen.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.repository.BoardDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDAO bdao;

	@Override
	public int insert(BoardVO bvo) {
		log.info(">> insert service in >>");
		return bdao.insert(bvo);
	}

	@Override
	public List<BoardVO> getList() {
		log.info(">> getList service in >>");
		return bdao.list();
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




}
