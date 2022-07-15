package com.adam.mobileserver.aop;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {
	//콘솔에 로그를 출력하기 위한 객체
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//컨트롤러의 요청처리 메서드를 호출하기 전에 호출되는 메서드
	//true를 리턴하면 컨트롤러의 요청처리 메서드를 호출하고
	//false를 리턴하면 cotroller의 메서드를 호출하지 안흠
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler)throws Exception{
		logger.info("PreHandler");
		
		//일단위로 파일을 생성
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		PrintWriter pw = new PrintWriter(new FileOutputStream(str+".log", true));
		pw.println(request.getRemoteAddr()
				+":"+request.getMethod()
				+":"+request.getRequestURI());
		pw.flush(); //버퍼의 내용을 전송
		
		pw.close();
		
		return true;
	}
	
	//컨트롤러가 정상적으로 요청을 처리한 후 호출되는 메서드
	//ModelAndView 객체를 이용해서 데이터를 변환할 수 있음
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView modelAndView)throws Exception{
		logger.info("PostHandler");
		
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception ex)throws Exception{
		logger.info("afterCompletion");
		
		
	}

}
