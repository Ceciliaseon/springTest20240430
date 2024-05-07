package com.ezen.www.repository;

import java.util.List;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> list(PagingVO pgvo);

	BoardVO detail(int bno);

	int modify(BoardVO bvo);

	void delete(int bno);

	int getTotal(PagingVO pgvo);

	int selectOneBno();

//	int fileCount(int bno);



}
