import Vue from 'vue'
import App from './App'
import router from './router'
import api from './http'
import i18n from './i18n'
import store from './store'
import echarts from 'echarts'
import global from '@/utils/global'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.min.css'
import '@/assets/iconfont/iconfont.css'
import '@/assets/css/main.css'

Vue.use(ElementUI)
Vue.use(api)
Vue.prototype.global = global;
Vue.prototype.$echarts = echarts;

new Vue({
  el: '#app',
  i18n,
  router,
  store,
  render: h => h(App)
});
