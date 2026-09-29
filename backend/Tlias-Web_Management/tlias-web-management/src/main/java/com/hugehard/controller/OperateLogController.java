package com.hugehard.controller;

import com.hugehard.annotation.SaveOperateLogAnno;
import com.hugehard.pojo.*;
import com.hugehard.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/log")
public class OperateLogController {
    @Autowired
    private OperateLogService operateLogService;

    @SaveOperateLogAnno
    @GetMapping("/page")
    public Result page(OperateLogQueryParam operateLogQueryParam) {
        log.info("日志分页查询，参数：{}", operateLogQueryParam);
        PageResult<OperateLog> pageResult = operateLogService.page(operateLogQueryParam);
        return Result.success(pageResult);
    }
}