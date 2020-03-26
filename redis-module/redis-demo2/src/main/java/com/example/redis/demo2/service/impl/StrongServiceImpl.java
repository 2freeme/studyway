package com.example.redis.demo2.service.impl;

import com.example.redis.demo2.dao.StrongMapper;
import com.studyway.redis.test.entity.Strong;
import com.studyway.redis.test.service.StrongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 19:50
 */
@Service
public class StrongServiceImpl implements StrongService {
    @Autowired
    StrongMapper strongMapper;

    @Override
    public void updateStrong(Strong strong) {
        strongMapper.updateStrong(strong);
    }
}
