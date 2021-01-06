package rocketMq.core;

import org.springframework.stereotype.Component;
import rocketMq.core.bean.TransEntity;
import rocketMq.core.bean.TransMsg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Author：
 * @Description：
 * @Date： 2021/1/4 17:47
 */

@Component
public class TransactionInterceptorHandler {
    public ThreadLocal<TransEntity> cache = new ThreadLocal();

    public TransactionInterceptorHandler() {
    }

    public void clear() {
        this.cache.remove();
    }

    public boolean hasTransaction() {
        TransEntity e = (TransEntity)this.cache.get();
        if (e == null) {
            return false;
        } else {
            return e.getInTransaction() != null && e.getInTransaction();
        }
    }

    public List<Callable<?>> getActions() {
        TransEntity e = (TransEntity)this.cache.get();
        return e == null ? Collections.emptyList() : e.getActions();
    }

    public List<TransMsg> getMessagess() {
        TransEntity e = (TransEntity)this.cache.get();
        return e == null ? Collections.emptyList() : e.getMessageEntities();
    }

    public String getClassName() {
        TransEntity e = (TransEntity)this.cache.get();
        return e == null ? null : e.getClassName();
    }

    public String getMethodName() {
        TransEntity e = (TransEntity)this.cache.get();
        return e == null ? null : e.getMethodName();
    }

    public void signInTransaction(String className, String methodName) {
        TransEntity e = (TransEntity)this.cache.get();
        if (e == null) {
            e = new TransEntity();
            e.setActions(new ArrayList());
            e.setMessageEntities(new ArrayList());
        }

        e.setInTransaction(true);
        e.setClassName(className);
        e.setMethodName(methodName);
        this.cache.set(e);
    }

    public void addAction(Callable<?> action) {
        TransEntity e = (TransEntity)this.cache.get();
        e.getActions().add(action);
    }

    public void addMessage(TransMsg message) {
        TransEntity e = (TransEntity)this.cache.get();
        e.getMessageEntities().add(message);
    }
}
