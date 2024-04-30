package com.ezen.test.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezen.test.domain.CommentVO;
import com.ezen.test.repository.BoardDAO;
import com.ezen.test.repository.CommentDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
	
	@Inject
	private CommentDAO cdao;
	@Inject
	private BoardDAO bdao;

	@Override
	public int post(CommentVO cvo) {
		log.info("post service in");
//		int isOk = cdao.post(cvo);
//		isOk *= bdao.cmtCount(cvo.getBno());
//		return isOk;
		return cdao.insert(cvo);
	}

	@Override
	public List<CommentVO> getList(int bno) {
		log.info("getList service in");
		return cdao.getList(bno);
	}

	@Override
	public int modify(CommentVO cvo) {
		log.info("modify service in");
		return cdao.modify(cvo);
	}

	@Override
	public int remove(int cno) {
		log.info("remove service in");
		return cdao.delete(cno);
	}

}
