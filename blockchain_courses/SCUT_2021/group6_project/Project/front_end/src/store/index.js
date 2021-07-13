import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		user: {
			user_id: '',
			alias: '用户1', //用户昵称 用于展示的
			name: '登录名', //用户名登录名 用于登录的
			avatar: require('@asset/image/img.png'),
			password: '123456', //登录密码
			email: '123456789@qq.com',
			phone: '',
			unionid: '',
			role: 'user',
			last_access_at: Date.now(),
			isLogin: true,
		},
		homePageCompId: 'personal',
	},
	mutations: {
		readIndexOfMessage(state, index) {
			state.user.messageList[index].read = true
			// this.messageList[index].read = true
		},
		deleteIndexOfMessage(state, index) {
			// this.messageList.splice(index, 1)
			state.user.messageList.splice(index, 1)
		},
		changeHomePageCompId(state, newString) {
			state.homePageCompId = newString
		},
		changeUserName(state, newString) {
			// 个人信息设置中改变用户登录名
			state.user.name = newString
		},
		changeUserPassword(state, newString) {
			// 个人信息设置中改变用户密码
			state.user.password = newString
		},
		changeUserEmail(state, newString) {
			// 个人信息设置中改变用户邮箱
			state.user.email = newString
		},
		storeUserId(state,Id){
			//存储用户身份
			state.user.Id = Id
		}
	},
	actions: {
		changeUserName({ commit }, newString) {
			//  这里只是假装做了处理 有api后应该操作api
			return new Promise((resolve, reject) => {
				setTimeout(() => {
					commit('changeUserName', newString)
					resolve({ status: 200 })
				}, 500)
			})
		},
		changeUserPassword({ commit }, newString) {
			//  这里只是假装做了处理 有api后应该操作api
			return new Promise((resolve, reject) => {
				setTimeout(() => {
					commit('changeUserPassword', newString)
					resolve({ status: 200 })
				}, 500)
			})
		},
		changeUserEmail({ commit }, newString) {
			//  这里只是假装做了处理 有api后应该操作api
			return new Promise((resolve, reject) => {
				setTimeout(() => {
					commit('changeUserEmail', newString)
					resolve({ status: 200 })
				}, 500)
			})
		},
	},
	modules: {},
})

export default store
