package com.example.demo.config.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.DecimalFormat;
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
public class TcOrderShardingDatabaseAlgorithm implements PreciseShardingAlgorithm<Long> {

//	库ID = userId % 库数量4 
//	表ID = userId / 库数量4 % 表数量8 
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        Long value = preciseShardingValue.getValue();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        StringBuilder sb = new StringBuilder();
        //sb.append("ds").append(sdf.format(value));
        long id = value%16;
        sb.append("tc-sharding-").append(String.format("%02d", id));
        return sb.toString();
    }
    public static void main(String[] args) {
    	 DecimalFormat df = new DecimalFormat("00");  
    	 System.out.println( df.format(1));
    	// 0 代表前面补充0         
    	    // 4 代表长度为4         
    	    // d 代表参数为正数型  
    	 String str = String.format("%02d", 2); 
    	 System.out.println(str);
	}
}

