package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.Order;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class RefundOrderServiceImpl  {

    private static final Logger logger = LoggerFactory.getLogger(RefundOrderServiceImpl.class);

    @Autowired
    private RedissonClient redissonClient;  //redis

    @Autowired
    private ThreadPoolTaskExecutor scheduledThreadPoolExecutor; //线程

    //读取阻塞队列,多线程执行 //正常订单超时未付款自动关闭
    public void refundOrder() {
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque("jiuyang_order_message");//正常订单超时未付款自动关闭
        scheduledThreadPoolExecutor.execute(new SendingTask(blockingDeque));
    }



    //内部类,获取队列消息
    public class SendingTask implements Runnable {
        RBlockingDeque<String> blockingDeque = null;
        public SendingTask(RBlockingDeque<String> blockingDeque) {
            this.blockingDeque = blockingDeque;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    String orderStr = blockingDeque.take();
                    Order order = JSONObject.parseObject(orderStr, Order.class);
                    scheduledThreadPoolExecutor.execute(new BusiTask(order));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();

                }
            }
        }
    }

    //处理队列消息
    public class BusiTask implements Runnable {
        Order order ;
        public BusiTask(Order order) {
            this.order = order;
        }

        @Override
        public void run() {
            try {
                if (order != null) {
                    System.out.println("---------自动取消订单业务开始处理----------");
                    Long orderId = order.getId();
                    String orderSn = order.getNamme();
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println("处理结束  "+"orderId   "+orderId+ "   orderSn   "+orderSn+"时间      " +sdf.format(new Date()));
                }
            } catch (Exception e) {
            	logger.error("BusiTask.error!",e);
            }
        }
    }





}
