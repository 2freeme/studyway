package com.example.demo.config.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @title: CarParkShardingTableAlgorithm
 * @projectName shardingdemo
 * @description: 按停车场id分表
 * @author zhy
 * @date 2020/5/611:25
 */
public class TcOrderShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {
//	库ID = userId % 库数量4 
//	表ID = userId / 库数量4 % 表数量8 
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        StringBuilder sb = new StringBuilder();
        Long value = preciseShardingValue.getValue();
        String logicTableName = preciseShardingValue.getLogicTableName();
        Long mod = value/16%32;
        sb.append(logicTableName).append("_").append(String.format("%02d", mod));
        return sb.toString();
    }
    //DB1T3
    
}

