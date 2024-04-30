package com.ezen.test.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ezen.test.domain.MemberVO;
import com.ezen.test.repository.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemeberServiceImpl implements MemeberService{
	
	@Inject
	private MemberDAO mdao;
	
	@Inject
	BCryptPasswordEncoder passwordEncoder;
	
	@Inject
	HttpServletRequest request;
	
	@Override
	public int insert(MemberVO mvo) {
		log.info("insert service in");
		// id가 중복되는지 체크 , 회원가입 실패 
		// 아이디만 주고, DB에서 일치하는 mvo 객체를 리턴 => 일치하는 객체 o : 가입실패 / x : 가입가능
		MemberVO tempMvo = mdao.getUser(mvo.getId()); 
		if (tempMvo !=null) {
			//기존 아이디가 있는 경우
			return 0;
		}
		// 아이디가 중복되지 않는 다면... 회원가입 진행
		// password가 null 이거나 값이 없다면... 회원가입 불가
		if (mvo.getId() == null || mvo.getId().length() ==0) {
			return 0;
		}
		if (mvo.getPw() == null || mvo.getPw().length() ==0) {
			return 0;
		}
		
		// 회원가입 진행
		// password 암호화하여 가입
		// encode() : 암호화를 진행 / matches(입력된비번, 암호화된 비번) => true/false 리턴
		
//		String pw = mvo.getPw();
//		String encodePw = passwordEncoder.encode(pw);
//		mvo.setPw(encodePw);
		mvo.setPw(passwordEncoder.encode(mvo.getPw()));
		
		// 회원가입
		int isOk = mdao.insert(mvo);
		return isOk;
	}

	@Override
	public MemberVO isUser(MemberVO mvo) {
		log.info("isUser service in");
		// 로그인 유저 확인
		// 회원가입 했을 때 사용했던 메서드 활용 (일치하는 아이디 가져오기)
		MemberVO tempMvo = mdao.getUser(mvo.getId()); 
		
		// 해당 아이디가 없다면...
		if(tempMvo == null) {
			return null;
		}
		
		// 아이디가 있다면.. matches(원래비번, 암호화된 비번) 비교
		if(passwordEncoder.matches(mvo.getPw(), tempMvo.getPw())) {
			return tempMvo;
		}
		return null;
	}

	@Override
	public void lastLoginUpdate(String id) {
		log.info("lastLoginUpdate service in");
		mdao.lastLoginUpdate(id);
		
	}

	@Override
	public void modify(MemberVO mvo) {
		// pw 여부에 따라 변경 사항을 나누어서 처리
		// pw를 바꾸지 않을 경우 값이 비어서 왔을 것 >> 기존 pw를 입력해야 할 것 
		// pw가 없다면 기존값 설정 / 있다면 암호화처리하여 수정
		if(mvo.getPw() == null || mvo.getPw().length() ==0) {
			MemberVO sesMvo = (MemberVO) request.getSession().getAttribute("ses");
			mvo.setPw(sesMvo.getPw());
		}else {
			log.info(">> 3");
			String setPw = passwordEncoder.encode(mvo.getPw());
			mvo.setPw(setPw);
		}
		log.info(">> pw 수정 후 mvo >> {}", mvo);
		mdao.update(mvo);

	}

	@Override
	public void remove(String id) {
		log.info("remove service in");
		mdao.delete(id);
	}
	
}
