package com.example.redis.controller.tran.impl;

import com.example.redis.controller.dao.MyorderDao;
import com.example.redis.controller.service.OrderService;
import com.example.redis.controller.tran.TransService;
import com.example.redis.controller.tran.TransService2;
import com.studyway.redis.test.entity.Account;
import com.studyway.redis.test.entity.MyOrder;
import com.studyway.redis.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author： Dingpengfei
 * @Description： 用作测试事务
 * @Date： 2020/6/4 23:20
 */
@Service
public class TransServiceImpl implements TransService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TransServiceImpl.class);

    @Autowired
    OrderService orderService;

    @Autowired
    TransService2 transService2;
    @Autowired
    AccountService accountService;
    @Autowired
    MyorderDao myorderDao;


    @Override
    @Transactional
    public void transCeshi1() {
//        System.out.println(1/a);
        myorderDao.insert(new MyOrder(111, "qq", "1111", null, new Date(), "transCeshi1", null, null, null));
        log.debug("-------------------------------------------------");
        System.out.println("==================");
        this.transCeshi2();
    }

    @Override
    @Transactional
    public void transCeshi2() {
        //在测试中已经验证，当线程首先进去 transCeshi1 的方法的时候，然后在transCeshi1中调用transCeshi2的方法
        //在transCeshi2中我们这个其实并不会回滚的
        myorderDao.insert(new MyOrder(1221, "qq", "222", null, new Date(), "transCeshi2", null, null, null));
        transCeshi3();
        //System.out.println(1 / 0);
    }

    @Override
    @Transactional
    public void transCeshi3() {

        myorderDao.insert(new MyOrder(1221, "qq", "333", null, new Date(), "transCeshi3", null, null, null));
        // System.out.println(1 / 0);
        System.out.println("时间 =====================" + new Date());
        Account account = new Account(1213, "etst", 24d, new Date(), "test", new Date(), "test", "ceshi");
        try {
            accountService.createAccountFlow(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        transCeshi4();

    }

    @Transactional
    public void transCeshi4() {
        myorderDao.insert(new MyOrder(1221, "qq", "444", null, new Date(), "transCeshi4", null, null, null));
        try {
            transService2.ceshi1();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(" transCeshi4===============");
        Account account = new Account(1213, "etst", 24d, new Date(), "test", new Date(), "test", "ceshi4");
        accountService.createAccountFlow2(account);
    }
    //当我们A B类的事务都是都是一个事务的时候，B事务已经监控到了异常，即使再上面catch了
    //他还是会回滚的。而且在执行完程序的时候会抛出异常如下
 /*   即整个程序其实走下来没什么问题。但是在执行结束的时候，
     spring的AOP结束，准备提交或者回滚事务。但是他回去查看事务。但是他发现事务已经回滚了。
     就会抛出异常（在分布式系统中这样及其危险，切记）*/
    //org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only
}
