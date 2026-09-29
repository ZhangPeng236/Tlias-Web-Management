import axios from 'axios'
import { ElMessage } from 'element-plus';
import router from '../router' // .js中获取路由实例，与.vue中获取路由实例方法不同


// 创建axios实例对象
const requestUtils = axios.create({
  baseURL: '/api',
  timeout: 600000 // 单位毫秒
})

// axios的请求 request 拦截器 - 获取localStorage中的token,在请求头中增加token请求头
requestUtils.interceptors.request.use(
  (config) => { // 成功回调
    // 获取token
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    if (loginUser && loginUser.token) {
      // 存在,设置token请求头
      config.headers.token = loginUser.token;
    }
    return config;
  },
  (error) => { // 失败回调
    return Promise.reject(error);
  }
)

// axios的响应 response 拦截器
requestUtils.interceptors.response.use(
  (response) => { // 成功回调
    return response.data
  },
  (error) => { // 失败回调
    if (error.response.status === 401) {
      // 提示信息
      ElMessage.error('登录信息已过期,请重新登录!');
      // 删除localStorage中的登录用户信息
      localStorage.removeItem('loginUser');
      // 跳转到登录页面
      router.push('/login');
    }else{
      ElMessage.error("登录接口访问异常");
    }
    return Promise.reject(error)
  }
)

export default requestUtils