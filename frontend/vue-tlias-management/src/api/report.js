import requestUtils from '@/utils/requestUtils';

// 员工职位统计
export const empJobDataApi = () => requestUtils.get('/report/empJobData');

// 员工性别统计
export const empGenderDataApi = () => requestUtils.get('/report/empGenderData');

// 学员班级统计
export const studentCountDataApi = () => requestUtils.get('/report/studentCountData');

// 学员学历统计
export const studentDegreeDataApi = () => requestUtils.get('/report/studentDegreeData');