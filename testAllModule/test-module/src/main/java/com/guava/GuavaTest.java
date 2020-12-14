//package com.guava;
//
//import com.google.common.util.concurrent.RateLimiter;
//
//import java.time.Duration;
//
///**
// * @Author： Dingpengfei
// * @Description：令牌桶算法
// * @Date： 2020/9/28 23:47
// */
//public class GuavaTest {
//    public static void main(String[] args) {
//        //每秒创造出的令牌
//        RateLimiter rateLimiter =RateLimiter.create(2);
//        //明天核实一下  可能api变了
//        rateLimiter.acquire();//拿令牌
//        rateLimiter.acquire(2);//拿令牌  每次拿2个令牌
//        rateLimiter.tryAcquire( Duration.ofSeconds(2000)) //2s中去取，不要一直的去等  异步的去判定  到时间直接的抛异常
//        rateLimiter.tryAcquire(2, Duration.ofSeconds(2000)); //2s中去取2个，不要一直的去等  异步的去判定  到时间直接的抛异常
//
//    }
//
//}
