package com.example.demo.module.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.base.Result;
import com.example.demo.module.pojo.MediaOrder;
import com.example.demo.module.pojo.TcOrder;

/**
 * @title: OrderService
 * @projectName shardingdemo
 * @description: TODO
 * @author zhy
 * @date 2020/5/69:31
 */
@Component
public interface TcOrderService extends IService<TcOrder> {
	public Result<TcOrder> saveTcOrder(TcOrder order);
	
	public Result<TcOrder> saveTcOrderTranMsg(TcOrder order);
	
	 Page<Map<String,Object>> selectListPage(int current,int number,long user_id);
	 
	 Page<Map<String,Object>> selectListPage2(int current,int number,long user_id);
}
