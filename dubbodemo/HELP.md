# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/maven-plugin/)
    在这里的一个服务的提供者和消费者。
创建过程中的出错点    
    dubbo  Caused by: java.lang.ClassNotFoundException: io.netty.bootstrap.ServerBootstrap
    这里的原因就是我的依赖使用错误，已在pom注释并修改
    
    我在customer中，遇到了@Autowired无法自动注入的问题。启动会失败
    使用@Reference没有问题，是因为会自动的调到spring的容器中，然后找到这个类型的对象
    
    注：**在多个服务器之间的调用的时候必须使用@Refence，因为其是JDK自带的，而@Autowired的却是Spring的，所以其必须在同个服务中**