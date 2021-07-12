import Vue from 'vue'
import Router from 'vue-router'
import TransferPoint from '../components/TransferPoint'
import DataCenter from '../components/DataCenter/DataCenter'
import UserInfo from '../components/UserInfo'
import UpLoad from '../components/UpLoad'
import Home from '../components/Home'
import Login from '../components/Login'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
      // home页面并不需要被访问
      redirect: '/login',
      children:[
        {
          path: '/datacenter',
          name: 'DataCenter',
          component: DataCenter,
          meta: {
            requireAuth: false,
          }
        },
        {
          path: '/transferpoint',
          name: 'TransferPoint',
          component: TransferPoint,
          meta: {
            requireAuth: false,
          }
        },
        {
          path: '/userinfo',
          name: 'UserInfo',
          component: UserInfo,
          meta: {
            requireAuth: false
          }
        },
        {
          path: '/upload',
          name: 'UpLoad',
          component: UpLoad,
          meta: {
            requireAuth: false
          }
        }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
    }
  ]
})
