//package com.example.demo.module.rest;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.shardingsphere.api.hint.HintManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.common.base.BaseRest;
//import com.example.demo.common.base.Result;
//import com.example.demo.common.utils.SnowflakeIdWorker;
//import com.example.demo.common.utils.UUIDUtil;
//import com.example.demo.module.pojo.MediaOrder;
//import com.example.demo.module.pojo.MyShardingVo;
//import com.example.demo.module.service.MediaOrderService;
//import com.vividsolutions.jts.io.ParseException;
//
///**
// * @title: OrderRest
// * @projectName shardingdemo
// * @description: TODO
// * @author zhy
// * @date 2020/5/69:33
// */
//@RestController
//@RequestMapping("/mediaorder")
//public class MediaOrderRest extends BaseRest {
//    @Autowired
//    private MediaOrderService mediaOrderService;
//    
//  
//    /**
//      * 根据id获取一条
//      * @param id
//      * @throws
//      * @return com.example.demo.common.base.Result<com.example.demo.module.pojo.Order>
//      * @author zhy
//      * @date 2020/5/10 20:33
//      * 
//      * http://127.0.0.1:8888/mediaorder/1/4
//      */
//    @GetMapping("/{id}/{userid}")
//    public Result<List<MediaOrder>> getById(@PathVariable String id,@PathVariable Long userid){
//    	Map<String, Object> map = new HashMap<String, Object>();
//        map.put("id", id);
//        map.put("user_id", userid);
//        List<MediaOrder> mediaOrders =   mediaOrderService.listByMap(map);
//        return addSucResult(mediaOrders);
//        
//        /** 强制走主库逻辑
//    	HintManager.clear();
//	    HintManager hintManager = HintManager.getInstance();
//	    hintManager.setMasterRouteOnly();
//    	Map<String, Object> map = new HashMap<String, Object>();
//        map.put("id", id);
//        map.put("user_id", userid);
//        List<MediaOrder> mediaOrders =   mediaOrderService.listByMap(map);
//        hintManager.close();
//        return addSucResult(mediaOrders);
//        **/
//    }
//    
//	
//    
//    
//  @GetMapping("/{userid}")
//  public Result<List<MediaOrder>> getByList(@PathVariable Long userid){
//  	Map<String, Object> map = new HashMap<String, Object>();
//      map.put("user_id", userid);
//      List<MediaOrder> mediaOrders =   mediaOrderService.listByMap(map);
//      return addSucResult(mediaOrders);
//  }
//  /**
//   * http://127.0.0.1:8888/mediaorder/orderno/4
//   * @param orderno
//   * @return
//   */
//  @GetMapping("/orderno/{orderno}")
//  public Result<List<MediaOrder>> getByList(@PathVariable String orderno){
//	  //HintManager.clear();
//  	  Map<String, Object> map = new HashMap<String, Object>();
//      map.put("no", orderno);
//    HintManager.clear();
//    HintManager hintManager = HintManager.getInstance();
////    hintManager.addDatabaseShardingValue("t_media_order", 1);
////    hintManager.addTableShardingValue("t_media_order" , 3);
//    hintManager.addDatabaseShardingValue("t_media_order", new MyShardingVo("user_id", 7, null));
//    hintManager.addTableShardingValue("t_media_order" , new MyShardingVo("user_id", 7, null));
//    hintManager.addTableShardingValue("t_media_order" , new MyShardingVo("user_id", 6, null));
//    hintManager.addTableShardingValue("t_media_order" , new MyShardingVo("user_id", 8, null));
//      List<MediaOrder> mediaOrders =   mediaOrderService.listByMap(map);
//      hintManager.close();
//      return addSucResult(mediaOrders);
//  }
//
//    /**
//      * 根据停车场id保存一条纪录
//      * @param carParkId
//      * @throws
//      * @return com.example.demo.common.base.Result
//      * @author zhy
//     * @throws ParseException 
//      * @date 2020/5/10 20:33
//      * http://127.0.0.1:8888/order/1
//      * 
//      * http://127.0.0.1:8888/mediaorder/1
//      */
//    @PostMapping("/{userid}")
//    public Result save(@PathVariable long userid) throws ParseException{
//        MediaOrder order = new MediaOrder();
//        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
//        long id = idWorker.nextId();
//        order.setId(UUIDUtil.getUUID());
//        order.setName("name"+idWorker.nextId());
//        
//        String sharding ="DB"+(userid%2 )+"T"+(userid/2%4)+"_";
//        String orderNo = sharding+UUIDUtil.getUUID();
//        System.out.println("orderNo:"+orderNo);
//        order.setNo(orderNo);
//        order.setUserId(userid);
//       // order.setCreateTime(new Date());
//        order.setCreateTime(new Date());
//        
//        HintManager.clear();
//        HintManager hintManager = HintManager.getInstance();
//        hintManager.addDatabaseShardingValue("t_media_order", 1);
//        hintManager.addTableShardingValue("t_media_order" , 3);
//
////        hintManager.addDatabaseShardingValue("t_media_order", new MyShardingVo("user_id", Integer.parseInt(userid+""), null));
////        hintManager.addTableShardingValue("t_media_order" , new MyShardingVo("user_id", Integer.parseInt(userid+""), null));
//        mediaOrderService.save(order);
//        hintManager.close();
//        return addSucResult();
//    }
//    
//    // * http://127.0.0.1:8888/mediaorder/txmsgtest/1
//    @PostMapping("/txmsgtest/{userid}")
//    public Result txmsgtest(@PathVariable long userid) throws ParseException{
//        MediaOrder order = new MediaOrder();
//        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
//        long id = idWorker.nextId();
//        order.setId(UUIDUtil.getUUID());
//        order.setName("name"+idWorker.nextId());
//        String sharding ="DB"+(userid%2 )+"T"+(userid/2%4)+"_";
//        String orderNo = sharding+UUIDUtil.getUUID();
//        System.out.println("orderNo:"+orderNo);
//        order.setNo(orderNo);
//        order.setUserId(userid);
//        order.setCreateTime(new Date());
//        
//        mediaOrderService.saveMediaOrder(order);
//       
//        try {
//			Thread.sleep(4000l);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//        return addSucResult();
//    }
//    
//    /**
//    * http://127.0.0.1:8888/mediaorder/savecomplex/1
//        */
//      @PostMapping("savecomplex/{userid}")
//      public Result saveComplex(@PathVariable long userid) throws ParseException{
//          MediaOrder order = new MediaOrder();
//          SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
//          long id = idWorker.nextId();
//          order.setId(UUIDUtil.getUUID());
//          order.setName("name"+idWorker.nextId());
//          String sharding ="DB"+(userid%2 )+"T"+(userid/2%4)+"_";
//          String orderNo = sharding+UUIDUtil.getUUID();
//          System.out.println("orderNo:"+orderNo);
//          order.setNo(orderNo);
//          order.setUserId(userid);
//          order.setCreateTime(new Date());
//          
//          return addSucResult();
//      }
//      
//   
//
//}
