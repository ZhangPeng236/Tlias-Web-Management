package com.hugehard.aspect;

import com.hugehard.mapper.OperateLogMapper;
import com.hugehard.pojo.OperateLog;
import com.hugehard.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class OperationLogAspect {
    @Autowired
    private OperateLogMapper operateLogMapper;

    // 环绕通知
    @Around("@annotation(com.hugehard.annotation.SaveOperateLogAnno)")
    public Object saveOperationLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        // 执行方法
        Object result = proceedingJoinPoint.proceed();
        // 当前时间
        long endTime = System.currentTimeMillis();
        // 耗时
        long costTime = endTime - startTime;

        // 构建日志对象
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateEmpId(getCurrentUserId()); // 需要实现 getCurrentUserId 方法
        operateLog.setOperateTime(LocalDateTime.now());
        operateLog.setClassName(proceedingJoinPoint.getTarget().getClass().getName());
        operateLog.setMethodName(proceedingJoinPoint.getSignature().getName());
        operateLog.setMethodParams(Arrays.toString(proceedingJoinPoint.getArgs()));
        operateLog.setReturnValue(result != null ? result.toString() : "void");
        operateLog.setCostTime(costTime);

        // 保存日志
        log.info("记录操作日志：{}", operateLog);

        // 插入日志
        operateLogMapper.insert(operateLog);

        CurrentHolder.removeCurrentEmployeeId();
        log.info("释放线程数据");
        return result;
    }

    // 示例方法，获取当前用户ID
    private Integer getCurrentUserId() {
        return CurrentHolder.getCurrentEmployeeId(); // 示例返回值
    }
}