package com.example.demo.common.transanctionMsg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;
@Component
public class TransactionInterceptorHandler {
    public  ThreadLocal<Entity> cache = ThreadLocal.withInitial(() -> null);

    public  void clear() {
        cache.remove();
    }

    public  boolean hasTransaction() {
        Entity e = cache.get();
        if (e == null) {
            return false;
        }
        return e.getInTransaction() != null && e.getInTransaction();
    }

    public  List<Callable<?>> getActions() {
        Entity e = cache.get();
        if (e == null) {
            return Collections.emptyList();
        }
        return e.getActions();
    }
    
    public  List<MessageEntity> getMessagess() {
        Entity e = cache.get();
        if (e == null) {
            return Collections.emptyList();
        }
        return e.getMessageEntities();
    }
    
    public  String getClassName() {
        Entity e = cache.get();
        if (e == null) {
            return null;
        }
        return e.getClassName();
    }
    
    
    public  String getMethodName() {
        Entity e = cache.get();
        if (e == null) {
            return null;
        }
        return e.getMethodName();
    }
 
    public  void setShardingDatabase(Integer databaseNum) {
    	Entity e= cache.get();
    	e.setShardingDatabase(databaseNum);
    }
    
    public  Integer getShardingDatabase() {
    	Entity e= cache.get();
    	return e.getShardingDatabase();
    }
    
    public  void clearShardingDatabase() {
    	Entity e= cache.get();
    	e.setShardingDatabase(null);
    }
    
    public  void signInTransaction(String className,String methodName) {
        Entity e= cache.get();
        if (e == null) {
            e = new Entity();
            e.setActions(new ArrayList<>());
            e.setMessageEntities(new ArrayList<>());
        }
        e.setInTransaction(true);
        e.setClassName(className);
        e.setMethodName(methodName);
        cache.set(e);
    }

    public  void addAction(Callable<?> action) {
        Entity e = cache.get();
        e.getActions().add(action);
    }
    
    public  void addMessage(MessageEntity message) {
        Entity e = cache.get();
        e.getMessageEntities().add(message);
    }

    
    
    
   
}