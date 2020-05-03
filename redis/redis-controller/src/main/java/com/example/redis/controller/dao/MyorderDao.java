package com.example.redis.controller.dao;

import com.studyway.redis.test.entity.Myorder;
import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * (Myorder)表数据库访问层
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
    Myorder queryById(Integer orderId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Myorder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param myorder 实例对象
     * @return 对象列表
     */
    List<Myorder> queryAll(Myorder myorder);

    /**
     * 新增数据
     *
     * @param myorder 实例对象
     * @return 影响行数
     */
    int insert(Myorder myorder);

    /**
     * 修改数据
     *
     * @param myorder 实例对象
     * @return 影响行数
     */
    int update(Myorder myorder);

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 影响行数
     */
    int deleteById(Integer orderId);

}