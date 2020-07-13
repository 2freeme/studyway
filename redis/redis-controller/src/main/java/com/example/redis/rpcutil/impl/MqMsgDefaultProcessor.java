//package com.example.redis.rpcutil.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.example.redis.rpcutil.MqMsgProcesser;
//import com.example.redis.rpcutil.RedisController;
//import com.example.redis.rpcutil.SpringContextHolder;
//import com.midea.ccs.rpcutil.pojo.CheckResult;
//import com.midea.ccs.rpcutil.pojo.MqMsg;
//import com.midea.ccs.rpcutil.util.RestFullApiUtils;
//import com.midea.ccs.rpcutil.util.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.io.Serializable;
//
///**
// * Created by zhaolp on 2017/6/29 0029.
// * 处理消费者信息
// */
//@Service
//public class MqMsgDefaultProcessor implements MqMsgProcesser {
//    private static final Logger logger = LoggerFactory.getLogger(MqMsgDefaultProcessor.class);
//    public static RedisController getRedisController(){
//        return SpringContextHolder.getBean(DefaultRedisController.class);
//    }
//    @Override
//    public void process(Serializable obj, Class resultClass) {
//        try {
//            logger.info("####消费程序信息: "+obj.toString());
//            long start = System.currentTimeMillis();
//
//            //消费程序打印信息
//            if(obj instanceof String){
//                if(((String) obj).contains("webhook")){
//                    MqMsg msg= JSON.parseObject(obj.toString(),MqMsg.class);
//                    String strmsg=msg.getMsg();
//                    String queue=msg.getQueueName();
//                    String exchange=msg.getExchangeName();
//                    String webhook=msg.getWebhook();
//                    if(!StringUtils.isEmpty(webhook)){
//                        if(!StringUtils.isEmpty(queue) && !StringUtils.isEmpty(exchange) && !StringUtils.isEmpty(strmsg) ){
//                            logger.debug("正在消费队列["+queue+"] 交换器["+exchange+"]的消息 "+strmsg);
//                        }
//                        String url = webhook.replace("{", "%7B").replace("}", "%7D");
//                        String reststr= RestFullApiUtils.getRestTemplate().getForObject(url, String.class, msg.getUrlParam());
//                        CheckResult rest= JSON.parseObject(reststr,CheckResult.class);
//
//                        long end = System.currentTimeMillis();
//
//                        if(rest != null) {
//                        	if(rest.isStat()){
//                                logger.debug(msg.getWebhook()+" 处理成功， 耗时：" + (end-start));
//                            }else{
//                                String errormsg=StringUtils.isEmpty(rest.getErrorMsg())?"":rest.getErrorMsg();
//                                logger.error(msg.getWebhook()+" 处理失败,，耗时 " + (end-start) + "，原因："+errormsg);
//                                throw new RuntimeException(errormsg);
//                            }
//                        } else {
//                        	logger.debug(msg.getWebhook()+" 返回结果为null， 耗时：" + (end-start));
//                        }
//
//                    }
//                }
//
//
//            }
//        }catch (Exception e){
//            logger.error("消费者回调处理失败！原因："+e.getMessage(), e);
//            if(obj instanceof String) {
//                if (((String) obj).contains("webhook")) {
//                    MqMsg msg= JSON.parseObject(obj.toString(),MqMsg.class);
//                    String policy=msg.getPolicy();
//                    if(!StringUtils.isEmpty(policy)){
//                        if(policy.startsWith("retry_")){
//                            int totalretry=Integer.valueOf(policy.substring("retry_".length(),policy.length()));
//                            String tempkey="CCS_MQ_RETRY_"+msg.getMsg();
//                            try {
//                                getRedisController().incr(tempkey);
//                                getRedisController().expire(tempkey,24*60*60);//设置过期时间为一天
//                            }catch (Exception ex){
//                            	logger.error(ex.getMessage(), ex);
//                                throw new RuntimeException(ex);//回滚
//                            }
//                            int redis_retry=Integer.valueOf(getRedisController().get(tempkey)).intValue();
//                            if(redis_retry<=totalretry){
//                                logger.warn("webhook url: "+msg.getWebhook()+"  retry "+redis_retry+" times!!!");
//                                throw new RuntimeException(e);//回滚
//                            }else{
//                                logger.error("webhook url: "+msg.getWebhook()+"  retry over "+totalretry+" times,so give up it!!!");
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//}
