package com.example.demo.module.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.base.BaseRest;
import com.example.demo.common.base.Result;
import com.example.demo.common.utils.SnowflakeIdWorker;
import com.example.demo.module.pojo.TTransactionMsg;
import com.example.demo.module.pojo.TcOrder;
import com.example.demo.module.pojo.TcOrderDetail;
import com.example.demo.module.service.TTransactionMsgService;
import com.example.demo.module.service.TcOrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sh
 * @since 2020-12-04
 */
@RestController
@RequestMapping("/tTransactionMsg")
public class TTransactionMsgRest extends BaseRest{
    @Autowired
    private TcOrderService tcOrderService;
   
    AtomicInteger idGen = new AtomicInteger(1);
    AtomicInteger count = new AtomicInteger(1);
	@Autowired
	private TTransactionMsgService tTransactionMsgService;
	
	  /**

     * http://127.0.0.1:8888/tTransactionMsg/saveTransMsg/test001
     */
   @GetMapping("/saveTransMsg/{message}")
   public Result savecomplexAlgorithmTransionMsg(@PathVariable String message){
	   TTransactionMsg tTransactionMsg = new TTransactionMsg();
	   tTransactionMsg.setBatchNo(1110l);
	   tTransactionMsg.setBusiClass("TestClass");
	   tTransactionMsg.setBusiMethod("testMethdo");
	   tTransactionMsg.setCreateTime(LocalDateTime.now());
	   tTransactionMsg.setUpdateTime(LocalDateTime.now());
	   tTransactionMsg.setMqTopic("testTopic");
	   tTransactionMsg.setMqTag("testTag");
	   tTransactionMsg.setStatus(0);
	   tTransactionMsg.setRetryTimes(1);
	   tTransactionMsg.setMessage(message);
	   tTransactionMsgService.save(tTransactionMsg);
	   return null;
   }

   
   /**

    * http://127.0.0.1:8888/tTransactionMsg/savecomplexAlgorithmTransionMsg/
    */
  @GetMapping("/savecomplexAlgorithmTransionMsg/{user_id}")
  public Result savecomplexAlgorithmTransionMsg(@PathVariable Long user_id){
  	
  		TcOrder order = new TcOrder();
  		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
  		order.setId(idGen.incrementAndGet());
  		order.setUser_id(user_id);
  		long datasource = user_id%4;
  		long table = user_id/4%32;
  		order.setOrder_no("order:" +String.format("%02d", datasource)+String.format("%02d", table));
  		order.setOrder_total_amount(BigDecimal.valueOf(new Random().nextDouble()));
  		order.setSeller_id(new Random().nextInt(100));
  		List<TcOrderDetail> tcorderdetailList = new ArrayList<TcOrderDetail>();
  		for(int d=0;d<3;d++) {
  			long detailid = idWorker.nextId();
  			TcOrderDetail toDetail = new TcOrderDetail();
  			toDetail.setId(detailid);
  			toDetail.setOrder_id(order.getId());
  			toDetail.setUser_id(user_id);
  			toDetail.setProduct_name("productname:"+d);
  			toDetail.setProduct_price(BigDecimal.valueOf(new Random().nextDouble()));
  			tcorderdetailList.add(toDetail);
  		}
  		order.setOrderdetailList(tcorderdetailList);
  		try {
  			tcOrderService.saveTcOrderTranMsg(order);
			} catch (Exception e) {
				e.printStackTrace();
			}

      return addSucResult();
  }
}
