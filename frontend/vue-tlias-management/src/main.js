import { createApp } from 'vue'
import Echarts from 'vue-echarts'
import App from './App.vue'
import 'echarts'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// import zhCn from 'element-plus/es/locale/lang/zh-cn'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// import './assets/main.css'

const app = createApp(App)

app.use(router)
app.use(ElementPlus, {locale: zhCn})
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.component('v-chart', Echarts)
app.mount('#app')