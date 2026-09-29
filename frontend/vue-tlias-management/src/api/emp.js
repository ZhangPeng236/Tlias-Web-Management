import requestUtils from "@/utils/requestUtils";

//查询员工列表数据
export const queryPageApi = (name, gender, begin, end, page, pageSize) =>
  requestUtils.get(`/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)

// 查询全部员工数据
export const queryAllApi = () => requestUtils.get('/emps/all');

//新增
export const addApi = (emp) => requestUtils.post('/emps', emp);

//根据ID查询
export const queryInfoApi = (id) => requestUtils.get(`/emps/${id}`);

//修改
export const updateApi = (emp) => requestUtils.put('/emps', emp);

//删除
export const deleteApi = (ids) => requestUtils.delete(`/emps?ids=${ids}`);