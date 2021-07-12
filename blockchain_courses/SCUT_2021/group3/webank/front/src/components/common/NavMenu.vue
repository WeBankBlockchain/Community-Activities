<template>
    <el-menu
      :default-active="'/datacenter'"
      router
      mode="horizontal"
      background-color="white"
      text-color="#222"
      active-text-color="red"
      style="min-width: 1300px">
      <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
        {{ item.navItem }}
      </el-menu-item>
      <el-button  style="color: #222;float: right;padding: 20px;" @click="logout()">退出登录</el-button>
      <i class="el-icon-menu" style="float:right;font-size: 45px;color: #222;padding-top: 8px"></i>
      <span style="position: absolute;padding-top: 20px;right: 40%;font-size: 30px;font-weight: bold">基于FISCO BCOS的数据共享平台</span>
    </el-menu>
</template>

<script>
  export default {
    name: 'NavMenu',
    data () {
      return {
        navList: [
          {name: '/datacenter', navItem: '数据大厅'},
          {name: '/upload', navItem: '数据上传'},
          {name: '/transferpoint', navItem: '积分转移'},
          {name: '/userinfo', navItem: '个人中心'}
        ]
      }
    },
    methods:{
      logout(){
        var path = this.$route.query.redirect;
        this.$router.replace({path: '/login'});
        localStorage.removeItem('username')
        localStorage.removeItem('userid')
      }
    }
  }
</script>

<style scoped>
  a{
    text-decoration: none;
  }

  span {
    pointer-events: none;
  }
</style>

