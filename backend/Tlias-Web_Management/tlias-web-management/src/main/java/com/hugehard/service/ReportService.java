package com.hugehard.service;

import com.hugehard.pojo.ClazzOption;
import com.hugehard.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /**
     * 获取员工职位人数
     *
     * @return
     */
    JobOption getEmpJobData();

    /**
     * 统计员工性别人数
     *
     * @return
     */
    List<Map<String, Object>> getEmpGenderData();

    ClazzOption getClazzStudentData();

    List<Map<String, Object>> getStudentDegreeData();
}