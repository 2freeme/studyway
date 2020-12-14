package com.example.demo.module.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.module.pojo.TcOrder;

/**
 * @title: OrderMapper
 * @projectName shardingdemo
 * @description: TODO
 * @author zhy
 * @date 2020/5/69:30
 */
@Mapper
public interface TcOrderMapper extends BaseMapper<TcOrder> {
	
	//多表联合查询 按条件orderID
	@Select("SELECT  o.*,od.* FROM  tc_order  o inner JOIN  tc_order_detail od on o.id = od.order_id where od.user_id= #{userid}")
	 List<Map<String,Object>> orderUserList(Page<Map<String,Object>> page,long userid);
	
	//多表联合查询 按条件orderID
		@Select("SELECT  o.* FROM  tc_order  o where 1=1  and exists (select 1 from tc_order_detail od WHERE o.id = od.order_id and o.user_id=#{userid} and od.user_id=#{userid})")
		 List<Map<String,Object>> orderUserList2(Page<Map<String,Object>> page,long userid);

}
