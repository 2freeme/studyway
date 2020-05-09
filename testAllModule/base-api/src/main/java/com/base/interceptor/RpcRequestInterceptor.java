//package com.base.interceptor;
//
//import com.midea.ccs.core.dto.request.BaseRequest;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 拦截rpc层远程调用，将数据权限过滤信息{userId,setsOfBooksID,orgId,orgIdList}
// * 装填至以BaseRequest为参数的方法中
// *
// * @author XYX
// * @version 1.00.00
// *
// */
//@Aspect
//public class RpcRequestInterceptor {
//
//    private static final Logger logger = LoggerFactory.getLogger(RpcRequestInterceptor.class.getName());
//
//    //作为中间变量，用于保存用户信息
//    private ThreadLocal<BaseRequest> threadLocal = new ThreadLocal<BaseRequest>();
//    //计数器，记录进入facade层数，用于退出相应层级时清空threadLocal里面值
//    private ThreadLocal<Integer> counter = new ThreadLocal<Integer>();
//
//    //对接口插入切面，否则在rpc层调用其它rpc模块时不能先从threadLocal中获取已塞进去的用户信息拿出来
//    @Around("execution(* com.midea.ccs..*.facade..*.*(..))")
//    public Object aroundFacadeImplMethod(ProceedingJoinPoint pjp) throws Throwable {
//    	long start = System.currentTimeMillis();
//        try {
//            increase();
//            logger.debug("进了RPC端的request interceptor(" + counter.get() + "次):" + pjp.getSignature().toString());
//
//            Object[] args = pjp.getArgs();
//
//            if (args != null && args.length > 0) {
//                if (args[0] != null && args[0] instanceof BaseRequest) { // 左边第一个
//                    BaseRequest arg = (BaseRequest) args[0];
//
//                    if (threadLocal.get() == null) { // case1: Web invoke RPC
//                        UserInfo userInfo = new UserInfo();
//                        BeanUtils.copyProperties(arg, userInfo);
//                        threadLocal.set(userInfo);
//                    } else { // case2: RPC invoke RPC
//                        BaseRequest source = threadLocal.get();
//                        List<String> ignores = buildIgnoreList(source,arg);
//                        String[] ignoreArray = ignores.toArray(new String[ignores.size()]);
//                        BeanUtils.copyProperties(source, arg, ignoreArray);
//                    }
//                }
//            }
//
//            return pjp.proceed(args);
//
//        } catch (Exception e) {
//            logger.error("RPC调用时遇到异常:" + e.getMessage(), e);
//            throw e;
//        } finally {
//            Integer cnt = minus();
//            if (cnt <= 0) {
//                threadLocal.remove();
//                counter.remove();
//            }
//
//            try {
//            	long end = System.currentTimeMillis();
//                logger.debug("RPC invoke time " + (end -start) + " ms (" + pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName() +")");
//            } catch (Exception ex) {
//            }
//        }
//    }
//
//    private Integer increase() {
//        Integer cnt = counter.get();
//        if (cnt == null)
//            cnt = 0;
//        counter.set(++cnt);
//        return counter.get();
//    }
//
//    private Integer minus() {
//        Integer cnt = counter.get();
//        counter.set(--cnt);
//        return counter.get();
//    }
//
//    // 用来临时保存权限数据使用
//    private static class UserInfo extends BaseRequest {
//    }
//
//    private List<String> buildIgnoreList(BaseRequest source,BaseRequest arg) throws Exception {
//        List<String> list = new ArrayList<String>();
//
//        Field[] fields = BaseRequest.class.getDeclaredFields();
//
//        for (Field field : fields) {
//            field.setAccessible(true);
//            if (field.get(source) == null) {
//                list.add(field.getName());
//            }
//        }
//
//        for (Field field : fields) {
//            field.setAccessible(true);
//            if (field.get(arg) != null) {
//                if(!list.contains(field.getName())){
//                    list.add(field.getName());
//                }
//            }
//        }
//        return list;
//    }
//}
