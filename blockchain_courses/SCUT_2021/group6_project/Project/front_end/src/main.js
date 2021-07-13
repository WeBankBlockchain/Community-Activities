import Vue from 'vue'
import App from './App.vue'

//导入全局样式
import './assets/css/global.css'
import './assets/css/customGlobal.css'
import './assets/css/font.css'
import './plugins/element.js'
import './assets/css/reset.css'


import Echarts from 'echarts'
import router from './router/index.js'
import store from './store/index.js'
import Element from 'element-ui'
import albumbox from './components/public/album'
import innerHeader from '@components/public/innerHeader'
import innerFooter from '@components/public/innerFooter'
import header from '@components/public/header'


Vue.use(header)
Vue.use(albumbox)
Vue.use(innerHeader)
Vue.use(innerFooter)

Vue.config.productionTip = false
Vue.use(Element)

import '@asset/js/axios.config.js' //引入axios
Vue.prototype.echarts = Echarts
Vue.use(Echarts)

import less from 'less' //引入less
Vue.use(less)

new Vue({
	router,
	store,
	render: (h) => h(App),
}).$mount('#app')
