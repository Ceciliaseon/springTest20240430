package com.ezen.www.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@EnableWebMvc  //<annotation-driven /> 설정
@ComponentScan(basePackages = {"com.ezen.www.controller", "com.ezen.www.service", "com.ezen.www.handler"})
public class ServletConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// resources 경로 설정 
		// 나중에 파일 업로드 경로 설정 추가
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// views 경로 설정 
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");	
		viewResolver.setViewClass(JstlView.class);
		
		registry.viewResolver(viewResolver); //registry가 인지할 수 있도록 세팅
	}
	
	//multipartResolver 설정(추후)
	
	
	
	
}