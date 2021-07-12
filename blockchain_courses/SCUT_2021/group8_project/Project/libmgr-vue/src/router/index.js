import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import LibraryIndex from '../components/library/LibraryIndex'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/library',
      name: 'Library',
      component: LibraryIndex
    }
  ]
})
