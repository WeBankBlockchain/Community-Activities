import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        isShowLoading: false, // 全局 loading
        // 左侧菜单栏数据
        menuItems: [
            {
                name: 'home', // 要跳转的路由名称 不是路径
                size: 18, // icon大小
                type: 'md-home', // icon类型
                text: '商城主页', // 文本内容
            },
            {
                name: 'me', // 要跳转的路由名称 不是路径
                size: 18, // icon大小
                type: 'md-person', // icon类型
                text: '我的资产', // 文本内容
            },
            {
                name: 'trans', // 要跳转的路由名称 不是路径
                size: 18, // icon大小
                type: 'logo-yen', // icon类型
                text: '交易列表', // 文本内容
            },
        ],
    },
    mutations: {
        setMenus(state, items) {
            state.menuItems = [...items]
        },
        setLoading(state, isShowLoading) {
            state.isShowLoading = isShowLoading
        },
    },
})

export default store
