package com.example.zookeeperdemo.suo;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2019-12-17 20:06
 */
public class OrderNumGenerator {
    private static int i=0;
    public String getNumber() {
        return String.valueOf(i +=1);
    }
}
