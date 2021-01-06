package rocketMq.core.aspet;

import com.base.util2.json.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.mybatis.logging.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import rocketMq.common.Result;
import rocketMq.core.TransMQ;
import rocketMq.core.TransactionInterceptorHandler;
import rocketMq.core.bean.TransMsg;
import rocketMq.gen.TcTransactionMsgGenDTO;
import rocketMq.gen.service.TcTransactionMsgGenService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @Author：
 * @Description：
 * @Date： 2021/1/4 17:27
 */

@Aspect
@Component
public class TransAspect {
    private static final org.mybatis.logging.Logger log = LoggerFactory.getLogger(TransAspect.class);
    @Resource
    TransactionInterceptorHandler transactionInterceptorHandler;
    @Resource
    @Lazy
    TcTransactionMsgGenService tcTransactionMsgGenService;
    @Resource
    private TransMQ transMQ;
    TransAspect.TxSyncAdapter txSyncAdapter;

    public TransAspect() {
    }

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void registerTransactionSynchronization(JoinPoint joinPoint) {
        if (this.txSyncAdapter == null) {
            this.txSyncAdapter = new TransAspect.TxSyncAdapter(this.transactionInterceptorHandler, this.tcTransactionMsgGenService, this.transMQ);
        }

        TransactionSynchronizationManager.registerSynchronization(this.txSyncAdapter);
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        this.transactionInterceptorHandler.signInTransaction(className, methodName);
        log.debug("register TransactionSynchronization successfully");
    }

    public static class TxSyncAdapter extends TransactionSynchronizationAdapter {
        TransactionInterceptorHandler transactionInterceptorHandler;
        TcTransactionMsgGenService tcTransactionMsgGenService;
        TransMQ transMQ;

        public void afterCompletion(int status) {
            this.transactionInterceptorHandler.clear();
            TransAspect.log.debug("TTransactionMsg afterCompletion");
        }

        public void beforeCommit(boolean readOnly) {
            TransAspect.log.debug("TTransactionMsg before commit start TransionMsg");
            List<TransMsg> messageEntities = this.transactionInterceptorHandler.getMessagess();
            if (!CollectionUtils.isEmpty(this.transactionInterceptorHandler.getMessagess())) {
                List<TcTransactionMsgGenDTO> collect = (List)messageEntities.stream().map((e) -> {
                    return (TcTransactionMsgGenDTO)JsonUtils.convertObject(e, TcTransactionMsgGenDTO.class);
                }).collect(Collectors.toList());
                Result<Boolean> result = this.tcTransactionMsgGenService.batchInsertTcTransactionMsg(collect);
                if (Boolean.FALSE.equals(result.getData())) {
                    throw new SystemException("插入MQ消息数据失败");
                }
            }

            TransAspect.log.debug("before commit end batch save TransionMsg");
        }

        public void afterCommit() {
            try {
                if (!CollectionUtils.isEmpty(this.transactionInterceptorHandler.getMessagess())) {
                    Iterator var1 = this.transactionInterceptorHandler.getMessagess().iterator();

                    while(var1.hasNext()) {
                        TransMsg transMsg = (TransMsg)var1.next();
                        if (TransAspect.log.isDebugEnabled()) {
                            TransAspect.log.debug("afterCommit: {}", JsonUtils.toJsonString(transMsg));
                        }

                        transMsg.setFirstSendTime(LocalDateTime.now());
                        this.transMQ.sendMessage(transMsg);
                    }
                }
            } catch (Exception var3) {
                TransAspect.log.error("TTransactionMsg afterCommit mq error!", var3);
            }

            TransAspect.log.debug("TTransactionMsg afterCommit");
        }

        public TxSyncAdapter(TransactionInterceptorHandler transactionInterceptorHandler, TcTransactionMsgGenService tcTransactionMsgGenService, TransMQ transMQ) {
            this.transactionInterceptorHandler = transactionInterceptorHandler;
            this.tcTransactionMsgGenService = tcTransactionMsgGenService;
            this.transMQ = transMQ;
        }
    }
}