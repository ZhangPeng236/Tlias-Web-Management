package com.hugehard.controller;

import com.hugehard.annotation.SaveOperateLogAnno;
import com.hugehard.pojo.Dept;
import com.hugehard.pojo.Result;
import com.hugehard.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 方式二：使用lombok提供的@Slf4j注解，自动创建日志记录器
@Slf4j
@RequestMapping("/depts")
/**
 * 默认bean是单例的 - singleton ： 默认单例的bean是在项目启动时创建的，创建完毕后，会将该bean存入IOC容器
 */
// @Lazy // 延迟初始化 ---> 延迟到第一次使用的时候，再来创建这个bean
// @Scope("prototype") // 非单例的，多例的
@RestController
public class DeptController {
    // public DeptController() {
    //     System.out.println("创建DeptController对象...");
    // }
    // 方式一：自定义日志记录器
    // private static final Logger log = LoggerFactory.getLogger(DeptController.class); // 固定写法

    @Autowired
    private DeptService deptService;

    // 方式一：@RequestMapping(value = "/depts",method = RequestMethod.GET)

    /**
     * 查询部门列表
     *
     * @return
     */
    // 方式二：@GetMapping("/depts")
    @SaveOperateLogAnno
    @GetMapping
    public Result list() {
        // System.out.println("查询全部部门的数据");
        log.info("查询全部部门的数据");
        List<Dept> dept = deptService.findAll();
        return Result.success(dept);
    }

    /**
     * 根据id删除部门
     */
    @SaveOperateLogAnno
    @DeleteMapping
    // 方式一：HttpServletRequest的getParameter()方法，只能接收为String，不推荐
    // public Result delete(HttpServletRequest request) {
    // String idStr = request.getParameter("id");
    // Integer id = Integer.parseInt(idStr);

    // 方式二：通过Spring提供的 @RequestParam注解，将请求参数绑定给方法形参
    // 注意事项：一旦声明了@RequestParam注解，该参数在请求时必须传递，如果不传递会报错。（默认 required 为 true）
    // public Result delete(@RequestParam(value = "id",required = false) Integer deptId) {
    // System.out.println("根据id删除部门：" + deptId);

    // 方式三：如果请求参数名与形参变量名相同，直接定义方法形参即可接受，省略@RequestParam注解
    public Result delete(Integer id) {
        // System.out.println("根据id删除部门：" + id);
        log.info("根据id删除部门：{}", id); // 使用log.info的重载方法，传入占位符和参数
        Integer affectedRows = deptService.deleteById(id);
        // System.out.println("受影响的行数：" + affectedRows);
        log.info("根据id删除部门受影响的行数：{}", affectedRows);
        return Result.success();
    }

    /**
     * 添加部门
     */
    @SaveOperateLogAnno
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        // System.out.println("添加部门：" + dept);
        log.info("添加部门：{}", dept);
        Integer affectedRows = deptService.add(dept);
        // System.out.println("受影响的行数：" + affectedRows);
        log.info("添加部门受影响的行数：{}", affectedRows);
        return Result.success();
    }

    /**
     * 根据id查询部门信息
     */
    @SaveOperateLogAnno
    @GetMapping("/{id}")
    // 方式一：通过Spring提供的 @PathVariable注解，将请求参数绑定给方法形参
    // public Result getInfo(@PathVariable("id") Integer deptId) {
    //    System.out.println("根据id查询部门信息：" + deptId);

    // 方式二：如果请求参数名与形参变量名相同，直接定义方法形参即可接受，省略@PathVariable注解
    public Result getInfo(@PathVariable Integer id) {
        // System.out.println("根据id查询部门信息：" + id);
        log.info("根据id查询部门信息：{}", id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 修改部门信息
     */
    @SaveOperateLogAnno
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        // System.out.println("修改部门信息：" + dept);
        log.info("修改部门信息：{}", dept);
        Integer affectedRows = deptService.update(dept);
        // System.out.println("受影响的行数：" + affectedRows);
        log.info("修改部门信息受影响的行数：{}", affectedRows);
        return Result.success();
    }
}