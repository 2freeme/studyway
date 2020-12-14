//package com.example.demo.module.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.example.demo.common.base.Result;
//import com.example.demo.module.dao.DictMapper;
//import com.example.demo.module.dao.MediaOrderMapper;
//import com.example.demo.module.pojo.Dict;
//import com.example.demo.module.pojo.MediaOrder;
//import com.example.demo.module.service.MediaOrderService;
//
///**
// * @title: OrderServiceImpl
// * @projectName shardingdemo
// * @description: TODO
// * @author zhy
// * @date 2020/5/69:31
// */
//@Service
//public class MediaOrderServiceImpl extends ServiceImpl<MediaOrderMapper, MediaOrder> implements MediaOrderService {
//    @Autowired
//    private MediaOrderMapper mediaOrderMapper;
//	@Autowired
//	private DictMapper dictMapper;
//	@Override
//	public Result<MediaOrder> saveMediaOrder(MediaOrder order) {
//		mediaOrderMapper.insert(order);
//		Dict dict = new Dict();
//		dict.setUstatus("sta");
//		dict.setUvalue("val");
//		dictMapper.insert(dict);
//		System.out.println("MediaOrderServiceImpl.insert,thread-name:"+Thread.currentThread().getId());
//		Result<MediaOrder> rs = new Result<MediaOrder>();
//        rs.setResult(true);
//        rs.setCode("200");
//        rs.setData(order);
//        return rs;
//	}
//}
