package com.hugehard.mapper;

import com.hugehard.pojo.Clazz;
import com.hugehard.pojo.ClazzQueryParam;
import com.hugehard.pojo.OperateLog;
import com.hugehard.pojo.OperateLogQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    // 插入操作日志数据
    @Insert("insert into operate_log (operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time)" +
            "values (#{operateEmpId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime})")
    public void insert(OperateLog operateLog);

    @Select("select ol.id, operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time,e.name operateEmpName from operate_log ol join emp e on ol.operate_emp_id=e.id")
    List<OperateLog> list(OperateLogQueryParam operateLogQueryParam);
}