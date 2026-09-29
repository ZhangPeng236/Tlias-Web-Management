import requestUtils from '@/utils/requestUtils';

// 查询全部部门数据
export const queryAllApi = () => requestUtils.get('/depts');

// 新增
export const addApi = (dept) => requestUtils.post('/depts', dept);

// 根据ID查询
export const queryByIdApi = (id) => requestUtils.get(`/depts/${id}`);

// 修改
export const updateApi = (dept) => requestUtils.put('/depts', dept);

// 删除
export const deleteByIdApi = (id) => requestUtils.delete(`/depts?id=${id}`);