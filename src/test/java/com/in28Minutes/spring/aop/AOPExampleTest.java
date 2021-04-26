package com.in28Minutes.spring.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AopConfig.class)
public class AOPExampleTest {
	
	@Autowired
	private HiByeService service;
	
	@Test
	public void testAop() {
		service.sayHi("jane");
		service.sayBye();
		service.returnSomething();
	}
}

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.in28Minutes.spring.aop")
class AopConfig {

}

@Aspect
@Component
class MyAspect {
	@Before("execution(* com.in28Minutes.spring.aop.HiByeService.*(..))")
	public void before(JoinPoint joinPoint) {
		System.out.print("Before  ");
		System.out.print(joinPoint.getSignature().getName() + " called with ");
		System.out.println(Arrays.toString(joinPoint.getArgs()));
	}
	
	@After("execution(* com.in28Minutes.spring.aop.HiByeService.*(..))")
	public void after(JoinPoint joinPoint) {
		System.out.print("After  ");
		System.out.println(joinPoint.getSignature().getName());
	}
	
	@AfterReturning(pointcut = "execution(* com.in28Minutes.spring.aop.HiByeService.*(..))",
					returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		System.out.print("After returning  ");
		System.out.println(joinPoint.getSignature().getName());
		System.out.println(" result is "+ result);
	}
	
	/*
	 * @Around("execution(* com.in28Minutes.spring.aop.HiByeService.*(..))") public
	 * void around(JoinPoint joinPoint) { System.out.print("After  ");
	 * System.out.println(joinPoint.getSignature().getName()); }
	 */
}

@Component
class HiByeService{
	public void sayHi(String param) {
		System.out.println("Hi " + param);
	}
	
	public void sayBye() {
		System.out.println("Bye");
	}
	
	public String returnSomething() {
		return "Something";
	}
}
