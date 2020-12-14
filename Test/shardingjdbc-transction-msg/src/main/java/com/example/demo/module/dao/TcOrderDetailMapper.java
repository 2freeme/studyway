package com.example.demo.module.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.module.pojo.TcOrderDetail;

/**
 * @title: OrderMapper
 * @projectName shardingdemo
 * @description: TODO
 * @author zhy
 * @date 2020/5/69:30
 */
@Mapper
public interface TcOrderDetailMapper extends BaseMapper<TcOrderDetail> {
}
