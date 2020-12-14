package com.example.demo.module.service.jobhandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
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
import com.example.demo.module.pojo.TimeUnitEnum;
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
public class TransactionMsgCleanerJob {
    private static Logger logger = LoggerFactory.getLogger(TransactionMsgCleanerJob.class);
    @Autowired
    private TTransactionMsgService tTransactionMsgService;
    
    @Value("${transactionMsgJob.transactionMsg.pageSize}")
    private int pageSize;
    
    @Value("${transactionMsgJob.transactionMsg.retryTimes}")
    private int retryTimes;
    
    @Value("${transactionMsgJob.transactionMsg.holding_time}")
    private int holding_time;
    
    @Value("${transactionMsgJob.transactionMsg.holding_time_unit:d}")
    private String holding_time_unit;
    
   
    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("sharding_00_transactionMsgCleanHandler")
    public ReturnT<String> sharding_00_transactionMsgCleanHandler(String param) throws Exception {
        XxlJobLogger.log("sharding_00_transactionMsgCleanHandler start");
        cleanRecords("00");
        return ReturnT.SUCCESS;
    }
    
    @XxlJob("sharding_01_transactionMsgCleanHandler")
    public ReturnT<String> sharding_01_transactionMsgCleanHandler(String param) throws Exception {
    	 XxlJobLogger.log("sharding_01_transactionMsgCleanHandler start");
         cleanRecords("01");
         return ReturnT.SUCCESS;
    }
    
    
    @XxlJob("sharding_02_transactionMsgCleanHandler")
    public ReturnT<String> sharding_02_transactionMsgCleanHandler(String param) throws Exception {
    	 XxlJobLogger.log("sharding_02_transactionMsgCleanHandler start");
         cleanRecords("02");
         return ReturnT.SUCCESS;
    }
    
    
    @XxlJob("sharding_03_transactionMsgCleanHandler")
    public ReturnT<String> sharding_03_transactionMsgCleanHandler(String param) throws Exception {
    	 XxlJobLogger.log("sharding_03_transactionMsgCleanHandler start");
         cleanRecords("03");
         return ReturnT.SUCCESS;
    }

    
    private Date getRemainDay() {
    	 Calendar calendar = Calendar.getInstance();
    	 //#日誌保留時間单位：m（一個月）1d(一天)  1h(一小時)  1M(一分鐘)  1s(1秒)
    	 if(holding_time_unit.equals(TimeUnitEnum.MON.getName())) {
	   		 calendar.add(Calendar.MONTH, -holding_time);
	   	 }else if(holding_time_unit.equals(TimeUnitEnum.DAY.getName())) {
	   		 calendar.add(Calendar.DATE, -holding_time);
	   	 }else if(holding_time_unit.equals(TimeUnitEnum.HOUR.getName())) {
	   		 calendar.add(Calendar.HOUR, -holding_time);
	   	 }else if(holding_time_unit.equals(TimeUnitEnum.MIN.getName())) {
	   		 calendar.add(Calendar.MINUTE, -holding_time);
	   	 }else if(holding_time_unit.equals(TimeUnitEnum.SEC.getName())) {
	   		 calendar.add(Calendar.SECOND, -holding_time);
	   	 }else {
	   		 logger.warn("时间单位不匹配！！！");
	   		 calendar.add(Calendar.DATE, -holding_time);
	   	 }
    	 return  calendar.getTime();
    }
    
    /**
     *       处理消息记录
     * @param tTransactionMsgList
     */
    private void cleanRecords(String databaseNum) {
    	QueryWrapper<TTransactionMsg> queryWrapper = new QueryWrapper<>();
    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    	Date remainDay = getRemainDay();
    	queryWrapper.lt("create_time", remainDay);
   	    boolean ret = tTransactionMsgService.deleteHint(queryWrapper, databaseNum);
   	    logger.info("cleanRecords-->before: {},ret:{} ",
   	    		sdf1.format(remainDay),ret);
          
    }

   
}
