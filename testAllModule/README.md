# studyway
##这里是记录一些生产中的bug
###mybatis
* 我们在使用mybatis的时候，后面需要加上jdbcType 例如在前面的那个如果数据库为null的话，mybatis会报错的。  因为没有对null处理
，即使 又加了 nvl是针对数据库层面的null。 _但是mybatis层面的不写的话，他是不知道这个字段的类型的_。 
>(#{snMinValue, jdbcType=INTEGER}, 0)           (#{snMinValue}, 0) 

###估算线程数
*  线程数=cpu可用核数 / 1-阻塞系数（io密集型接近于1，计算密集型接近于0）
### 提升qps
*  提高并发数
    .开多线程
    .增加连接数  tomcat  redis  mysql
    .服务无状态，横向扩张
    .让服务的能力对等
*  减少响应时间