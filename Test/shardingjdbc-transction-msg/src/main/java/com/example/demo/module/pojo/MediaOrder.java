package com.example.demo.module.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @title: Order
 * @projectName shardingdemo
 * @description: 订单实体
 * @author zhy
 * @date 2020/5/69:26
 */
@TableName("t_media_order")
@Data
public class MediaOrder implements Serializable {

    // 串行版本ID
    private static final long serialVersionUID = 3345453981832744L;

    @TableId
    private String id;

    private String name;
    
    private Long userId;

    private String no;

    private Date createTime;
}
