package com.adam.mobileserver.aop;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAop {
	//모든 POST 요청에 대해서 반응하는 메서드
	@Pointcut("@annotation(org.springframework.web.bind.annotation.Postmapping)")
	public void postMapping() {
		@AfterReturning(pointcut="postMapping()", returning="result")
		public void afterReturning(Joinpoint joinPoint, Object result) {
			System.out.println("모든 POST요청에 반응");
		}
	}
}
