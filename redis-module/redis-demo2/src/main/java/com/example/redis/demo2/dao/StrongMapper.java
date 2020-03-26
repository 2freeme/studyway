package com.example.redis.demo2.dao;

import com.studyway.redis.test.entity.Strong;
import org.springframework.stereotype.Repository;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 19:51
 */
@Repository
public interface StrongMapper {
    void updateStrong(Strong strong);
}
