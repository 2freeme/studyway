<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
        xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
        xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.2.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.2.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.2.xsd">

<!-- fc写库 -->
<bean id="writeFcDataSource" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
<property name="driverClassName" value="${database.fc.wirte.driverClassName}" />
<property name="url" value="${database.fc.wirte.url}" />
<property name="username" value="${database.fc.wirte.username}" />
<property name="password" value="${database.fc.wirte.password}" />
<property name="maxActive"><value>150</value></property>
<property name="initialSize"><value>50</value></property>
<property name="maxWait"><value>80000</value></property>        <!-- 超时等待时间  以毫秒为单位 -->
<property name="maxIdle"><value>20</value></property>            <!-- 最大空闲连接 -->
<property name="minIdle"><value>5</value></property>             <!-- 最小空闲连接 -->
<property name="removeAbandoned"><value>true</value></property>  <!-- 是否自动回收超时连接 -->
<property name="removeAbandonedTimeout"><value>30</value></property>  <!-- 超时时间(以秒数为单位) -->
<property name="testWhileIdle"><value>true</value></property>    <!-- 打开检查,用异步线程evict进行检查 -->
<property name="testOnBorrow"><value>true</value></property>
<property name="testOnReturn"><value>false</value></property>
<property name="validationQuery"><value>select 1</value></property>
<property name="numTestsPerEvictionRun"><value>20</value></property>
</bean>

<!-- fc只读库1 -->
<bean id="readFcDataSource1" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
<property name="driverClassName" value="${database.fc.read1.driverClassName}" />
<property name="url" value="${database.fc.read1.url}" />
<property name="username" value="${database.fc.read1.username}" />
<property name="password" value="${database.fc.read1.password}" />
<property name="maxActive"><value>150</value></property>
<property name="initialSize"><value>50</value></property>
<property name="maxWait"><value>80000</value></property>        <!-- 超时等待时间  以毫秒为单位 -->
<property name="maxIdle"><value>20</value></property>            <!-- 最大空闲连接 -->
<property name="minIdle"><value>5</value></property>             <!-- 最小空闲连接 -->
<property name="removeAbandoned"><value>true</value></property>  <!-- 是否自动回收超时连接 -->
<property name="removeAbandonedTimeout"><value>30</value></property>  <!-- 超时时间(以秒数为单位) -->
<property name="testWhileIdle"><value>true</value></property>    <!-- 打开检查,用异步线程evict进行检查 -->
<property name="testOnBorrow"><value>true</value></property>
<property name="testOnReturn"><value>false</value></property>
<property name="validationQuery"><value>select 1</value></property>
<property name="numTestsPerEvictionRun"><value>20</value></property>
</bean>

<!-- fc只读库2 -->
<bean id="readFcDataSource2" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
<property name="driverClassName" value="${database.fc.read2.driverClassName}" />
<property name="url" value="${database.fc.read2.url}" />
<property name="username" value="${database.fc.read2.username}" />
<property name="password" value="${database.fc.read2.password}" />
<property name="maxActive"><value>150</value></property>
<property name="initialSize"><value>50</value></property>
<property name="maxWait"><value>30000</value></property>        <!-- 超时等待时间  以毫秒为单位 -->
<property name="maxIdle"><value>20</value></property>            <!-- 最大空闲连接 -->
<property name="minIdle"><value>5</value></property>             <!-- 最小空闲连接 -->
<property name="removeAbandoned"><value>true</value></property>  <!-- 是否自动回收超时连接 -->
<property name="removeAbandonedTimeout"><value>30</value></property>  <!-- 超时时间(以秒数为单位) -->
<property name="testWhileIdle"><value>true</value></property>    <!-- 打开检查,用异步线程evict进行检查 -->
<property name="testOnBorrow"><value>true</value></property>
<property name="testOnReturn"><value>false</value></property>
<property name="validationQuery"><value>select 1</value></property>
<property name="numTestsPerEvictionRun"><value>20</value></property>
</bean>

<!-- ECM只读库 -->
<bean id="dataSource_ecm" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
<property name="driverClassName" value="${database.ecm.read.driverClassName}"/>
<property name="url" value="${database.ecm.read.url}"/>
<property name="username" value="${database.ecm.read.username}"/>
<property name="password" value="${database.ecm.read.password}"/>
<property name="maxActive"><value>150</value></property>
<property name="initialSize"><value>50</value></property>
<property name="maxWait"><value>80000</value></property>     <!-- 超时等待时间  以毫秒为单位 -->
<property name="maxIdle"><value>5</value></property>            <!-- 最大空闲连接 -->
<property name="minIdle"><value>2</value></property>             <!-- 最小空闲连接 -->
<property name="removeAbandoned"><value>true</value></property>  <!-- 是否自动回收超时连接 -->
<property name="removeAbandonedTimeout"><value>120</value></property>  <!-- 超时时间(以秒数为单位) -->
<property name="logAbandoned"><value>false</value></property>
<property name="testWhileIdle"><value>true</value></property>    <!-- 打开检查,用异步线程evict进行检查 -->
<property name="testOnBorrow"><value>true</value></property>
<property name="testOnReturn"><value>true</value></property>
<property name="validationQuery"><value>select 1</value></property>
<property name="timeBetweenEvictionRunsMillis"><value>1000</value></property>
<property name="numTestsPerEvictionRun"><value>20</value></property>
<property name="defaultAutoCommit"><value>true</value></property>
<property name="defaultReadOnly"><value>true</value></property>
</bean>

<!-- 读写分离配置 -->
<bean id="dsconfDO" class="com.midea.trade.rws.util.DsConfDO">
<property name="writeRestrictTimes" value="0"/><!-- 时间范围内写限制次数 -->
<property name="readRestrictTimes" value="0"/><!-- 时间范围内读限制次数 -->
<property name="timeSliceInMillis" value="0"/><!-- 时间范围不能小于1000ms -->
<property name="maxConcurrentReadRestrict" value="0"/><!-- 最大并发读限制 -->
<property name="maxConcurrentWriteRestrict" value="0"/><!-- 最大并发写限制 -->
</bean>
<bean id="fetcher" class="com.midea.trade.rws.util.SpringDataSourceFetcher"/>

<bean id="groupDataSource" class="com.midea.trade.rws.group.TGroupDataSource">
<constructor-arg name="dsKeyAndWeightCommaArray" value="writeFcDataSource:wq1,readFcDataSource2:rp3"/>
<constructor-arg ref="fetcher"/>
<constructor-arg ref="dsconfDO"/>
</bean>

</beans>
