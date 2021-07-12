<template>
    <header class="header">
        <a href="" class="logo">健康码系统</a>
        <el-menu :default-active="activeMenu" background-color="transparent" router class="menu" mode="horizontal" @select="handleSelect">
            <el-menu-item index="uploadUserInfo">普通用户</el-menu-item>
            <el-menu-item index="hospital">医护人员</el-menu-item>
            <el-menu-item index="government">政府人员</el-menu-item>
            <el-menu-item index="market">检验人员</el-menu-item>
        </el-menu>
        <router-view/>
    </header>
</template>

<script>
export default {
  name: 'Header',
  data () {
    return {
      activeMenu: 'uploadUserInfo'
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      switch (key) {
        case 'uploadUserInfo' :
          this.$router.push({path: '/uploadUserInfo'})
          break
        case 'hospital' :
          this.$router.push({path: '/hospital'})
          break
        case 'government' :
          this.$router.push({path: '/government'})
          break
        case 'market':
          this.$router.push({path: '/market'})
          break
      }
      this.activeMenu = key
    }
  }
}
</script>

<style>
    .header {
    background: url("../assets/uploadUserInfo.jpg") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
    /* position: fixed; */
    top: 0;
    /* z-index: 1020; */
    /* box-sizing: border-box; */
    /* padding: 0 20px; */
    width: 100%;
    border-radius: 15px;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    background-clip: padding-box;
    /* height: 55px; */
    /* line-height: 55px; */
    /* background-color: #fff; */
    /* border-bottom: 1px solid #c2cfd6; */
    }
    .el-menu-item.is-active{
      background-color:rgb(158, 167, 173) !important;
    }
    .logo {
      font-size: 30px;
      color: #4f6d79;
      font-weight: bold;
    }
</style>
