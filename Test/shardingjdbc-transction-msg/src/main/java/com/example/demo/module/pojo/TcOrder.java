package com.example.demo.module.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
@TableName("tc_order")
@Data
public class TcOrder implements Serializable{
	private static final long serialVersionUID = 1L;
	@TableId
    private long id;
	private String order_no;
	private long user_id;
	private long seller_id;
	private BigDecimal order_total_amount;
	@TableField(exist = false)
	private List<TcOrderDetail> orderdetailList;
	
}

