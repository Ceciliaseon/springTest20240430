package com.ezen.test.repository;

import java.util.List;

import com.ezen.test.domain.BoardVO;
import com.ezen.test.domain.CommentVO;
import com.ezen.test.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> getList(PagingVO pgvo);

	BoardVO getDetail(int bno);

	int update(BoardVO bvo);

	void remove(int bno);

	void updateReadCount(int bno);

	int totalCount(PagingVO pgvo);

	int selectBno();

//	int updateCmtQty(BoardVO bvo);

	void cmtCountUpdate();

	void cmtFileUpdate();

	int fileCount(int bno);



}
