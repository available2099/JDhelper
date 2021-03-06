package com.demo.ai.dao;

import com.demo.ai.entity.JdHelp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * jd_plantBean(JdHelp)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-24 16:53:18
 */
@Mapper
public interface JdHelpDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JdHelp queryById(BigInteger id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<JdHelp> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param jdHelp 实例对象
     * @return 对象列表
     */
    List<JdHelp> queryAll(JdHelp jdHelp);

    /**
     * 新增数据
     *
     * @param jdHelp 实例对象
     * @return 影响行数
     */
    int insert(JdHelp jdHelp);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<JdHelp> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<JdHelp> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<JdHelp> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<JdHelp> entities);

    /**
     * 修改数据
     *
     * @param jdHelp 实例对象
     * @return 影响行数
     */
    int update(JdHelp jdHelp);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(BigInteger id);

}