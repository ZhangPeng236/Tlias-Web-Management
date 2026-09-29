package com.hugehard.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hugehard.exception.ClazzContainsStudentException;
import com.hugehard.mapper.ClazzMapper;
import com.hugehard.pojo.Clazz;
import com.hugehard.pojo.ClazzQueryParam;
import com.hugehard.pojo.PageResult;
import com.hugehard.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());

        List<Clazz> rows = clazzMapper.list(clazzQueryParam);
        log.info("分页查询，结果：{}", rows);
        log.info("分页查询，结果类型：{}", rows.getClass());


        // 3.封装PageResult对象并返回
        Page<Clazz> p = (Page<Clazz>) rows;
        if (!CollectionUtils.isEmpty(rows)) {
            // 遍历集合，为empId赋值
            rows.forEach(clazz -> {
                if (LocalDate.now().isBefore(clazz.getBeginDate())) {
                    clazz.setStatus("未开班");
                } else if (LocalDate.now().isAfter(clazz.getEndDate())) {
                    clazz.setStatus("已结课");
                } else {
                    clazz.setStatus("已开班");
                }
            });
        }
        return new PageResult<Clazz>(p.getTotal(), p.getResult());
    }

    @Override
    public List<Clazz> pageAll() {
        return clazzMapper.findAll();
    }

    @Override
    public Integer add(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        return clazzMapper.insert(clazz);
    }

    @Override
    public Clazz getById(Integer id) {
        return clazzMapper.getById(id);
    }

    @Override
    public Integer update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        return clazzMapper.update(clazz);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Integer deleteById(Integer id) {
        if (clazzMapper.getStudentByClazzId(id) > 0) {
            throw new ClazzContainsStudentException("对不起, 当前班级下有学生, 不能直接删除！");
        }
        return clazzMapper.deleteById(id);
    }
}