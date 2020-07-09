package com.bigdata.boot.chapter10.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Aspect
@Component // 仅仅声明切面是不够的，还需要通过 @Component 注解，将切面引入IOC容器
public class ServiceMonitor {

	// 前置返回通知
	@AfterReturning("execution(* com..*Service.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		System.out.println("Completed: " + joinPoint);
	}

}