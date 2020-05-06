package com.example.redis.demo2.service.impl;

import com.example.redis.demo2.dao.StrongDao;
import com.studyway.redis.test.entity.Strong;
import com.studyway.redis.test.service.StrongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 19:50
 */
@Service
@RestController  //必须的因为是http的请求 所以需要  不然调用的时候会404
public class StrongServiceImpl implements StrongService {
    @Autowired
    StrongDao strongMapper;

    @Override
    @PostMapping("/updateStrong")
    public void updateStrong(Strong strong) {
        System.out.println("com.example.redis.demo2.service.impl.StrongServiceImpl.updateStrong" +strong.toString());
        //strongMapper.updateStrong(strong);
    }
}
