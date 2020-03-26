package com.example.redis.demo1.dao;

import com.studyway.redis.test.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 19:46
 */
@Repository
public interface AccountMapper {
    @Insert("INSERT account (user_name ,amount)VALUES( #{userName},#{amount})")
    void saveAccountFlow(Account account);
}
