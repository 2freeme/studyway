package com.example.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedisConfig {



    @Bean
    public RedissonClient createRedisAPi(){
        //redis集群配置 start
       /* Config redissonConfig = new Config();
        //改用redisson后为了之间数据能兼容，这里修改编码为org.redisson.client.codec.StringCodec
        redissonConfig.setCodec(new org.redisson.client.codec.StringCodec());
        ClusterServersConfig clusterServersConfig = redissonConfig.useClusterServers();
        clusterServersConfig.setScanInterval(2000)
                .addNodeAddress("redis://10.12.11.19:7000")
                .addNodeAddress("redis://10.12.11.19:7001")
                .addNodeAddress("redis://10.12.11.19:7002")
                .addNodeAddress("redis://10.12.11.19:7003")
                .addNodeAddress("redis://10.12.11.19:7004")
                .addNodeAddress("redis://10.12.11.19:7005");
        //设置对于master节点的连接池中连接数最大为500
        clusterServersConfig.setMasterConnectionPoolSize(500);
        //设置密码
//        clusterServersConfig.setPassword("1qaz@WSX");
        //设置对于slave节点的连接池中连接数最大为500
        clusterServersConfig.setSlaveConnectionPoolSize(500);
        //如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，
        // 那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
        clusterServersConfig.setIdleConnectionTimeout(10000);
        //同任何节点建立连接时的等待超时。时间单位是毫秒。
        clusterServersConfig.setConnectTimeout(30000);
        //等待节点回复命令的时间。该时间从命令发送成功时开始计时。
        clusterServersConfig.setTimeout(3000);
        clusterServersConfig.setPingTimeout(30000);
        //当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
        clusterServersConfig.setReconnectionTimeout(3000);
        //redis集群配置 end
        return Redisson.create(redissonConfig);*/

        //单redis连接 start
        Config redissonConfig = new Config();
        SingleServerConfig singleServerConfig = redissonConfig.useSingleServer();
        singleServerConfig.setAddress("redis://192.168.43.101:6379");
       // singleServerConfig.setPassword("zsq2170");
        //  设置redis几号数据库
        singleServerConfig.setDatabase(4);
        singleServerConfig.setClientName("jiuyue");
        singleServerConfig.setConnectTimeout(10000);
        singleServerConfig.setConnectionPoolSize(300);
        return Redisson.create(redissonConfig);
        //单redis连接 end
    }
}
