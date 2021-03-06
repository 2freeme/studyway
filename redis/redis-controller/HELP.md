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

###布隆过滤器，用来排除缓存的穿透
* 有一定的概率会减少缓存击穿
* 请求可能会被错误的标记  1%
*   穿透了，不存在 cli要进行对key的布隆的标记
*   如果是数据库中有的话，就要针对于redis进行对布隆的标记
*   还有回收策略

技术是易于人的使用，但是底层比较复杂


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
 
注1: redis的持久化是默认rdb的,而且有默认的持久化的时间的,比如几s内改变了一个键
     就持久化一次,也是可以手动的持久化的。待我们重启的时候会将持久化的数据重新的加载
     到redis中
     RDB持久化是指在指定的时间间隔内将内存中的数据集快照写入磁盘，实际操作过程是fork一个子进程，先将数据集写入临时文件，写入成功后，再替换之前的文件，用二进制压缩存储。
     
     RDB持久化保存键空间的所有键值对(包括过期字典中的数据),并以二进制形式保存，符合rdb文件规范，根据不同数据类型会有不同处理。
     
     AOF持久化以日志的形式记录服务器所处理的每一个写、删除操作，查询操作不会记录，以文本的方式记录，可以打开文件看到详细的操作记录。
     
     AOF持久化保存redis服务器所执行的所有写命令来记录数据库状态,在写入之前命令存储在aof_buf缓冲区

注2：对于redis 每次设置的key 和设置的过期时间都是分开的，对于我们已经设置好的过期时间的key，如果在过期时间内
    修改了的话，那我们时间也会被修改为没有过期时间

                
注 ：redis 是有事务的，但是redis的事务不保证原子性  
     接下来需要看的是  可重入锁
     redisson锁的机制和普通的redis锁的区别
     原子性   

注： 在redis大批量的数据的读写的时候，从使用多线程从连接池中取链接分批跑数据更快     

关于spring的事务 
    1、只针对进入当前的类的第一个方法上面加上 @Transactional 才有用  
       原因：在进入的第一个方法的时候，我们调用的这个类会生成一个代理对象。而spring知道这个代理
            所以在连接的时候，会得到数据库的连接，然后开启事务（）。等到这个方法结束的时候 spring才会
            将我们的数据提交。
    2、所以所有的事务其实都是基于数据库的本身的事务，只是spring进行了一个整合。在开始的时候，spring的
       AOP 只是在在遇到注解的时候形成了一个代理。然后调用数据库的事务的开启和提交或者回滚的方法
    3、其实我们在分布式的系统中，我们在一个服务中调用了另外的服务，如果假设两个服务的方法上面都有事务的话
        而且都没有被catch起来。那么我们在B服务中的异常被抛出来，是可以将B服务的事务回滚的，而异常传到了
        A服务的话，没有catch也是会将A服务的事务回滚的。
    4、spring的事务是一个aop的代理。当我们的事务，一个切面，当我们异常在声明式事务结束的时候未被捕获，
      这样就会被感知。 每一个声明式事务都是一个单独的AOP
      所以我们如果在两个方法上面都加上 Transactional的话，而然后有catch的话，其实我们最开始的哪个事务也会回滚了的
      （会在程序执行完提交事务的时候抛出异常）                 
       