package com.example.redis.demo2.dao;

import com.studyway.redis.test.entity.Strong;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * (Strong)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-01 15:36:50
 */
@Mapper
public interface StrongDao {

    /**
     * 通过ID查询单条数据
     *
     * @param strongId 主键
     * @return 实例对象
     */
    Strong queryById(Integer strongId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Strong> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param strong 实例对象
     * @return 对象列表
     */
    List<Strong> queryAll(Strong strong);

    /**
     * 新增数据
     *
     * @param strong 实例对象
     * @return 影响行数
     */
    int insert(Strong strong);

    /**
     * 修改数据
     *
     * @param strong 实例对象
     * @return 影响行数
     */
    int update(Strong strong);

    /**
     * 通过主键删除数据
     *
     * @param strongId 主键
     * @return 影响行数
     */
    int deleteById(Integer strongId);

}