package com.hugehard.service;

import com.hugehard.pojo.Dept;

import java.util.List;

public interface DeptService {
    /**
     * 查询所有部门
     * @return
     */
    List<Dept> findAll();

    /**
     * 根据id删除部门
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 添加部门
     * @param dept
     * @return
     */
    Integer add(Dept dept);

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    Dept getById(Integer id);

    /**
     * 修改部门
     * @param dept
     * @return
     */
    Integer update(Dept dept);
}
