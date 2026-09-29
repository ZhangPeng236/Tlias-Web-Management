package com.hugehard.mapper;

import com.hugehard.pojo.Emp;
import com.hugehard.pojo.Student;
import com.hugehard.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    public List<Student> list(StudentQueryParam studentQueryParam);

    @Insert("insert into student(name, no, gender, phone, id_card, is_college, address, degree, graduation_date, clazz_id, create_time, update_time) " +
            "values (#{name}, #{no}, #{gender}, #{phone}, #{idCard}, #{isCollege}, #{address}, #{degree}, #{graduationDate}, #{clazzId}, #{createTime},#{updateTime})")
    Integer insert(Student student);

    @Select("select id, name, no, gender, phone, id_card, is_college, address, degree, graduation_date, clazz_id, violation_count, violation_score, create_time, update_time from student where id = #{id}")
    Student getById(Integer id);

    @Update("update student set name = #{name}, no = #{no}, gender = #{gender}, phone = #{phone}, id_card = #{idCard}, is_college = #{isCollege}, address = #{address}, degree = #{degree}, graduation_date = #{graduationDate}, clazz_id = #{clazzId}, update_time = #{updateTime} where id = #{id}")
    Integer updateById(Student student);

    Integer deleteByIds(List<Integer> ids);

    @Update("update student set violation_count = violation_count + 1, violation_score = violation_score + #{score} where id = #{id}")
    Integer violation(Integer id, Integer score);

    @MapKey("clazz")
    List<Map<String, Object>> countStudentClassData();

    @MapKey("/degree")
    List<Map<String, Object>> countStudentDegreeData();
}