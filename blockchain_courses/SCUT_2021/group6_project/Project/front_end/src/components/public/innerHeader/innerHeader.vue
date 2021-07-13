<template>
  <!-- 导航栏 -->
  <div class="box">
    <div class="bg-left">
      <img src="@asset/FISCO_BCOS_Logo.svg">
      <el-button type="text"
                 style="cursor:pointer"
                 @click="routeToHome">首页</el-button>

      <el-button type="text" @click="routeToList">医疗物资</el-button>
      <el-button type="text" @click="routeToData">数据统计</el-button>
      <el-button type="text" @click="routeToMy">我的</el-button>
    </div>

    <div class="bg-middle"></div>

    <div class="bg-right">
      <el-dropdown @command="handleUserImgLink">
        <span class="el-dropdown-link">
          <img src="@asset/image/img.png">
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="personal">我的主页</el-dropdown-item>
          <el-dropdown-item command="message">我的物资</el-dropdown-item>
          <el-dropdown-item command="home">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return {
    }
  },
  methods: {
    //退出登录
    loginOut(){
      // 清除token
      // console.log(1);
      window.sessionStorage.clear();
      routeToHome()
    },
    routeToHome() {
      this.$router.push({
        name: 'Home'
      })
    },
    routeToMy(){
      this.$router.push({
        name: 'MyHomePage'
      })
    },
    routeToList(){
      this.$router.push({
        name: 'Gallery'
      })
    },
    routeToData(){
      this.$router.push({
        name: 'DataBoard'
      })
    },
    handleUserImgLink(arg) {
      if(arg === 'home'){ 
        window.sessionStorage.clear();
        this.$message.success("退出成功")
        this.routeToHome();
        this.$router.go(0)
        return
      }
      this.$store.commit('changeHomePageCompId', arg)
      this.$router
        .push({
          name: 'MyHomePage',
          params: {
            routerType: 'push',
            compId: arg
          }
          // query: {
          //   compId: arg
          // }
        })
        .catch((err) => {
          console.log('捕获重复路由错误', err)
        })
    },

  }
}
</script>

<style lang="less" scoped>
.box {
  display: flex;
  /* 吸顶 */
  overflow: hidden;
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 100;
}
.bg-right {
  background: #302fa6;
  width: 384px;
  height: 100px;
  margin-top: 0px !important;
  display: flex;
  justify-content: center;
  align-items: center;
}
.bg-left {
  /* flex+align-items居中对齐弹性盒子的各项 */
  background: #252525;
  width: 1152px;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: space-around;
}
.bg-left img {
  width: 112px;
  height: 58px;
  padding-right: 80px;
  padding-left: 80px;
}

.bg-middle {
  background: #ffd500;
  width: 384px;
  height: 100px;
}

.el-button,
.el-dropdown {
  display: inline-block;
  width: 120px;
  height: 100px;
  text-align: center;
  margin: 0px 20px 0px 0px !important;

  color: #ffffff;
  font-size: 18px;
  font-family: AlibabaPuHuiTi-Regular, AlibabaPuHuiTi;
  font-weight: 400;
  line-height: 18px;

  padding: 12px 20px;
  box-sizing: border-box;
}
.el-button:hover {
  color: #ffd500;
}

.el-dropdown {
  cursor: pointer;
  &:hover {
    color: #ffd500;
  }
  &-link {
    display: inline-block;
    width: 100%;
    height: 100%;
    line-height: 76px;
  }
  &-menu {
    top: 75px !important;
  }
  img {
    box-sizing: border-box;
    border-radius: 50%;
    text-align: center;
    width: 45px;
    height: 45px;
    margin-top: 16px;
  }
}
</style>