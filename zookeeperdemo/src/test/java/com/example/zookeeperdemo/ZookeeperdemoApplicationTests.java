package com.example.zookeeperdemo;

import com.studyway.redis.test.entity.Myorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperdemoApplicationTests {
	@Autowired


	@Test
	public void contextLoads() {
		Myorder myOrder = new Myorder();

	}


}
