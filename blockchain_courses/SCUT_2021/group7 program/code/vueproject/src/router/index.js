import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import Appindex from '../components/home/Appindex'
import UploadUserInfo from '../components/UploadUserInfo'
import Hospital from '../components/home/Hospital'
import Government from '../components/home/Government'
import test from '../components/home/test'
import Market from '../components/home/Market.vue'
import Header from '../components/Header'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/header',
      name: 'Header',
      component: Header,
      redirect: '/index',
      children: [
        {
          path: '/uploadUserInfo',
          name: 'UploadUserInfo',
          component: UploadUserInfo
        },
        {
          path: '/market',
          name: 'Market',
          component: Market
        },
        {
          path: '/test',
          name: 'test',
          component: test
        },
        {
          path: '/index',
          name: 'Appindex',
          component: Appindex
        },
        {
          path: '/hospital',
          name: 'Hospital',
          component: Hospital
        },
        {
          path: '/government',
          name: 'Government',
          component: Government
        }
      ]
    }
  ]
})
