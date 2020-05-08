package com.example.redis.controller.service;

import com.studyway.redis.test.entity.MyOrder;

public interface OrderService {
    void submitOrder(MyOrder order);
}
