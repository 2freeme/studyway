package rocketMq.core.bean;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：
 * @Description：
 * @Date： 2021/1/4 17:44
 */
@Data
public class TransMsg {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long batchNo;
    private String busiClass;
    private String busiMethod;
    private String mqTopic;
    private String mqTag;
    private String message;
    private String messageKey;
    private Integer status;
    private Integer opType;
    private Integer retryTimes;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String sendType;
    private String msgId;
    private LocalDateTime firstSendTime;
    private LocalDateTime lastSendTime;
    private String tag;

}
