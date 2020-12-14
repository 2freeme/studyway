package com.example.demo.config.sharding;

import java.util.Collection;
import java.util.Map;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import com.google.common.collect.Lists;

/**
 * 复合分片数据源
 * @author ADMIN
 *
 */
public class OrderDatasourceComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {
		Map<String, Collection<String>> columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
		if(columnNameAndShardingValuesMap.containsKey("user_id")){
			Collection<String> user_ids = columnNameAndShardingValuesMap.get("user_id");
			String user_id =String.valueOf( user_ids.iterator().next());
			long user_Id_mod  =  Long.parseLong(user_id)%4;
			for (String availableTargetName : availableTargetNames) {
				if(availableTargetName.endsWith(String.format("%02d", user_Id_mod))){
					System.out.println("=========datasource===========>"+availableTargetName);
					return Lists.newArrayList(availableTargetName);
				}
			}
		}
		if(columnNameAndShardingValuesMap.containsKey("order_no")){
			Collection<String> orderNos = columnNameAndShardingValuesMap.get("order_no");
			String orderNo = orderNos.iterator().next();
			String datasource = orderNo.substring(orderNo.length()-4, orderNo.length()-2);
			for (String availableTargetName : availableTargetNames) {
				if(availableTargetName.endsWith(datasource)){
					return Lists.newArrayList(availableTargetName);
				}
			}
		}
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		String orderNo = "P1003400014015";
		String datasource = orderNo.substring(orderNo.length()-5, orderNo.length()-3);
		String table = orderNo.substring(orderNo.length()-3);
		System.out.println(datasource);
		System.out.println(table);
	}
}
