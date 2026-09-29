import requestUtils from '@/utils/requestUtils';

// 登录
export const loginApi = (data) => requestUtils.post('/login', data);