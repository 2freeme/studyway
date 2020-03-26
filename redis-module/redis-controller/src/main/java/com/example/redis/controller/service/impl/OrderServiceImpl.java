package com.example.redis.controller.service.impl;

import com.example.redis.controller.dao.OrderMapper;
import com.example.redis.controller.service.OrderService;
import com.studyway.redis.test.entity.Account;
import com.studyway.redis.test.entity.MyOrder;
import com.studyway.redis.test.entity.Strong;
import com.studyway.redis.test.service.AccountService;
import com.studyway.redis.test.service.StrongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 17:50
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    AccountService accountService;
    @Autowired
    StrongService strongService;

    @Override
    public void submitOrder(MyOrder order ) {
        Account account = new Account();
        account.setUserName(order.getUserName());
        account.setAmount(order.getAmount());
        accountService.createAccountFlow(account);

        Strong strong = new Strong();
        strong.setItemName(order.getItemName());
        strong.setQty(order.getQty());
        strong.setWareHouse(order.getWareHouse());

        strongService.updateStrong(strong);

        orderMapper.save(order);
    }
}
