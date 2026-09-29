import requestUtils from "@/utils/requestUtils";

//查询班级列表数据
export const queryPageApi = (name, begin, end, page, pageSize) =>
  requestUtils.get(`/clazzs?name=${name}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)

// 查询全部员工数据
export const queryAllApi = () => requestUtils.get('/clazzs/all');

//新增
export const addApi = (clazz) => requestUtils.post('/clazzs', clazz);

//根据ID查询
export const queryInfoApi = (id) => requestUtils.get(`/clazzs/${id}`);

//修改
export const updateApi = (clazz) => requestUtils.put('/clazzs', clazz);

//删除
export const deleteApi = (id) => requestUtils.delete(`/clazzs/${id}`);