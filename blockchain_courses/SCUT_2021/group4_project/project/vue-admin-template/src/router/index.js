import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const commonRoutes = [
    {
        path: '/login',
        name: 'login',
        meta: { title: '登录' },
        component: () => import('../components/Login.vue'),
    },
    {
        path: '/404',
        name: '404',
        meta: { title: '404' },
        component: () => import('../components/404.vue'),
    },
    {
        path: '/register',
        name: 'register',
        meta: { title: 'register' },
        component: () => import('../components/Register.vue'),
    },
    { path: '/', redirect: '/home' },
]

// 本地所有的页面 需要配合后台返回的数据生成页面
export const asyncRoutes = {
    home: {
        path: 'home',
        name: 'home',
        meta: { title: '主页' },
        component: () => import('../views/Home.vue'),
    },
    trans: {
        path: 'trans',
        name: 'trans',
        meta: { title: '交易' },
        component: () => import('../views/Trans.vue'),
    },
    me: {
        path: 'me',
        name: 'me',
        meta: { title: '我的' },
        component: () => import('../views/Me.vue'),
    },
}

const createRouter = () => new Router({
    routes: commonRoutes,
})

const router = createRouter()

export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher
}

export default router
