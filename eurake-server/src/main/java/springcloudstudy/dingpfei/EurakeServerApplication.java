package springcloudstudy.dingpfei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurakeServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurakeServerApplication.class, args);
	}

}
