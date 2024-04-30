package com.ezen.test.service;

import java.util.List;

import com.ezen.test.domain.BoardDTO;
import com.ezen.test.domain.BoardVO;
import com.ezen.test.domain.CommentVO;
import com.ezen.test.domain.PagingVO;

public interface BoardService {

	int insert(BoardDTO bdto);

	List<BoardVO> getList(PagingVO pgvo);

	BoardDTO getDetail(int bno);

	void update(BoardDTO bdto);

	void remove(int bno);

	int getTotal(PagingVO pgvo);

	int removiFile(String uuid, int bno);

//	int getQty(CommentVO cvo);

	void cmtFileCountUpdate();




}