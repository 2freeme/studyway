package rocketMq.core;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.base.util2.json.JsonUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import rocketMq.core.aspet.ITransMQ;
import rocketMq.core.bean.TransMsg;
import rocketMq.gen.entity.TcTransactionMsg;
import rocketMq.gen.service.TcTransactionMsgGenService;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author：
 * @Description：
 * @Date： 2021/1/4 17:48
 */

@Component
public class TransMQ {
    private static final Logger log = LoggerFactory.getLogger(TransMQ.class);
    public static final String SENDTYPE_SYN = "synSend";
    public static final String SENDTYPE_ASYN = "asynSend";
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_SUC = 1;
    public static final int STATUS_FAIL = 2;
    @Resource
    private TcTransactionMsgGenService tcTransactionMsgGenService;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public TransMQ() {
    }

    @ITransMQ
    public void synSend(String destination, Message<?> message) {
        this.rocketMQTemplate.syncSend(destination, message);
    }

    @ITransMQ
    public void synSend(String destination, Object object) {
        this.rocketMQTemplate.syncSend(destination, object);
    }

    @ITransMQ
    public void asyncSend(String destination, Message<?> message, SendCallback sendCallback) {
        this.rocketMQTemplate.asyncSend(destination, message, sendCallback);
    }

    @ITransMQ
    public void asyncSend(String destination, Message<?> message, SendCallback sendCallback, long timeout, int delayLevel) {
        this.rocketMQTemplate.asyncSend(destination, message, sendCallback, timeout, delayLevel);
    }

    public void sendMessage(final TransMsg entity) {
        if (log.isDebugEnabled()) {
            log.debug("TcTransactionMsg MQ消息发送:{},报文：{}", entity.getMqTopic(), entity.getMessage());
        }

        entity.setLastSendTime(LocalDateTime.now());
        String destination;
        if (StringUtils.isNotBlank(entity.getMqTag())) {
            destination = entity.getMqTopic() + ":" + entity.getMqTag();
        } else {
            destination = entity.getMqTopic();
        }

        Message msg = MessageBuilder.withPayload(entity.getMessage()).setHeader("KEYS", entity.getMessageKey()).build();
        this.rocketMQTemplate.asyncSend(destination, msg, new SendCallback() {
            public void onSuccess(SendResult sendResult) {
                entity.setMsgId(sendResult.getMsgId());
                TransMQ.this.sendSuccess(entity);
            }

            public void onException(Throwable e) {
                TransMQ.this.sendFailed(entity);
            }
        });
        if (log.isDebugEnabled()) {
            log.debug("TcTransactionMsg MQ消息发送成功");
        }

    }

    public void sendSuccess(TransMsg transMsg) {
        transMsg.setStatus(1);
        this.updateMsg(transMsg);
        if (log.isDebugEnabled()) {
            log.debug("TcTransactionMsg MQ消息发送成功:参数=[{}]", transMsg.getMessage());
        }

    }

    public void sendFailed(TransMsg transMsg) {
        transMsg.setStatus(2);
        this.updateMsg(transMsg);
        if (log.isDebugEnabled()) {
            log.debug("TcTransactionMsg MQ消息发送失败:参数=[{}]", transMsg.getMessage());
        }

    }

    public void updateMsg(TransMsg transMsg) {
        TcTransactionMsg tcTransactionMsg = (TcTransactionMsg) JsonUtils.convertObject(transMsg, TcTransactionMsg.class);
        this.updateMsg(tcTransactionMsg);
    }

    public void updateMsg(TcTransactionMsg transactionMsg) {
        this.tcTransactionMsgGenService.updateById(transactionMsg);
        if (log.isDebugEnabled()) {
            log.debug("TcTransactionMsg updateMsg :{},retryTimes:{}", JSONObject.toJSONString(transactionMsg), transactionMsg.getRetryTimes());
        }

    }

    private void deleteMsg(TransMsg transMsg) {
        this.tcTransactionMsgGenService.deleteTcTransactionMsg(transMsg.getId());
        if (log.isDebugEnabled()) {
            log.info("TcTransactionMsg deleteMsg :{}", transMsg.getId());
        }

    }
}
