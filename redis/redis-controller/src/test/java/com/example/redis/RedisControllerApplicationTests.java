package com.example.redis;

import com.example.redis.controller.service.OrderService;
import com.example.redis.mdredis.JedisUtil;
import com.studyway.redis.test.entity.MyOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * 用作测试使用大批量的数据的时候 使用多线程去跑redis比较好还是 单线程比较好
 * 结论：在10w级数据的时候，从连接池中拿取20个线程，明显比单个线程跑的快
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.example.redis.controller.dao")
public class RedisControllerApplicationTests {
    @Autowired
    OrderService orderService;
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    JedisUtil jedisUtil;

    @Test
    public void contextLoads() {
        MyOrder myOrder = new MyOrder();
        myOrder.setItemName("手机");
        myOrder.setQty(1);
        myOrder.setUserName("dingpf1");
        orderService.submitOrder(myOrder);

    }

    @Test
    public void set() {
        redisTemplate.opsForValue().set("myKey", "myValue");
        System.out.println("测试" + redisTemplate.opsForValue().get("myKey"));
    }

    @Test
    public void testJedis() {
        try {
            Boolean dingpf = jedisUtil.execExistsFromCache("dingpf");
            System.out.println(dingpf);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test1() {
        long oldmin1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            jedisUtil.execSetToCache("test" + i, "vales" + i);
        }
        long new1 = System.currentTimeMillis();
        System.out.println("测试1 : " + (new1 - oldmin1)); //测试1 : 27982
        long oldmin2 = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            jedisUtil.execDelToCache("test" + i);
        }
        long new2 = System.currentTimeMillis();
        System.out.println("测试2 : " + (new2 - oldmin2)); //测试2 : 25692
        CountDownLatch n = new CountDownLatch(20);
        long old3 = System.currentTimeMillis();
        for (int i = 1; i < 21; i++) {
            final int m = i;
            new Thread(() -> {
                for (int j = 1; j < 5001; j++) {
                    jedisUtil.execSetToCache("test" + (m * j), "vales" + (m * j));
                }
                n.countDown();
            }).start();
        }
        try {
            n.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long new3 = System.currentTimeMillis();
        System.out.println("测试3: " + (new3 - old3)); //测试3: 12732

        for (int i = 0; i < 100000; i++) {
            jedisUtil.execDelToCache("test" + i);
        }
    }

    @Test
    public void test3() {
        String aa = null;
        String note = (aa == null ? "" : aa + ", ") + "由CIMS财务单[]自动生成出库单";
        System.out.println(note);

    }

    /**
     * 用来测试redisson
     */
    @Test
    public void testredisson() {
        //配置redission
        Config config = new Config();
        config.useSingleServer().setAddress("127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        //默认的也有 默认的就用的就是本地的机器
        RedissonClient redissonClient = Redisson.create();
        RLock lock = redissonClient.getLock("test");
        lock.lock( );

    }
}

