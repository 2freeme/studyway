package com.example.redis.controller.dao;

import com.studyway.redis.test.entity.MyOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * (MyOrder)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-01 15:36:35
 */
public interface MyorderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    MyOrder queryById(Integer orderId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MyOrder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param MyOrder 实例对象
     * @return 对象列表
     */
    List<MyOrder> queryAll(MyOrder MyOrder);

    /**
     * 新增数据
     *
     * @param MyOrder 实例对象
     * @return 影响行数
     */
    int insert(MyOrder MyOrder);

    /**
     * 修改数据
     *
     * @param MyOrder 实例对象
     * @return 影响行数
     */
    int update(MyOrder MyOrder);

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 影响行数
     */
    int deleteById(Integer orderId);

}