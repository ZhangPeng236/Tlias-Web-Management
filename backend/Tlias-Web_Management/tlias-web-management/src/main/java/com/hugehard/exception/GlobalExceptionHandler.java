package com.hugehard.exception;

import com.hugehard.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author hugehard
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("程序出错啦~:", e);
        return Result.error("出错啦，请联系管理员~");
    }

    @ExceptionHandler
    public Result handlerDuplicateKeyException(DuplicateKeyException e) {
        log.error("程序出错啦~:", e);
        String message = e.getMessage();
        System.out.println("=====begin" + message + "end=====");
        int i = message.indexOf("Duplicate entry");
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.error(arr[2] + "已存在");
    }

    @ExceptionHandler
    public Result handlerClazzContainsStudentException(ClazzContainsStudentException e) {
        log.error("程序出错啦~:", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public Result handlerDeptContainsEmpException(DeptContainsEmpException e) {
        log.error("程序出错啦~:", e);
        return Result.error(e.getMessage());
    }
}