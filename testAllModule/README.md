# studyway
##这里是记录一些生产中的bug
###mybatis
* 我们在使用mybatis的时候，后面需要加上jdbcType 例如在前面的那个如果数据库为null的话，mybatis会报错的。  因为没有对null处理
，即使 又加了 nvl是针对数据库层面的null。 _但是mybatis层面的不写的话，他是不知道这个字段的类型的_。 
>(#{snMinValue, jdbcType=INTEGER}, 0)           (#{snMinValue}, 0) 
