package com.example.redis.controller.service;

import com.studyway.redis.test.entity.Myorder;

public interface OrderService {
    void submitOrder(Myorder order);
}
