package rocketMq.gen.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.midea.mcsp.aftersale.atoapi.gen.dto.TcTransactionMsgGenDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.ui.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TcTransactionMsg extends Model<TcTransactionMsg> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分库
     */
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
     * msgKey
     */
    private String messageKey;

    /**
     * 状态：0 待处理 1：待处理 2：待处理
     */
    private Integer status;

    /**
     * asyn,synSend
     */
    private String sendType;

    /**
     * 状态：0：生产 1：消费
     */
    private Integer opType;

    private Integer retryTimes;

    /**
     * 类
     */
    private String msgId;

    private LocalDateTime firstSendTime;

    private LocalDateTime lastSendTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    public static final String ID = "id";

    public static final String SHARDING_COLUMN = "sharding_column";

    public static final String BATCH_NO = "batch_no";

    public static final String BUSI_CLASS = "busi_class";

    public static final String BUSI_METHOD = "busi_method";

    public static final String MQ_TOPIC = "mq_topic";

    public static final String MQ_TAG = "mq_tag";

    public static final String MESSAGE = "message";

    public static final String MESSAGE_KEY = "message_key";

    public static final String STATUS = "status";

    public static final String SEND_TYPE = "send_type";

    public static final String OP_TYPE = "op_type";

    public static final String RETRY_TIMES = "retry_times";

    public static final String MSG_ID = "msg_id";

    public static final String FIRST_SEND_TIME = "first_send_time";

    public static final String LAST_SEND_TIME = "last_send_time";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public TcTransactionMsg accept(TcTransactionMsgGenDTO dto){
        if(dto == null) return null;
        this.setId(dto.getId());
        this.setShardingColumn(dto.getShardingColumn());
        this.setBatchNo(dto.getBatchNo());
        this.setBusiClass(dto.getBusiClass());
        this.setBusiMethod(dto.getBusiMethod());
        this.setMqTopic(dto.getMqTopic());
        this.setMqTag(dto.getMqTag());
        this.setMessage(dto.getMessage());
        this.setMessageKey(dto.getMessageKey());
        this.setStatus(dto.getStatus());
        this.setSendType(dto.getSendType());
        this.setOpType(dto.getOpType());
        this.setRetryTimes(dto.getRetryTimes());
        this.setMsgId(dto.getMsgId());
        this.setFirstSendTime(dto.getFirstSendTime());
        this.setLastSendTime(dto.getLastSendTime());
        this.setCreateTime(dto.getCreateTime());
        this.setUpdateTime(dto.getUpdateTime());
        return this;
    }

    public TcTransactionMsgGenDTO send(){
        TcTransactionMsgGenDTO dto = new TcTransactionMsgGenDTO();
        dto.setId(this.getId());
        dto.setShardingColumn(this.getShardingColumn());
        dto.setBatchNo(this.getBatchNo());
        dto.setBusiClass(this.getBusiClass());
        dto.setBusiMethod(this.getBusiMethod());
        dto.setMqTopic(this.getMqTopic());
        dto.setMqTag(this.getMqTag());
        dto.setMessage(this.getMessage());
        dto.setMessageKey(this.getMessageKey());
        dto.setStatus(this.getStatus());
        dto.setSendType(this.getSendType());
        dto.setOpType(this.getOpType());
        dto.setRetryTimes(this.getRetryTimes());
        dto.setMsgId(this.getMsgId());
        dto.setFirstSendTime(this.getFirstSendTime());
        dto.setLastSendTime(this.getLastSendTime());
        dto.setCreateTime(this.getCreateTime());
        dto.setUpdateTime(this.getUpdateTime());
        return dto;
    }

}
