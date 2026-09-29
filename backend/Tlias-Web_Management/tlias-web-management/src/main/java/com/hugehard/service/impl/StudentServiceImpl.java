package com.hugehard.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hugehard.mapper.EmpMapper;
import com.hugehard.mapper.StudentMapper;
import com.hugehard.pojo.*;
import com.hugehard.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());

        List<Student> rows = studentMapper.list(studentQueryParam);
        log.info("分页查询，结果：{}", rows);
        log.info("分页查询，结果类型：{}", rows.getClass());

        Page<Student> p = (Page<Student>) rows;
        return new PageResult<Student>(p.getTotal(), p.getResult());
    }

    @Override
    public Integer save(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        return studentMapper.insert(student);
    }

    @Override
    public Student getById(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    public Integer update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        return studentMapper.updateById(student);
    }

    @Override
    public Integer delete(List<Integer> ids) {
        return studentMapper.deleteByIds(ids);
    }

    @Override
    public Integer violation(Integer id, Integer score) {
        return studentMapper.violation(id,score);
    }
}