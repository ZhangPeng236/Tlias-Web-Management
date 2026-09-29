package com.hugehard.pojo;

import lombok.Data;

@Data
public class OperateLogQueryParam {
    private Integer page = 1; // 页码
    private Integer pageSize = 10; //每页展示记录数
}