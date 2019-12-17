package com.example.zookeeperdemo.suo;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2019-12-17 19:30
 */
public interface Lock {
    //获取到锁的资源
    public void getLock();
    // 释放锁
    public void unLock();
}

