package rocketMq.gen;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TcTransactionMsgGenDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long shardingColumn;
    private Long batchNo;
    private String busiClass;
    private String busiMethod;
    private String mqTopic;
    private String mqTag;
    private String message;
    private String messageKey;
    private Integer status;
    private String sendType;
    private Integer opType;
    private Integer retryTimes;
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


}
