package com.ezen.www.repository;

import java.util.List;

import com.ezen.www.domain.BoardVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> list();

	BoardVO detail(int bno);

	int modify(BoardVO bvo);

	void delete(int bno);



}
