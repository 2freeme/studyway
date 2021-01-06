package rocketMq.core.bean;

/**
 * @Author：
 * @Description：
 * @Date： 2021/1/4 17:44
 */

import java.util.List;
import java.util.concurrent.Callable;

public class TransEntity {
    private List<Callable<?>> actions;
    private List<TransMsg> messageEntities;
    private Boolean inTransaction;
    private String className;
    private String methodName;

    public TransEntity() {
    }

    public List<Callable<?>> getActions() {
        return this.actions;
    }

    public List<TransMsg> getMessageEntities() {
        return this.messageEntities;
    }

    public Boolean getInTransaction() {
        return this.inTransaction;
    }

    public String getClassName() {
        return this.className;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setActions(List<Callable<?>> actions) {
        this.actions = actions;
    }

    public void setMessageEntities(List<TransMsg> messageEntities) {
        this.messageEntities = messageEntities;
    }

    public void setInTransaction(Boolean inTransaction) {
        this.inTransaction = inTransaction;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

}
