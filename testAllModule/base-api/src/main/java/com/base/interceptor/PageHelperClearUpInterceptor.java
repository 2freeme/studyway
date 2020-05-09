package com.base.interceptor;

import com.base.util.VersionControlHelper;
import com.github.pagehelper.SqlUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 修复pageHelper分页插件、版本控制插件 线程污染问题(service方法)
 * 
 * @author
 * @version 1.00.00
 *
 */
@Aspect
public class PageHelperClearUpInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(PageHelperClearUpInterceptor.class.getName());
    
    //修复pageHelper分页插件、版本控制插件 线程污染问题
    @Around("execution(* com.midea.ccs..*.service..*.*(..))")
    public Object aroundServiceMethod(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object[] args = pjp.getArgs();
            return pjp.proceed(args);
        } finally {
        	if(SqlUtil.getLocalPage() != null) {
        		SqlUtil.clearLocalPage();
        		logger.debug("发现分页插件把线程污染，已清理");
        	}
        	
        	if(VersionControlHelper.getVersionControl() != null) {
        		VersionControlHelper.removeVersionControl();
        		logger.debug("发现版本控制插件把线程污染，已清理");
        	}
        }
    }
}
