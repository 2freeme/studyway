package com.example.demo.config.sharding.aop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Aspect   //这两个注解必须有
@Component 
public class DaoAspect{
	private final static Logger log = LoggerFactory.getLogger(DaoAspect.class);
	
	//定义切点位置:下面如果你在SSM中用AOP，在xml中配的就是下面
	@Pointcut("execution(* com.example.demo.module.dao..*.*(..))")
	public void mapper() {
	}
	
	
	/**
	 * 前置通知
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("mapper()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		Object[] parames = joinPoint.getArgs();// 获取目标方法体参数
        String className = joinPoint.getTarget().getClass().getSimpleName();// 获取目标类名
        String signature = joinPoint.getSignature().getName();// 获取目标方法签名
    	RequestHolder.add(className+"."+signature, JSON.toJSONString(parames));
	}
	
}
