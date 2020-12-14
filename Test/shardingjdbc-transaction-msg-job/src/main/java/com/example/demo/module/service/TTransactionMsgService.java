package com.example.demo.module.service;

import com.example.demo.module.pojo.TTransactionMsg;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sh
 * @since 2020-12-05
 */
public interface TTransactionMsgService extends IService<TTransactionMsg> {
	
	public List<TTransactionMsg> selectHint (IPage<TTransactionMsg> tTransactionPage,Wrapper<TTransactionMsg> queryWrapper,String databaseNum);
    
	public Boolean deleteHint (Wrapper<TTransactionMsg> queryWrapper,String databaseNum);
}
