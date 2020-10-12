package com.studyway.rocket.product.service;

import com.studyway.rocket.product.api.Student;
import org.apache.rocketmq.client.producer.SendResult;

public interface ProducerService {

    SendResult sendString(String s);

    SendResult sendStudent(Student student);
}
