package com.example.demo.module.service.jobhandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.transanctionMsg.MessageEntity;
import com.example.demo.module.mq.rocketmq.MQProducer2;
import com.example.demo.module.pojo.TTransactionMsg;
import com.example.demo.module.service.TTransactionMsgService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.ShardingUtil;

/**
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Component
public class TransactionMsgJob {
    private static Logger logger = LoggerFactory.getLogger(TransactionMsgJob.class);
    @Autowired
    private TTransactionMsgService tTransactionMsgService;
    @Autowired
    private MQProducer2 mQProducer2;
    
    @Value("${transactionMsgJob.transactionMsg.pageSize}")
    private int pageSize;
    
    @Value("${transactionMsgJob.transactionMsg.retryTimes}")
    private int retryTimes;
    
    @Value("${transactionMsgJob.transactionMsg.holding_time}")
    private int holding_time;
    
    @Value("${transactionMsgJob.transactionMsg.holding_time_unit}")
    private String holding_time_unit;
    
   
    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("sharding_00_transactionMsgRetryHandler")
    public ReturnT<String> sharding_00_transactionMsgRetryHandler(String param) throws Exception {
        XxlJobLogger.log("sharding00_transactionMsgRetryHandler start");
        transanctionMsgRetryHandler("00");
        return ReturnT.SUCCESS;
    }
    
    @XxlJob("sharding_01_transactionMsgRetryHandler")
    public ReturnT<String> sharding_01_transactionMsgRetryHandler(String param) throws Exception {
        XxlJobLogger.log("sharding01_transactionMsgRetryHandler start");
        transanctionMsgRetryHandler("01");
        return ReturnT.SUCCESS;
    }
    
    
    @XxlJob("sharding_02_transactionMsgRetryHandler")
    public ReturnT<String> sharding_02_transactionMsgRetryHandler(String param) throws Exception {
        XxlJobLogger.log("sharding02_transactionMsgRetryHandler start");
        transanctionMsgRetryHandler("02");
        return ReturnT.SUCCESS;
    }
    
    
    @XxlJob("sharding_03_transactionMsgRetryHandler")
    public ReturnT<String> sharding_03_transactionMsgRetryHandler(String param) throws Exception {
        XxlJobLogger.log("sharding03_transactionMsgRetryHandler start");
        transanctionMsgRetryHandler("03");
        return ReturnT.SUCCESS;
    }

    
    public void transanctionMsgRetryHandler(String databaseNum) {
    	 QueryWrapper<TTransactionMsg> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("status", 0);//状态：0 待发送 1：发送成功 2：发送失败',
         queryWrapper.orderByAsc("create_time");
         //queryWrapper.le("retry_times", retryTimes);
         int pageNo = 0;
         while(true) {
        	 IPage<TTransactionMsg> tTransactionPage = new Page<>(pageNo, pageSize);
        	 List<TTransactionMsg> tTransactionMsgList = tTransactionMsgService.selectHint(tTransactionPage,queryWrapper, databaseNum);
        	 if(CollectionUtils.isEmpty(tTransactionMsgList)) {
        		 break;
        	  }
        	 proccesRecords(tTransactionMsgList);
        	 pageNo++;
         }
        
    }
    
    /**
     *       处理消息记录
     * @param tTransactionMsgList
     */
    private void proccesRecords(List<TTransactionMsg> tTransactionMsgList) {
    	 if(!CollectionUtils.isEmpty(tTransactionMsgList)) {
          	for(TTransactionMsg tmsg : tTransactionMsgList) {
          		try {
          			if(tmsg.getRetryTimes() >= retryTimes ) {
            			  tmsg.setStatus(2);
            			  tmsg.setUpdateTime(LocalDateTime.now());
                   	      UpdateWrapper<TTransactionMsg> updateWrapper=new UpdateWrapper<>();
                   	      updateWrapper.eq("id",tmsg.getId());
                   	      updateWrapper.eq("sharding_column", tmsg.getShardingColumn());
                   	      boolean ret = tTransactionMsgService.update(tmsg, updateWrapper);
                   	      logger.info("TTransactionMsg id:{},sharding_column:{} 超过最大重试次数, 更新发送失败：{} ",
                   	    		  tmsg.getId(),tmsg.getShardingColumn(),ret);
                		}else {
                			MessageEntity entity = new MessageEntity();
                			entity.setTopic(tmsg.getMqTopic());
                			entity.setContent(tmsg.getMessage());
                			MessageEntity ret = mQProducer2.sendMessage(entity);
                			int status = ret != null? 1 :0;
            				tmsg.setStatus(status);
               			    tmsg.setUpdateTime(LocalDateTime.now());
               			    tmsg.setRetryTimes(tmsg.getRetryTimes()+1);
                      	    UpdateWrapper<TTransactionMsg> updateWrapper=new UpdateWrapper<>();
                      	    updateWrapper.eq("id",tmsg.getId());
                      	    updateWrapper.eq("sharding_column", tmsg.getShardingColumn());
                			tTransactionMsgService.update(tmsg, updateWrapper);
                			 logger.info("TTransactionMsg:{},restryTimes:{},重试：{} ",
                      	    		  JSONObject.toJSONString(tmsg),tmsg.getRetryTimes(),ret);
                		}
          		}catch (Exception e) {
          			logger.error("transanctionMsgRetryHandler-->ttMsg:"+JSONObject.toJSONString(tmsg),e);
 				}
          		
          	}
          	
          }
    }

   
}
