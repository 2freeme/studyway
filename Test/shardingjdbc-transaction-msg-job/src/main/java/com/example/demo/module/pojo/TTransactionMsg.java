package com.example.demo.module.pojo;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author sh
 * @since 2020-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TTransactionMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    private Long shardingColumn;

    /**
     * 批次号
     */
    private Long batchNo;

    /**
     * 类
     */
    private String busiClass;

    /**
     * 方法
     */
    private String busiMethod;

    /**
     * mq->topic
     */
    private String mqTopic;

    /**
     * mq->tag
     */
    private String mqTag;

    /**
     * 消息
     */
    private String message;

    /**
     * 状态：0 待发送 1：发送成功 2：发送失败
     */
    private Integer status;

    private Integer retryTimes;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
