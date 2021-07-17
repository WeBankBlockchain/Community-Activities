import Vue from 'vue'
import axios from 'axios'
import ViewUI from 'view-design'
import App from './App'
import store from './store'
import router from './router'
import 'view-design/dist/styles/iview.css'
import './permission'

Vue.config.productionTip = false
Vue.use(ViewUI)
// 设置反向代理，前端请求默认发送到
axios.defaults.baseURL = 'http://127.0.0.1:7022/contract/person'


Vue.prototype.$axios = axios // 全局注册,给Vue函数添加一个原型属性$axios

new Vue({
    el: '#app',
    router,
    store,
    axios,
    render: h => h(App),
})
