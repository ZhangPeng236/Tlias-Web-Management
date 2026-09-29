package com.hugehard.service;

import com.hugehard.pojo.Emp;
import com.hugehard.pojo.EmpQueryParam;
import com.hugehard.pojo.LoginInfo;
import com.hugehard.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    /**
     * 分页查询员工数据
     */
    // 方式一：
    // PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);
    // 方式二：
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    List<Emp> pageAll();

    /**
     * 新增员工
     */
    Integer save(Emp emp);

    /**
     *批量删除员工
     * @param ids
     * @return
     */
    Integer delete(List<Integer> ids);

    /**
     * 根据id查询员工信息
     */
    Emp getInfo(Integer id);

    /**
     * 更新员工信息
     */
    Integer update(Emp emp);

    LoginInfo login(Emp emp);
}