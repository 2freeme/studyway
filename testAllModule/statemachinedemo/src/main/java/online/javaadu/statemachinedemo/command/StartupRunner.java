package online.javaadu.statemachinedemo.command;

import online.javaadu.statemachinedemo.statemachine.Events;
import online.javaadu.statemachinedemo.statemachine.States;
import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.StateMachine;

import javax.annotation.Resource;

public class StartupRunner implements CommandLineRunner {

    @Resource
    StateMachine<States, Events> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
        stateMachine.sendEvent(Events.ONLINE);
        System.out.println("111111111111111111111");
        stateMachine.sendEvent(Events.PUBLISH);
        stateMachine.sendEvent(Events.ROLLBACK);
    }
}
