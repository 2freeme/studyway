package com.example.demo.module.service.impl;

import com.example.demo.module.pojo.TTransactionMsg;
import com.example.demo.module.dao.TTransactionMsgMapper;
import com.example.demo.module.service.TTransactionMsgService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sh
 * @since 2020-12-05
 */
@Service
@Transactional
public class TTransactionMsgServiceImpl extends ServiceImpl<TTransactionMsgMapper, TTransactionMsg> implements TTransactionMsgService {

	@Override
	public List<TTransactionMsg> selectHint(IPage<TTransactionMsg> tTransactionPage,Wrapper<TTransactionMsg> queryWrapper,String databaseNum) {
		  HintManager.clear();
		  HintManager hintManager = HintManager.getInstance();
          hintManager.addDatabaseShardingValue("t_transaction_msg", databaseNum);
////	    hintManager.addTableShardingValue("t_media_order" , 3);
         return super.page(tTransactionPage, queryWrapper).getRecords();
	}

	@Override
	public Boolean deleteHint(Wrapper<TTransactionMsg> queryWrapper, String databaseNum) {
		HintManager.clear();
		HintManager hintManager = HintManager.getInstance();
        hintManager.addDatabaseShardingValue("t_transaction_msg", databaseNum);
        return super.remove(queryWrapper);
	}

}
