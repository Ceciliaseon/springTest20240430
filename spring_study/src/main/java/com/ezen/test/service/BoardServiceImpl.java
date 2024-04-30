package com.ezen.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.test.domain.BoardDTO;
import com.ezen.test.domain.BoardVO;
import com.ezen.test.domain.CommentVO;
import com.ezen.test.domain.FileVO;
import com.ezen.test.domain.PagingVO;
import com.ezen.test.repository.BoardDAO;
import com.ezen.test.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDAO bdao;
	private final FileDAO fdao;

	@Override
	public int insert(BoardDTO bdto) {
		log.info("board register service check!");
		int isOk = bdao.insert(bdto.getBvo());
		
		//file  처리 => bno는 아직 없는 상태
		if(bdto.getFlist() == null) {
			return isOk;
		} else {
			//파일이 있기 때문에 저장이 필요
			if(isOk > 0 && bdto.getFlist().size() > 0) {
				// bno는 아직 없음
				// insert를 통해 자동 생성 => DB에서 검색해와야 함
				int bno = bdao.selectBno();
				for(FileVO fvo : bdto.getFlist()) {
					fvo.setBno(bno);
					// 파일 bno 저장
					isOk *= fdao.insertFile(fvo);
				}
			}
		}
		
		return isOk;
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		log.info("board list service check!");
		return bdao.getList(pgvo);
	}

	@Override
	public BoardDTO getDetail(int bno) {
		log.info("board detail service check!");
		//rea_count 증가
		bdao.updateReadCount(bno);
		
		BoardDTO bdto = new BoardDTO();
		BoardVO bvo = bdao.getDetail(bno); // 기존에 처리된 bvo 객체
		bdto.setBvo(bvo);
		bdto.setFlist(fdao.getFileList(bno)); // bno에 해당되는 모든 파일 리스트 검색 
		return bdto;
	}

	@Override
	public void update(BoardDTO bdto) {
		log.info("board update service check!");
		// 파일 추가 작업
		// bvo >> boardMapper / flist >> fileMapper
		
		int isOk = bdao.update(bdto.getBvo());
		
		// 파일 값이 없다면...
		if(bdto.getFlist() == null) {
			return;
		}
		
		// bvo 업데이트 완료 후 파일도 있다면... 파일 추가
		if(isOk > 0 && bdto.getFlist().size()>0) {
			// bno setting
			for (FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bdto.getBvo().getBno());
				isOk *= fdao.insertFile(fvo);
			}
		}
	}

	@Override
	public void remove(int bno) {
		log.info("board remove service check!");
		bdao.remove(bno);
	}

	@Override
	public int getTotal(PagingVO pgvo) {
		log.info("board totalCount service check!");
		return bdao.totalCount(pgvo);
	}

//	@Override
//	public int removiFile(String uuid, int bno) {
//		log.info("board removiFile service check!");
//		int isOk = fdao.removeFile(uuid);
//		if(isOk > 0) {
//			isOk *= bdao.fileCount(bno);
//		}
//		return isOk;
//	}

//	@Override
//	public int getQty(CommentVO cvo) {
//		log.info("board getQty service check!");
//		BoardVO bvo = new BoardVO();
//		bvo.setBno(cvo.getBno());
//		return bdao.updateCmtQty(bvo);
//	}

	@Override
	public void cmtFileCountUpdate() {
		bdao.cmtCountUpdate();
		bdao.cmtFileUpdate();
		
	}

	@Override
	public int removiFile(String uuid, int bno) {
		int isOk = fdao.removeFile(uuid);
		if(isOk > 0) {
			isOk *= bdao.fileCount(bno);
		}
		return 0;
	}





}