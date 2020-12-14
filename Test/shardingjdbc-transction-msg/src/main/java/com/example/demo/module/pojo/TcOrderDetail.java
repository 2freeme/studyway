package com.example.demo.module.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
@TableName("tc_order_detail")
@Data
public class TcOrderDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	@TableId
    private long id;
	private long order_id;
	private long user_id;
	private String product_name;
	private BigDecimal product_price;
	
}

