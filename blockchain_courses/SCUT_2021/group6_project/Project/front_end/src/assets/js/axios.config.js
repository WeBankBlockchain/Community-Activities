// axios 配置
import Vue from 'vue'
import axios from 'axios'
// 进度条
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
// 修改接口请求
// 1.修改baseURL地址
// 2.每次请求携带token
axios.defaults.timeout = 3000

// 设置 axios 请求 baseUrl
// axios.defaults.baseURL = process.env.BASE_API;
//配置请求的根路径
axios.defaults.baseURL = 'http://api.mrfs.zrcode.top/'
axios.defaults.timeout = 3000
axios.interceptors.request.use(config => {
  NProgress.start();
  //在每个请求头加上token
  config.headers.token = window.sessionStorage.getItem('token')
  // console.log(config);
  //在最后必须return config
  return config
})
axios.interceptors.response.use(config => {
  NProgress.done();
  return config
})
Vue.prototype.$http = axios
// axios.interceptors.request可以拦截接口的请求
// 所有使用axios发送的请求，在请求发送出去之前，都会进入到这个方法
// 在这里，为所有的请求添上token

// axios.interceptors.request.use((config) => {
// 	// console.log('gg')
// 	// config.url = '/api' + config.url;
// 	//   if (config.params) {
// 	//     config.params.token = "";
// 	//   } else {
// 	//     config.params = {
// 	//       token: ""
// 	//     };
// 	//   }
// 	// 不要忘记config对象需要return
// 	return config
// })

// // axios.interceptors.response可以拦截接口的响应
// /**
//  * 设置拦截器，interceptors 响应处理，
//  * 所有使用 axios 的请求响应，都会优先回调到拦截器中
//  * 在正确的返回情况下，数据会优先进入第一个回调方法
//  * 在请求错误的情况下，错误会进入第二个回调方法
//  */
// axios.interceptors.response.use(
// 	(response) => {
// 		//  统一处理数据，使组件中的请求只获取到需要的业务数据，
// 		// 而不需要去关注返回的状态码等与业务无关的数据
// 		// console.log('响应拦截器');
// 		return response.data
// 	},
// 	(error) => {
// 		// 处理错误的响应
// 		return Promise.reject(error)
// 	}
// )

// //  绑定到 vue 原型中，组件中 ： this.$http -> axios 。
// Vue.prototype.$http = axios
// // 写法：
// // this.$http.get('/goods')
// // .then(data => {
// //   this.dataSource = data.list
// //   // 数据排序
// //   this.setSortGoodsData()
// //   // 设置布局
// //   this.initLayout()
// // })
