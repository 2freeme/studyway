package com.example.demo.module.service;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.base.Result;
import com.example.demo.module.pojo.MediaOrder;

/**
 * @title: OrderService
 * @projectName shardingdemo
 * @description: TODO
 * @author zhy
 * @date 2020/5/69:31
 */
@Component
public interface MediaOrderService extends IService<MediaOrder> {
	public Result<MediaOrder> saveMediaOrder(MediaOrder order);
}
