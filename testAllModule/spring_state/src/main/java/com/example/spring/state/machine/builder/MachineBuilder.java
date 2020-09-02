package com.example.spring.state.machine.builder;

import com.example.spring.state.machine.enums.Events;
import com.example.spring.state.machine.enums.States;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.EnumSet;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-9-1 8:52
 */
public class MachineBuilder
{
    public StateMachine<States, Events> buildMachine() throws Exception {
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.STATE1)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withExternal()
                .source(States.STATE1).target(States.STATE2)
                .event(Events.EVENT1)
                .and()
                .withExternal()
                .source(States.STATE2).target(States.STATE1)
                .event(Events.EVENT2);

        return builder.build();
    }


    public void test() throws Exception {
        StateMachine<States, Events> stateMachine = buildMachine();
        stateMachine.start();
        stateMachine.sendEvent(Events.EVENT1);
        stateMachine.sendEvent(Events.EVENT2);
    }
}
