package com.hugehard.controller;

import com.hugehard.annotation.SaveOperateLogAnno;
import com.hugehard.pojo.*;
import com.hugehard.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/clazzs")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @SaveOperateLogAnno
    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("班级分页查询，参数：{}", clazzQueryParam);
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    @SaveOperateLogAnno
    @GetMapping("/all")
    public Result pageAll() {
        log.info("查询所有班级");
        List<Clazz> pageResult = clazzService.pageAll();
        return Result.success(pageResult);
    }

    @SaveOperateLogAnno
    @PostMapping
    public Result add(@RequestBody Clazz clazz) {
        log.info("添加班级：{}", clazz);
        Integer affectedRows = clazzService.add(clazz);
        log.info("添加班级受影响的行数：{}", affectedRows);
        return Result.success();
    }

    @SaveOperateLogAnno
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据id查询班级信息：{}", id);
        Clazz clazzs = clazzService.getById(id);
        return Result.success(clazzs);
    }

    @SaveOperateLogAnno
    @PutMapping
    public Result update(@RequestBody Clazz clazz) {
        log.info("修改班级信息：{}", clazz);
        Integer affectedRows = clazzService.update(clazz);
        log.info("修改班级信息受影响的行数：{}", affectedRows);
        return Result.success();
    }

    @SaveOperateLogAnno
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("根据id删除班级：{}", id); // 使用log.info的重载方法，传入占位符和参数
        Integer affectedRows = clazzService.deleteById(id);
        log.info("根据id删除班级受影响的行数：{}", affectedRows);
        return Result.success();
    }
}