package com.example.demo.module.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.base.BaseRest;
import com.example.demo.common.base.Result;
import com.example.demo.common.utils.SnowflakeIdWorker;
import com.example.demo.module.pojo.TcOrder;
import com.example.demo.module.pojo.TcOrderDetail;
import com.example.demo.module.service.TcOrderService;

/**
 * @title: OrderRest
 * @projectName shardingdemo
 * @description: TODO
 * @author zhy
 * @date 2020/5/69:33
 */
@RestController
@RequestMapping("/tcorder")
public class TcOrderRest extends BaseRest {
    @Autowired
    private TcOrderService tcOrderService;
   
    AtomicInteger idGen = new AtomicInteger(1);
    AtomicInteger count = new AtomicInteger(1);

    /**

      * http://127.0.0.1:8888/tcorder/save/
      */
    @GetMapping("/save")
    public Result save(){
    	
    	for(int i=1;i<=300;i++) {
    		int user_id= i;
    		TcOrder order = new TcOrder();
    		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
    		long id = idWorker.nextId();
    		order.setId(idGen.incrementAndGet());
    		order.setUser_id(user_id);
    		order.setOrder_no("order:"+count.incrementAndGet());
    		order.setOrder_total_amount(BigDecimal.valueOf(new Random().nextDouble()));
    		order.setSeller_id(new Random().nextInt(100));
    		List<TcOrderDetail> tcorderdetailList = new ArrayList<TcOrderDetail>();
    		for(int d=0;d<3;d++) {
    			long detailid = idWorker.nextId();
    			TcOrderDetail toDetail = new TcOrderDetail();
    			toDetail.setId(detailid);
    			toDetail.setOrder_id(id);
    			toDetail.setUser_id(user_id);
    			toDetail.setProduct_name("productname:"+d);
    			toDetail.setProduct_price(BigDecimal.valueOf(new Random().nextDouble()));
    			tcorderdetailList.add(toDetail);
    		}
    		order.setOrderdetailList(tcorderdetailList);
    		try {
    			tcOrderService.saveTcOrder(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
  
        return addSucResult();
    }
    
    
    /**

     * http://127.0.0.1:8888/tcorder/savecomplexAlgorithm/
     */
   @GetMapping("/savecomplexAlgorithm/{user_id}")
   public Result savecomplexAlgorithm(@PathVariable Long user_id){
   	
   		TcOrder order = new TcOrder();
   		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
   		order.setId(idGen.incrementAndGet());
   		order.setUser_id(user_id);
   		long datasource = user_id%16;
   		long table = user_id/16%32;
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
   			tcOrderService.saveTcOrder(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
 
       return addSucResult();
   }
   
   
   
   //http://127.0.0.1:8888/tcorder/getByUserID/1234
	 @GetMapping("/getByUserID/{user_id}")
	 public Result<TcOrder> getByUserId(@PathVariable Long user_id){
    	 QueryWrapper<TcOrder> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("user_id", user_id);
         TcOrder tor = tcOrderService.getOne(queryWrapper);
	     return addSucResult(tor);
	 }
	 //http://127.0.0.1:8888/tcorder/getByOrderNo/order:0213
	 @GetMapping("/getByOrderNo/{order_no}")
	 public Result<TcOrder> getByUserId(@PathVariable String order_no){
    	 QueryWrapper<TcOrder> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("order_no", order_no);
         TcOrder tor = tcOrderService.getOne(queryWrapper);
         List<TcOrder> list = tcOrderService.list(queryWrapper);
         System.out.println(tor);
	     return addSucResult(list);
	 }
	 
	 //http://127.0.0.1:8888/tcorder/getById/2
	 @GetMapping("/getById/{id}")
	 public Result<TcOrder> getById(@PathVariable long id){
    	 QueryWrapper<TcOrder> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("id", id);
         TcOrder tor = tcOrderService.getOne(queryWrapper);
         System.out.println(tor);
	     return addSucResult(tor);
	 }
	   
   
     public static void main(String[] args) {
		System.out.println(new Random().nextDouble());
		System.out.println(new Random().nextInt(100));
	}
    
    
    // * http://127.0.0.1:8888/tcorder/getorderList1/0
     @GetMapping("/getorderList1/{userid}")
    public Result getorderList1(@PathVariable long userid) {
    	 Page<Map<String,Object>> r = tcOrderService.selectListPage(1, 10, userid);
    	 System.out.println("getorderList1 ret:"+r);
        return addSucResult(r);
    }
     
     
     // * http://127.0.0.1:8888/tcorder/getorderList2/0
     @GetMapping("/getorderList2/{userid}")
    public Result getorderList3(@PathVariable long userid) {
    	 Page<Map<String,Object>> r = tcOrderService.selectListPage2(1, 10, userid);
    	 System.out.println("selectListPage2 ret:"+r);
        return addSucResult(r);
    }
 
   
     /**

      * http://127.0.0.1:8888/tcorder/savecomplexAlgorithm/
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
