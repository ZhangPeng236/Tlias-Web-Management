package com.hugehard.controller;

import com.hugehard.annotation.SaveOperateLogAnno;
import com.hugehard.pojo.ClazzOption;
import com.hugehard.pojo.JobOption;
import com.hugehard.pojo.Result;
import com.hugehard.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 获取员工职位人数
     *
     * @return
     */
    @SaveOperateLogAnno
    @GetMapping("/empJobData")
    public Result getEmpJobData() {
        log.info("统计员工职位人数");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    /**
     * 统计员工性别人数
     *
     * @return
     */
    @SaveOperateLogAnno
    @GetMapping("/empGenderData")
    public Result getEmpGenderData() {
        log.info("统计员工性别人数");
        List<Map<String, Object>> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);
    }

    @SaveOperateLogAnno
    @GetMapping("/studentCountData")
    public Result getClassStudentData() {
        log.info("统计班级学员人数");
        ClazzOption clazzOption = reportService.getClazzStudentData();
        return Result.success(clazzOption);
    }

    @SaveOperateLogAnno
    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData() {
        log.info("统计学员学历人数");
        List<Map<String, Object>> degreeList = reportService.getStudentDegreeData();
        return Result.success(degreeList);
    }
}