package com.example.redis.controller.dao;

import com.studyway.redis.test.entity.MyOrder;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 19:07
 */
@Repository
public interface OrderMapper {
    @Insert("INSERT INTO Myorder (user_name, item_name ,qty)VALUES(#{userName},#{itemName},#{qty}) ")
    void save(MyOrder myOrder);
}
