package com.ezen.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ezen.www.security.CustomAuthUserService;
import com.ezen.www.security.LoginFailHandler;
import com.ezen.www.security.LoginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration //설정하기 위해 만든 class
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//비밀번호 암호화 객체 PasswordEncoder Bean으로 생성
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//SuccessHandler 객체 Bean 생성 => 사용자 지정 커스텀 객체
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new LoginSuccessHandler(); //아직 생성 전
	}
	
	//FailHandler 객체 Bean 생성 => 사용자 지정 커스텀 객체
	@Bean
	public AuthenticationFailureHandler authFailHandler() {
		return new LoginFailHandler();
	}
	
	//UserDetail 객체 Bean 생성 => 사용자 지정 커스텀 객체
	@Bean
	public UserDetailsService customUserService() {
		return new CustomAuthUserService();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 인증되는 객체로 설정 (id/pw 설정) 
		auth.userDetailsService(customUserService()).passwordEncoder(bcPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Http 화면에서 설정되는 권한에 따른 주소 맵핑 설정
		// csrf() :
		http.csrf().disable();
		
		// 권한 승인 요청
		// andMatchers : 접근을 허용하는 값(경로)
		// pormitAll : 권한을 모두에게 주는 값(누구나 접근 가능한 경로)
		// authenticated() : 인증된 사용자만 가능한 경로
		// auth => hasRole : 권한을 확인하는 경로 (어떠한 권한을 가지는지)
		// USER, ADMIN, MANAGER
		http.authorizeRequests()
			.antMatchers("/user/list").hasRole("ADMIN")
			.antMatchers("/", "/board/list", "/board/detail", "/comment/**", "/up/**", "/re/**", "/user/register", "/user/login").permitAll()
			.anyRequest().authenticated(); //나머지 모든 매칭 경로는 권한이 있어야만 진행할 수 있다 의미 
		
		// 커스텀 로그인 페이지 구성
		// Controller에 주소요청 맵핑은 반드시 같이 있어야 함 (필수)
		// 요청에 대한 경로가 없다면 실행되지 않음
		// login & logout 반드시 form tag를 싸서 가야함 (method ="post")
		
		// handler 커스텀 페이지가 없다면 failureForwardUrl 과 같이
		// 코드 진행 후 연결될 경로를 지정하는 방법도 있음
		
		http.formLogin()
		.usernameParameter("email") // userVO 멤버변수 중 id로 쓸 파라미터 값
		.passwordParameter("pwd")
		.loginPage("/user/login")
		.successHandler(authSuccessHandler())
		.failureHandler(authFailHandler());
		
		// 로그아웃 페이지 반드시 method="post"
		http.logout()
		.logoutUrl("/user/logout")
		.invalidateHttpSession(true) //세션을 끊는 과정
		.deleteCookies("JSESSIONID") //쿠키를 지우는 과정 (쿠키를 지워야 로그인한 상태에서 벗어날 수 있음)
		.logoutSuccessUrl("/");
	}

}
