package com.hugehard.service;

import com.hugehard.pojo.Clazz;
import com.hugehard.pojo.ClazzQueryParam;
import com.hugehard.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    List<Clazz> pageAll();

    Integer add(Clazz clazz);

    Clazz getById(Integer id);

    Integer update(Clazz clazz);

    Integer deleteById(Integer id);
}
