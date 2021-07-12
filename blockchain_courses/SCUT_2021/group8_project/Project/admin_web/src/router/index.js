import Vue from 'vue'
import Router from 'vue-router'
import AppIndex from '../components/home/AppIndex'
import Book from '../components/home/Book'
import User from '../components/home/User'
import Home from '../components/Home'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/index'
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'AppIndex',
          component: AppIndex,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/book',
          name: 'Book',
          component: Book,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/user',
          name: 'User',
          component: User,
          meta: {
            requireAuth: true
          }
        }
      ]
    }
  ]
})
