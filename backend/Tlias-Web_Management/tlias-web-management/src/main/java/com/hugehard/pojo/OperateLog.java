package com.hugehard.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperateLog {
    private Integer id; // ID
    private Integer operateEmpId; // 操纵人ID
    private LocalDateTime operateTime; // 操作时间
    private String className; // 操作类名
    private String methodName; // 操作方法名
    private String methodParams; // 操作方法实参
    private String returnValue; // 操作方法返回值
    private Long costTime; // 操作耗时

    private String operateEmpName; // 操作人姓名
}