package com.hugehard.service.impl;

import com.hugehard.mapper.EmpMapper;
import com.hugehard.mapper.StudentMapper;
import com.hugehard.pojo.ClazzOption;
import com.hugehard.pojo.JobOption;
import com.hugehard.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        // 1. 调用mapper，获取统计数据
        List<Map<String, Object>> list = empMapper.countEmpJobData();

        // 2.组装结果，并返回
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dateList = list.stream().map(dataMap -> dataMap.get("num")).toList();

        return new JobOption(jobList, dateList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public ClazzOption getClazzStudentData() {
        List<Map<String, Object>> list = studentMapper.countStudentClassData();
        List<Object> clazzList = list.stream().map(dataMap -> dataMap.get("clazz")).toList();
        List<Object> dateList = list.stream().map(dataMap -> dataMap.get("num")).toList();
        return new ClazzOption(clazzList, dateList);
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }
}
