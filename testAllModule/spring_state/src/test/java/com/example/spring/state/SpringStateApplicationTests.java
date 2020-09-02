package com.example.spring.state;

import com.example.spring.state.machine.test.MyApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringStateApplicationTests {

	@Test
	public void contextLoads() {
	MyApp myApp = 	new MyApp();

	}

}
