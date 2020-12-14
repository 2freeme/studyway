package com.example.demo.module.service.impl;

import com.example.demo.module.pojo.TTransactionMsg;
import com.example.demo.module.dao.TTransactionMsgMapper;
import com.example.demo.module.service.TTransactionMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
