import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/home/home.vue'
import store from '../store/index'

// import Search from '../views/Search.vue'
const Search = () => import('../views/Search.vue')
const AlbumDetail = () => import('../views/CataDetail.vue')
const ImageDetail = () => import('../views/TranscationDetail.vue')
const MyHomePage = () => import('../views/MyHomePage.vue')
const Gallery = () => import('../views/MedicalList.vue')
const DataBoard = () => import('../views/DataBoard.vue')
const Admin = () => import('../views/Admin.vue')
Vue.use(VueRouter)
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}

const vueStore = store
const routes = [
	{
		path: '/',
		name: 'Home',
		component: Home,
	},
	{ path: '/search', name: 'Search', component: Search },
	{ path: '/categoryDetail', name: 'AlbumDetail', component: AlbumDetail },
	{ path: '/transcationDetail', name: 'ImageDetail', component: ImageDetail },
	{
		path: '/myHomePage',
		name: 'MyHomePage',
		component: MyHomePage,
	},
	{
		path: '/dataBoard',
		name: 'DataBoard',
		component: DataBoard,
	},
	{
		path: '/medicalList',
		name: 'Gallery',
		component: Gallery,
	},
	{
		path: '/admin',
		name: Admin,
		component: Admin,
	}
]

const router = new VueRouter({
	routes,
})

// router.beforeEach((to, from, next) => {
// 	const store = vueStore
// 	// 获取vuex中的store.state.user.isLogin 若它不存在 则表示还没登录
// 	if (store.state.user && !store.state.user.isLogin && to.path !== '/home') {
// 		// 若未登录状态 则跳转到首页
// 		// next({ path: '/home' })
// 		next()
// 	} else {
// 		// 若已登录 则顺利跳转
// 		next()
// 	}
// })

// 挂载路由守卫
router.beforeEach((to,from,next)=>{
	console.log(from.path,to.path);
	// if(from.path === '/Home' && to.path=== '/Home'){
	// 	return this.$router.go(0) 
	// }
	next()
  //to 将要访问的路径  from 从哪里来 
  //next放行函数  next()表示放行 next('/login')  强制跳转至login页面
//   if(to.path === '/myHomePage'){
// 	  const token = window.sessionStorage.getItem('token')
// 	  if(!token) {
// 		// this.$message.error('请先登录')
// 		return 
// 	  }
//   } 
//   next()
})
export default router
