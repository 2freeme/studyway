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
    使用dubbo自带的@Reference 就没有问题，因为涉及到两个服务的关系，消费者需要找到服务者。如果不是dubbo的话就会报错


**dubbo+springboot的使用**
    **首先、在整合的过程中，只需要引入一个依赖即可(详情见pom) 主要注意的是版本的问题**
    **其次、在配置文件的过程中，提供者和服务者的不同就是配置项的多少略微不太一样(见配置文件)**  
    **接着、在配置提供者的时候，注意我们的提供的接口一定要使用dubbo的@service。因为dubbo就是根据这个接口来提供服务的，所以这个接口上面一定要配置。每一个接口都是一个提供者和**
    **然后、在配置消费者的时候，我们的引用的地方一定要是dubbo的@Reference 因为这个就是我们的消费者消费的服务， 每一个都是一个提供者**
    **dubbo的服务断开了之后，我发现zookeeper上面的节点是不会消失的。（在服务正常情况下是文件夹，断开了之后就是一个数据里面包含着地址）**
    