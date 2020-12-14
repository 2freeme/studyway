//package com.example.demo.module.rest;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import javax.sql.DataSource;
//
//import org.apache.shardingsphere.api.hint.HintManager;
//import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
//import org.apache.shardingsphere.shardingjdbc.spring.datasource.SpringShardingDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.example.demo.common.base.BaseRest;
//import com.example.demo.common.base.Result;
//import com.example.demo.common.utils.SnowflakeIdWorker;
//import com.example.demo.common.utils.UUIDUtil;
//import com.example.demo.module.dao.DictMapper;
//import com.example.demo.module.pojo.Dict;
//import com.example.demo.module.pojo.MediaOrder;
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
//@RequestMapping("/mediadict")
//public class MediaDictRest extends BaseRest {
//	@Autowired
//	DictMapper dictMapper;
//	
//	
//	//http://127.0.0.1:8888/mediadict/save
//	@GetMapping("/save")
//	// ================= 测试公共表 ======================
//	public Result addDict() {
//		Dict dict = new Dict();
//		dict.setUstatus("S");
//		dict.setUvalue("已成功");
//		dictMapper.insert(dict);
//		return addSucResult();
//	}
//
//	@GetMapping("/{dict_id}")
//	public Result<List<MediaOrder>> getDict(@PathVariable String dict_id) {
//		QueryWrapper queryWrapper = new QueryWrapper();
//		queryWrapper.eq("dict_id", dict_id);
//		Dict dict = dictMapper.selectOne(queryWrapper);
//		System.out.println(dict);
//		return addSucResult(dict);
//
//	}
//
//}
