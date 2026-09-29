package com.hugehard.controller;

import com.hugehard.annotation.SaveOperateLogAnno;
import com.hugehard.pojo.Emp;
import com.hugehard.pojo.EmpQueryParam;
import com.hugehard.pojo.PageResult;
import com.hugehard.pojo.Result;
import com.hugehard.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * 员工管理控制器
 */
@RestController
@Slf4j
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 分页查询员工数据
     */
    @SaveOperateLogAnno
    @GetMapping
    // 方式一：
    //    public Result page(@RequestParam(defaultValue = "1") Integer page,
    //                       @RequestParam(defaultValue = "10") Integer pageSize,
    //                       String name, Integer gender,
    //                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
    //                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
    //        log.info("分页查询员工数据，参数：page={}，pageSize={}，name={}，gender={}，begin={}，end={}");
    //        PageResult<Emp> pageResult = empService.page(page, pageSize, name, gender, begin, end);

    // 方式二：
    public Result page(EmpQueryParam empQueryParam) {
        log.info("分页查询，参数：{}", empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    @SaveOperateLogAnno
    @GetMapping("/all")
    public Result pageAll() {
        log.info("查询所有员工");
        List<Emp> pageResult = empService.pageAll();
        return Result.success(pageResult);
    }

    /**
     * 保存员工数据
     */
    @SaveOperateLogAnno
    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("保存员工数据，参数：{}", emp);
        Integer affectedRows = empService.save(emp);
        log.info("保存员工数据受影响的行数：{}", affectedRows);
        return Result.success();
    }

    /**
     * 删除员工 - List
     *
     * @param ids
     * @return
     */
    @SaveOperateLogAnno
    @DeleteMapping
    // 方式一：使用数组接收ids参数
    // public Result delete(Integer[] ids) {
    //     log.info("删除员工：{}", Arrays.toString(ids));
    // 方式二：推荐使用List接收ids参数，@RequestParam注解不能省略
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除员工：{}", ids);
        Integer affectedRows = empService.delete(ids);
        log.info("删除员工受影响的行数：{}", affectedRows);
        return Result.success();
    }

    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     */
    @SaveOperateLogAnno
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据id查询员工信息，id：{}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    /**
     * 更新员工信息
     *
     * @return
     */
    @SaveOperateLogAnno
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("更新员工信息：{}", emp);
        Integer affectedRows = empService.update(emp);
        log.info("更新员工受影响的行数：{}", affectedRows);
        return Result.success();
    }
}