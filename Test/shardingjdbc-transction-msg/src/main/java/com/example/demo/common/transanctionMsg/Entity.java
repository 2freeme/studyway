package com.example.demo.common.transanctionMsg;

import java.util.List;
import java.util.concurrent.Callable;

import lombok.Data;

@Data
public  class Entity {
    private List<Callable<?>> actions;
    private List<MessageEntity> messageEntities;
    private Boolean inTransaction;
    private Integer shardingDatabase;
    private String className;
    private String methodName;
}