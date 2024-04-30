package com.ezen.test.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.test.domain.MemberVO;
import com.ezen.test.service.MemeberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member/*")
@Controller
public class MemeberController {
	
	@Inject
	private MemeberService msv;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(MemberVO mvo) {
		log.info(">>> MemberVO >>> {}", mvo);
		int isOk = msv.insert(mvo);
		return "index";
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/login")
	public String login(MemberVO mvo, HttpServletRequest request, Model m) {
		log.info(">>> MemberVO >>> {}", mvo);
		
		// mvo 객체가 DB의 값과 일치하는 객체 가져오기 >> session 저장
		MemberVO loginMvo = msv.isUser(mvo);
		log.info(">>> loginMvo >>> {}", loginMvo);
		
		// 값이 넘어온 후
		if(loginMvo != null) {
			// 로그인 성공
			HttpSession ses = request.getSession();
			ses.setAttribute("ses", loginMvo); // 세션에 로그인 객체 저장
			ses.setMaxInactiveInterval(10*60); // 로그인 유지 시간 (선택사항)
		}else {
			// 로그인 실패
			m.addAttribute("msg_login", "1");
		}
		return "index";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, Model m) {
		// 라스트 로그인 업데이트
		MemberVO mvo = (MemberVO)request.getSession().getAttribute("ses");
		msv.lastLoginUpdate(mvo.getId());
		
		// 세션객체 삭제 => 끊기
		request.getSession().removeAttribute("ses"); // 세션객체 삭제
		request.getSession().invalidate(); // 세션 끊기
		
		m.addAttribute("msg_logout","1");
		return "index";
	}
	
	@GetMapping("/modify")
	public void modify() {}
	
	@PostMapping("/modify")
	public String modify(MemberVO mvo) {
		log.info(">>> modify mvo >>> {}",mvo);
	
		msv.modify(mvo);
		return "redirect:/member/logout";
	}

	// 반복적으로 사용하는 구문은 메서드로 따로 뽑아서 코드 작업을 진행할 수 있음 
	private String getId (HttpServletRequest request) {
		MemberVO mvo = (MemberVO)request.getSession().getAttribute("ses");
		return mvo.getId();
	}
	
	@GetMapping("/remove")
	public String remove(HttpServletRequest request, RedirectAttributes re) {
//		MemberVO delMvo = (MemberVO)request.getSession().getAttribute("ses");
//		msv.remove(delMvo.getId());
		msv.remove(getId(request)); //메서드를 활용하는 방법

		// 1회성으로 메시지를 띄우는 방법
		re.addFlashAttribute("msg_remove", "1");
		
		// 중복적으로 세션을 끊게 된다면 redirect로 넘어가서 끊을 정보가 없음
		return "redirect:/member/logout";

	}
	
 
}
