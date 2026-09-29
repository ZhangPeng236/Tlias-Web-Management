package com.hugehard.controller;

import com.hugehard.annotation.SaveOperateLogAnno;
import com.hugehard.pojo.*;
import com.hugehard.service.EmpService;
import com.hugehard.service.StudentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @SaveOperateLogAnno
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam) {
        log.info("分页查询，参数：{}", studentQueryParam);
        PageResult<Student> pageResult = studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    @SaveOperateLogAnno
    @PostMapping
    public Result save(@RequestBody Student student) {
        log.info("保存学员数据，参数：{}", student);
        Integer affectedRows = studentService.save(student);
        log.info("保存学员数据受影响的行数：{}", affectedRows);
        return Result.success();
    }

    @SaveOperateLogAnno
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据id查询学员信息：{}", id);
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    @SaveOperateLogAnno
    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("更新学员信息：{}", student);
        Integer affectedRows = studentService.update(student);
        log.info("更新学员受影响的行数：{}", affectedRows);
        return Result.success();
    }

    @SaveOperateLogAnno
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除学员：{}", ids);
        Integer affectedRows = studentService.delete(ids);
        log.info("删除学员受影响的行数：{}", affectedRows);
        return Result.success();
    }


    @SaveOperateLogAnno
    @PutMapping("/violation/{id}/{score}")
    public Result violation(@PathVariable Integer id, @PathVariable Integer score) {
        log.info("更新学员id{}，违纪分数{}:", id, score);
        Integer affectedRows = studentService.violation(id, score);
        log.info("更新学员违规受影响的行数：{}", affectedRows);
        return Result.success();
    }
}