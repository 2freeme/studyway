package com.example.spring.state.machine.pojo;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-9-1 9:00
 */
@WithStateMachine
 class MyBean {

    @OnTransition(target = "STATE1")
    void toState1() {
    }

    @OnTransition(target = "STATE2")
    void toState2() {
    }
}
