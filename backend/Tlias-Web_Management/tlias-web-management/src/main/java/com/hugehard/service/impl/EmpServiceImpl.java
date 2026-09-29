package com.hugehard.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hugehard.mapper.EmpExprMapper;
import com.hugehard.mapper.EmpMapper;
import com.hugehard.pojo.*;
import com.hugehard.service.EmpLogService;
import com.hugehard.service.EmpService;
import com.hugehard.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;

    @Override
    // 方式一：
    // public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
    // 方式二：
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        // 方式一：原始的分页查询
        // 1.调用mapper接口，查询总记录数
        // Long total = empMapper.count();

        // 2.调用mapper接口，查询结果列表
        // Integer start = (page - 1) * pageSize;
        // List<Emp> rows = empMapper.list(start, pageSize);

        // 3.封装PageResult对象并返回

        // 方式二：基于PageHelper实现的分页查询
        // 1.设置分页参数(PageHelper)
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        // 2.执行查询
        List<Emp> rows = empMapper.list(empQueryParam);
        log.info("分页查询，结果：{}", rows);
        log.info("分页查询，结果类型：{}", rows.getClass());

        // 3.封装PageResult对象并返回
        Page<Emp> p = (Page<Emp>) rows;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }

    @Override
    public List<Emp> pageAll() {
        return empMapper.findAll();
    }


    @Transactional(rollbackFor = {Exception.class}) // 事务管理 - 默认出现运行时异常RuntimeException，才会回滚事务
    @Override
    public Integer save(Emp emp) {
        Integer affectedRows = null;
        try {
            // 1.保存员工基本信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            affectedRows = empMapper.insert(emp);

            // 2.保存员工工作经历信息
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
                // 遍历集合，为empId赋值
                exprList.forEach(empExpr -> {
                    empExpr.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(exprList);
            }
            return affectedRows;
        } finally {
            // 记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工：" + emp);
            empLogService.insertLog(empLog);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Integer delete(List<Integer> ids) {
        Integer affectedRows = null;
        // 1.批量删除员工基本信息
        affectedRows = empMapper.deleteByIds(ids);

        // 2.批量删除员工工作经历信息
        empExprMapper.deleteByEmpIds(ids);
        return affectedRows;
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer update(Emp emp) {
        // 1.根据id修改员工的基本信息
        emp.setUpdateTime(LocalDateTime.now());
        Integer affectedRows = empMapper.updateById(emp);
        // 2.根据id修改员工工作经历信息
        // 2.1 先根据员工id删除掉原有的工作经历
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        // 2.2 再添加这个员工新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            // 遍历集合，为empId赋值
            exprList.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(exprList);
        }
        return affectedRows;
    }

    @Override
    public LoginInfo login(Emp emp) {
        // 1.调用mapper接口，根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);

        // 2.判断：判断是否存在这个员工，如果存在，组装成功登录信息
        if (e != null) {
            log.info("登录成功，工信息：{}", e);
            // 生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            String token = JwtUtils.generateToken(claims);
            return new LoginInfo(e.getId(), e.getUsername(), e.getName(), token);
        } else {
            // 3.不存在，返回null
            return null;
        }
    }
}