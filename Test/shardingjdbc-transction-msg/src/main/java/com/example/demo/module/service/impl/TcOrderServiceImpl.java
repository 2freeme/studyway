package com.example.demo.module.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.base.Result;
import com.example.demo.common.transanctionMsg.MessageEntity;
import com.example.demo.common.utils.IdGenerator;
import com.example.demo.module.dao.TcOrderDetailMapper;
import com.example.demo.module.dao.TcOrderMapper;
import com.example.demo.module.mq.rocketmq.MQProducer2;
import com.example.demo.module.pojo.TcOrder;
import com.example.demo.module.pojo.TcOrderDetail;
import com.example.demo.module.service.TcOrderService;

/**
 * @title: OrderServiceImpl
 * @projectName shardingdemo
 * @description: TODO
 * @author zhy
 * @date 2020/5/69:31
 */
@Service
public class TcOrderServiceImpl extends ServiceImpl<TcOrderMapper, TcOrder> implements TcOrderService {
    @Autowired
    private TcOrderMapper tcOrderMapper;
    @Autowired
    private TcOrderDetailMapper tcOrderDetailMapper;	
    @Autowired
    private  MQProducer2 mqProducer2;
	@Override
	public Result<TcOrder> saveTcOrder(TcOrder order) {
		tcOrderMapper.insert(order);
		for(TcOrderDetail tcOrderDetail : order.getOrderdetailList()) {
			tcOrderDetailMapper.insert(tcOrderDetail);
		}
		return null;
	}
	
	
	
	
	@Override
	public Page<Map<String, Object>> selectListPage(int current, int number, long user_id) {
		//新建分页
	    Page<Map<String,Object>> page =new Page<Map<String,Object>>(current,number);
	    //返回结果
	    return page.setRecords(this.baseMapper.orderUserList(page, user_id));
	}
	@Override
	public Page<Map<String, Object>> selectListPage2(int current, int number, long user_id) {
		//新建分页
	    Page<Map<String,Object>> page =new Page<Map<String,Object>>(current,number);
	    //返回结果
	    return page.setRecords(this.baseMapper.orderUserList2(page, user_id));
	}




	@Override
	@Transactional
	public Result<TcOrder> saveTcOrderTranMsg(TcOrder order) {
		tcOrderMapper.insert(order);
		MessageEntity msgEntity = new MessageEntity();
		msgEntity.setTopic("newTopic");
		msgEntity.setShardingColumn(order.getUser_id());
		msgEntity.setTag("A");
		msgEntity.setContent("tcOrderMapper.insert");
		msgEntity.setId(IdGenerator.genId());
		mqProducer2.sendMessage(msgEntity);
		for(TcOrderDetail tcOrderDetail : order.getOrderdetailList()) {
			tcOrderDetailMapper.insert(tcOrderDetail);
		}
		MessageEntity msgEntity2 = new MessageEntity();
		msgEntity2.setTopic("newTopic");
		msgEntity2.setShardingColumn(order.getUser_id());
		msgEntity2.setTag("B");
		msgEntity2.setContent("tcOrderDetailMapper.insert");
		msgEntity2.setId(IdGenerator.genId());
		mqProducer2.sendMessage(msgEntity2);
		return null;
	}

	
   
	
}
