import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueBus from 'vue-bus'
// 设置反向代理，前端请求默认发送到 http://localhost:8090
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8090' // 后端基地址,config/index.js中proxyTable也与后端连接有关
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(VueBus)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  components: { App },
  template: '<App/>'
})
