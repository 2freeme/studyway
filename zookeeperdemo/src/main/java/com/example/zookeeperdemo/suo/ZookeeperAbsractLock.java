package com.example.zookeeperdemo.suo;

import org.I0Itec.zkclient.ZkClient;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2019-12-17 19:32
 */
public abstract class ZookeeperAbsractLock implements Lock {
    //连接地址
    private final static String CONNECT_ADDRESS = "112.74.16.186";
    protected  ZkClient zkClient = new ZkClient(CONNECT_ADDRESS);
    private String pathUrl = "/lock";


    public void getLock() {
        if (tryLock()) {
            System.out.println("抢到锁了");
        } else {
            waitLock();
            int i = 0;
            System.out.println("未拿到锁 " + (i+=1));
            getLock();
        }
    }

    abstract void waitLock();

    abstract boolean tryLock();

    public void unLock() {
        if(zkClient != null){
            System.out.println("zkClient unLock " + zkClient);
            zkClient.close();
        }
    }


}
