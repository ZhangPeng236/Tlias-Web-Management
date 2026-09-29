package com.hugehard.service;

import com.hugehard.pojo.OperateLogQueryParam;
import com.hugehard.pojo.OperateLog;
import com.hugehard.pojo.PageResult;

public interface OperateLogService {
    PageResult<OperateLog> page(OperateLogQueryParam operateLogQueryParam);
}