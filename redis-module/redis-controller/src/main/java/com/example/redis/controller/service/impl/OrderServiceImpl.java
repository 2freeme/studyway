package com.example.redis.controller.service.impl;

import com.example.redis.controller.dao.MyorderDao;
import com.example.redis.controller.service.AccountFeignService;
import com.example.redis.controller.service.OrderService;
import com.example.redis.controller.service.StrongFeignService;
import com.studyway.redis.test.entity.Account;
import com.studyway.redis.test.entity.Myorder;
import com.studyway.redis.test.entity.Strong;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 17:50
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    MyorderDao myorderDao;

    @Autowired
    AccountFeignService accountService;
    @Autowired
    StrongFeignService strongService;

    @Override
    public void submitOrder(Myorder order ) {
        System.out.println("com.example.redis.controller.service.impl.OrderServiceImpl.submitOrder" +order.toString());
        Account account = new Account();
        BeanUtils.copyProperties(order,account );
        account.setUserName(order.getUserName());
        accountService.createAccountFlow(account);

        Strong strong = new Strong();
        strong.setItemName(order.getItemName());
        strong.setQty(order.getQty());
        strong.setWareHouse(order.getWareHouse());

        strongService.updateStrong(strong);

        myorderDao.insert(order);
    }
}
