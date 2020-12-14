package com.example.demo.config.sharding;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Collection;

public final class DatabaseHintShardingAlgorithm implements HintShardingAlgorithm<String> {
    
    @Override
    public Collection<String> doSharding(final Collection<String> availableTargetNames, final HintShardingValue<String> shardingValue) {
        Collection<String> result = new ArrayList<>();
        for (String each : availableTargetNames) {
            for (String databseno : shardingValue.getValues()) {
                if (each.endsWith(String.valueOf(databseno))) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}