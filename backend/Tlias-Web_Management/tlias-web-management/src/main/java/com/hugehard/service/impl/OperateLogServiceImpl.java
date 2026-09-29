package com.hugehard.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hugehard.mapper.EmpMapper;
import com.hugehard.mapper.OperateLogMapper;
import com.hugehard.pojo.Emp;
import com.hugehard.pojo.OperateLog;
import com.hugehard.pojo.OperateLogQueryParam;
import com.hugehard.pojo.PageResult;
import com.hugehard.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public PageResult<OperateLog> page(OperateLogQueryParam operateLogQueryParam) {
        PageHelper.startPage(operateLogQueryParam.getPage(), operateLogQueryParam.getPageSize());

        // 2.执行查询
        List<OperateLog> rows = operateLogMapper.list(operateLogQueryParam);
        log.info("分页查询，结果：{}", rows);
        log.info("分页查询，结果类型：{}", rows.getClass());

        // 3.封装PageResult对象并返回
        Page<OperateLog> p = (Page<OperateLog>) rows;
        return new PageResult<OperateLog>(p.getTotal(), p.getResult());
    }
}
