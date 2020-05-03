# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.0.BUILD-SNAPSHOT/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.0.BUILD-SNAPSHOT/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

用作测试redis锁的回滚的性能

这里的redis 已经安装完毕，在本机的电脑，服务是自动启动的。

测试的pom的依赖的问题 依赖还是需要复制，不然的话容易出问题(找不到bean)
在controller的启动类上需要加上feign的注解，不然的话，就代理不了对象，所以就会报找不到bean

还有feign的调用是不支持下划线的 报错 Service id not legal hostname(xx_sss

还要加上@RestController的注解  不然会报错404

还有就是在公用的service中需要加上@RequestBody  不然的话数据会传不过去。

安装的过程中遇到的问题：
    在刚开始的时候是使用压缩包的形式安装的，直接的解压，然后启动server
    这里很奇怪的就是 在命令行种是可以进入到server  但是在用可视化工具
    连接就不行
    
    后面网上下载的一个msi的安装文件，直接安装即可。服务是自启动的。可以直接连接


    目前的话是controller 产生订单服务
            demo1去消减库存
            demo2去扣钱