package com.dubbo.dubbo.provrider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// 开启基于注解的dubbo功能（主要是包扫描@DubboComponentScan）
// 也可以在配置文件中使用dubbo.scan.base-package来替代   @EnableDubbo  讲道理如果没有的话就是默认整个项目的加载
@EnableDubbo
@SpringBootApplication
public class DubboProvriderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboProvriderApplication.class, args);
	}

}
