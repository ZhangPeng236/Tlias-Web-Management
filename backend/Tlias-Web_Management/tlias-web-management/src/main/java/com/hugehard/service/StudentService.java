package com.hugehard.service;


import com.hugehard.pojo.PageResult;
import com.hugehard.pojo.Student;
import com.hugehard.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    Integer save(Student student);

    Student getById(Integer id);

    Integer update(Student student);

    Integer delete(List<Integer> ids);

    Integer violation(Integer id, Integer score);
}