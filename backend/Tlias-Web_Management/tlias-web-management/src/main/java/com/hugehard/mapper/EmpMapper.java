package com.hugehard.mapper;

import com.hugehard.pojo.Emp;
import com.hugehard.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 员工信息
 */
@Mapper
public interface EmpMapper {
    // 方式一：原始的分页查询
    ///**
    // * 查询总记录数
    // */
    // @Select("select count(*) from emp")
    //public Long count();

    /// **
    // * 条件分页查询员工信息
    // */
    //@Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id  " +
    //        "order by e.update_time desc limit #{start},#{pageSize}")
    //public List<Emp> list(Integer start, Integer pageSize);

    // 方式二：基于PageHelper的分页查询，定义普通的查询语句
    // @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc")
    // public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);
    List<Emp> list(EmpQueryParam empQueryParam);

    @Select("select id, name from emp order by update_time desc")
    List<Emp> findAll();

    /**
     * 保存员工信息
     */
    @Options(useGeneratedKeys = true, keyProperty = "id") // 获取到生成的主键 -- 主键返回
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    Integer insert(Emp emp);

    /**
     * 根据id批量删除员工基本信息
     */
    Integer deleteByIds(List<Integer> ids);

    /**
     * 根据id查询员工信息以及工作经历信息
     */
    Emp getById(Integer id);

    /**
     * 根据id更新员工的基本信息
     */
    Integer updateById(Emp emp);

    /**
     * 统计员工职位人数
     */
    @MapKey("pos")
    List<Map<String, Object>> countEmpJobData();

    /**
     * 统计员工性别人数
     */
    @MapKey("name")
    List<Map<String, Object>> countEmpGenderData();

    /**
     * 根据用户名和密码查询员工信息
     */
    @Select("select id, username, name from emp where username = #{username} and password = #{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}