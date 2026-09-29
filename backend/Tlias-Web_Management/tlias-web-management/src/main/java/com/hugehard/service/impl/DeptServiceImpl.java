package com.hugehard.service.impl;

import com.hugehard.exception.DeptContainsEmpException;
import com.hugehard.mapper.DeptMapper;
import com.hugehard.pojo.Dept;
import com.hugehard.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Integer deleteById(Integer id) {
        if (deptMapper.getEmpByDeptId(id) > 0) {
            throw new DeptContainsEmpException("对不起, 当前部门下有员工, 不能直接删除！");
        }

        return deptMapper.deleteById(id);
    }

    @Override
    public Integer add(Dept dept) {
        // 1.补全基础属性 - createTime,updateTime
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        // 2.调用Mapper接口方法插入数据
        return deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public Integer update(Dept dept) {
        // 1.补全基础属性 - updateTime
        dept.setUpdateTime(LocalDateTime.now());

        // 2.调用Mapper接口方法插入数据
        return deptMapper.update(dept);
    }
}