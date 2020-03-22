package com.example.zookeeperdemo.test;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2019-12-17 15:32
 */
public class ZookeeperTest {


    //连接地址
    private static final String ADDRES = "112.74.16.186:2181";
    //session 会话
    private static final int SESSION_OUTTIME = 2000;
    //信号量,阻塞程序执行,用户等待zookeeper连接成功,发送成功信号，
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        Thread.currentThread().setName("主线程");
        ZooKeeper zk = new ZooKeeper(ADDRES, SESSION_OUTTIME, new Watcher() {

            public void process(WatchedEvent event) {
                System.out.println("次线程 "+Thread.currentThread().getName());
                // 获取事件状态
                Watcher.Event.KeeperState keeperState = event.getState();
                // 获取事件类型
                Event.EventType eventType = event.getType();
                if (Event.KeeperState.SyncConnected == keeperState) {
                    if (Event.EventType.None == eventType) {
                        countDownLatch.countDown();
                        System.out.println("zk 启动连接...");
                    }
                    System.out.println("==============="+eventType);
                    if (Event.EventType.NodeCreated == eventType){
                        System.out.println("节点被创建");
                    }
                }


            }
        });
        // 进行阻塞
        countDownLatch.await();
        //这里是为了在创建的时候就进行阻塞，因为其是另开的线程，而且会有监听的通知，所以我们这里的阻塞就是等连接完毕。
        //连接的话是另一个线程去的，所以需要等待
        System.out.println(Thread.currentThread().getName()+"主线程");
        String result = zk.create("/itmayeidu_Lasting2", "Lasting".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        System.out.println("result"+result);
        zk.close();
    }


}
