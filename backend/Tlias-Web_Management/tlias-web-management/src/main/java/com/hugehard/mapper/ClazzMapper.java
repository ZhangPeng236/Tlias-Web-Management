package com.hugehard.mapper;

import com.hugehard.pojo.Clazz;
import com.hugehard.pojo.ClazzQueryParam;
import com.hugehard.pojo.Dept;
import com.hugehard.pojo.Emp;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Mapper
public interface ClazzMapper {
    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    @Select("select id, name from clazz order by update_time desc")
    List<Clazz> findAll();

    @Insert("insert into clazz(name, room, begin_date, end_date, master_id, subject, create_time, update_time) " +
            "values(#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})")
    Integer insert(Clazz clazz);

    @Select("select id, name, room, begin_date, end_date, master_id, subject, create_time, update_time from clazz where id = #{id}")
    Clazz getById(Integer id);

    @Update("update clazz set name = #{name}, room = #{room}, begin_date = #{beginDate}, end_date = #{endDate}, " +
            "master_id = #{masterId}, subject = #{subject}, update_time = #{updateTime} where id = #{id}")
    Integer update(Clazz clazz);

    @Delete("delete from clazz where id = #{id}")
    Integer deleteById(Integer id);

    @Select("select count(1) from student where clazz_id = #{id}")
    int getStudentByClazzId(Integer id);
}