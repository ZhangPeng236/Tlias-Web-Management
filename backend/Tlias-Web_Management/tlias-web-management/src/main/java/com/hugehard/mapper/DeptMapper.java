package com.hugehard.mapper;

import com.hugehard.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     * 查询所有部门
     *
     * @return
     */
    // 方式一：手动结果映射
    //    @Results({
    //            @Result(column = "create_time", property = "createTime"),
    //            @Result(column = "update_time", property = "updateTime")
    //    })

    // 方式二：起别名
    //    @Select("select id, name, create_time createTime, update_time updateTime from dept order by update_time desc")

    // 方式伞：开启驼峰命名法
    @Select("select id, name, create_time, update_time from dept order by update_time desc")
    List<Dept> findAll();

    /**
     * 根据id删除部门
     *
     * @param id
     * @return
     */
    @Delete("delete from dept where id = #{id}")
    Integer deleteById(Integer id);

    /**
     * 添加部门
     *
     * @param dept
     * @return
     */
    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    Integer insert(Dept dept);

    /**
     * 根据id查询部门
     *
     * @param id
     * @return
     */
    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getById(Integer id);

    /**
     * 修改部门
     *
     * @param dept
     * @return
     */
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    Integer update(Dept dept);


    @Select("select count(*) from emp where dept_id = #{id}")
    int getEmpByDeptId(Integer id);
}