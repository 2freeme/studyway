package com.example.demo.config.sharding.aop;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.base.Result;
import com.example.demo.common.base.CodeEnumInterface.CodeEnumEnum;

//@Aspect   //这两个注解必须有
//@Component 
public class EsMsgSenderAspect{
	private final static Logger log = LoggerFactory.getLogger(EsMsgSenderAspect.class);

//	@Autowired
//    private RocketMQTemplate rocketMQTemplate;
	
	//定义切点位置:下面如果你在SSM中用AOP，在xml中配的就是下面
	@Pointcut("execution(* com.example.demo.module.service..*.*(..))")
	public void esMsgSend() {
	}
	
//    @Before("transantionMsgSend()")
//    public void doBefore(JoinPoint joinPoint){
//    	Object[] parames = joinPoint.getArgs();// 获取目标方法体参数
//        String className = joinPoint.getTarget().getClass().getSimpleName();// 获取目标类名
//        String signature = joinPoint.getSignature().getName();// 获取目标方法签名
//    	RequestHolder.add(className+"."+signature, JSON.toJSONString(parames));
//    }
//    
//    @AfterReturning(returning = "ret",pointcut = "transantionMsgSend()")
//    public void doAfter(JoinPoint joinPoint,Object ret) throws Exception{
//    	Map<String,Object> requestParams = RequestHolder.getCurrentRequest();
//    	boolean isSendMsg = false;
//    	if(ret instanceof Boolean ) {
//    		Boolean resultTemp = (Boolean) ret;
//    		isSendMsg = resultTemp;
//    	} if(ret instanceof Result){
//     	   Result<?> resultTemp = (Result<?>) ret;
//     	   if(CodeEnumEnum.SUCCESS.getValue() == resultTemp.getCode() 
//     			   && StringUtils.isEmpty(resultTemp.getMessage())){
//     		  isSendMsg = true;
//     	   }
//         }
//    	if(isSendMsg) {
//    		System.out.println("发送  json:"+JSON.toJSONString(requestParams));
//    	}
//    	RequestHolder.remove();
//    }
    
    
	
	/**
	 * 环绕通知记录时间
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("esMsgSend()")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
	    // 记录起始时间
	    long begin = System.currentTimeMillis();
	    Object result = "";
	    /** 执行目标方法 */
	    try {
	    	Object[] parames = joinPoint.getArgs();// 获取目标方法体参数
            String className = joinPoint.getTarget().getClass().getSimpleName();// 获取目标类名
            String signature = joinPoint.getSignature().getName();// 获取目标方法签名
            //String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));
	    	RequestHolder.add(className+"."+signature, JSON.toJSONString(parames));
	        result = joinPoint.proceed();
	        boolean isSendMsg = false;
	        if(result instanceof Boolean ) {
	    		Boolean resultTemp = (Boolean) result;
	    		isSendMsg = resultTemp;
	    	} if(result instanceof Result){
	     	   Result<?> resultTemp = (Result<?>) result;
	     	   if(CodeEnumEnum.SUCCESS.getValue() == resultTemp.getCode() 
	     			   && StringUtils.isEmpty(resultTemp.getMessage())){
	     		  isSendMsg = true;
	     	   }
	         }
	    	if(isSendMsg) {
	    		Map<String,Object> requestParams = RequestHolder.getCurrentRequest();
	    		System.out.println("发送  json:"+JSON.toJSONString(requestParams));
	    	}
	    } catch (Exception e) {
	        log.error("日志记录发生错误, errorMessage: {}", e.getMessage());
	    } finally {
	    	RequestHolder.remove();
	        /** 记录操作时间 */
	        long took = (System.currentTimeMillis() - begin) / 1000;
	        log.debug("controller执行时间为: {}秒", took);
	    }
	    return result;
	}
	
	
}
