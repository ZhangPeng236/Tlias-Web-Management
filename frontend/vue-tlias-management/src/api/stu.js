import requestUtils from "@/utils/requestUtils";

//查询学员列表数据
export const queryPageApi = (name, degree, clazzId, page, pageSize) =>
  requestUtils.get(`/students?name=${name}&degree=${degree}&clazzId=${clazzId}&page=${page}&pageSize=${pageSize}`)


//新增
export const addApi = (stu) => requestUtils.post('/students', stu);

//根据ID查询
export const queryInfoApi = (id) => requestUtils.get(`/students/${id}`);

//修改
export const updateApi = (emp) => requestUtils.put('/students', emp);

//删除
export const deleteApi = (ids) => requestUtils.delete(`/students?ids=${ids}`);


//违规
export const vioApi = (id, score) => requestUtils.put(`/students/violation/${id}/${score}`);