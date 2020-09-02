package com.example.spring.state.machine.test;

import com.example.spring.state.machine.enums.Events;
import com.example.spring.state.machine.enums.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-9-1 9:02
 */
public class MyApp {

    @Autowired
    StateMachine<States, Events> stateMachine;

    void doSignals() {
        stateMachine.start();
        stateMachine.sendEvent(Events.EVENT1);
        stateMachine.sendEvent(Events.EVENT2);
    }
}
