package com.example.demo.config.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @title: CarParkShardingTableAlgorithm
 * @projectName shardingdemo
 * @description: 按创建时间分库
 * @author zhy
 * @date 2020/5/611:25
 */
public class MediaShardingDatabaseAlgorithm implements PreciseShardingAlgorithm<Long> {

//	库ID = userId % 库数量4 
//	表ID = userId / 库数量4 % 表数量8 
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        Long value = preciseShardingValue.getValue();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        StringBuilder sb = new StringBuilder();
        //sb.append("ds").append(sdf.format(value));
        long id = value%2;
        sb.append("ms-ds-").append(id);
        return sb.toString();
    }
}

