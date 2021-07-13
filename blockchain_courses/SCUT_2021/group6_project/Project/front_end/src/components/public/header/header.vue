<template>
  <div>
    <div class="container">
      <!-- 头部 -->
      
      <div  class="head">
        <img class="head_logo" src="@asset/FISCO_BCOS_Logo.svg">
        <el-button class="home"
                   type="text"
                   size="medium"
                   @click="routeToHome">首页</el-button>
        <el-button class="home"
                   type="text"
                   @click="routeToGallery">医疗物资</el-button>
        <el-button class="home"
                   type="text" 
                   @click="routeToData">数据统计</el-button>
        <el-button class="home"
                   type="text" @click="routerToMy">我的</el-button>
 
          <el-button style="font-size: 18px;font-family: AlibabaPuHuiTi-Regular, AlibabaPuHuiTi;color:white" 
          @click="WechatLoginDialogVisible=true"  type="info" round>登录</el-button>
      </div>
      <inner-header v-if="headFlag"></inner-header>
    </div>

    <!-- 用户注册 -->
    <el-dialog class="loginDialog"
               :visible.sync="WechatLoginDialogVisible">
      <!-- logo -->
      <div style="text-align:center;">
      <img class="loginLogo"
           src="@asset/FISCO_BCOS_Logo.svg"> 
      </div>
        
           <!-- 注册框 -->
        <div style="text-align:center;">
          <!-- 邮箱 -->
          <input v-model="registerForm.mail" style="width:210px" class="username" placeholder="请输入邮箱" ref="username">
          <!-- 省份 -->
          <el-select style="width:130px"  v-model="registerForm.province" placeholder="请选择省份" >
            <el-option
              v-for="item in proviceList"
              :key="item.value"
              :label="item.name"
              :value="item.name">
            </el-option>
          </el-select>
        </div>
        <!-- 验证码 -->
        <div style="text-align:center;">
              <input v-model="registerForm.captcha"  class="username" style="width:225px" placeholder="请输入验证码" ref="username">
               <el-button style="font-size:15px" @click="getCode" >获取验证码</el-button>
        </div>
        <!-- 用户名 -->
        <div style="text-align:center;"><input class="username" v-model="registerForm.name"
               placeholder="请输入用户名"
               ref="username"></div>
        <!-- 密码   -->
        <div style="text-align:center;"><input type="password" class="userpassword" v-model="registerForm.password"
               placeholder="请输入密码"
               ref='userpassword'></div>


        <el-button type="primary" class="loginButton" round @click="submit()">注册</el-button>
        <div style="text-align:center">
        <button class="passwordLoginButton" 
                @click="WechatLoginDialogVisible=false,passwordLoginDialogVisible=true ">账号密码登录</button>
        </div>
    </el-dialog>
    <!-- 用户账号密码登录 -->
    <el-dialog class="loginDialog"
               :visible.sync="passwordLoginDialogVisible">
      <div style="text-align:center;">
      <img class="loginLogo"
           src="@asset/FISCO_BCOS_Logo.svg"> 
      </div>
      <div style="text-align:center;margin-top:72px"><input class="username" v-model="loginForm.mail"
               placeholder="请输入邮箱"
               ref="username"></div>
      <div style="text-align:center;"><input type="password" class="userpassword" v-model="loginForm.password"
               placeholder="请输入密码"
               ref='userpassword'></div>
      <el-button type="primary"
                 class="loginButton"
                 round
                 @click="loginClick()">登录</el-button>
      <div style="text-align:center">
        <button class="passwordLoginButton"
                @click="WechatLoginDialogVisible=true ,passwordLoginDialogVisible=false">用户注册</button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'vue-awesome-swiper';
import "swiper/css/swiper.css";
export default {
  components: {
    Swiper,
    SwiperSlide,
  },
  name: 'v-login',
  data() {
    return {
      // 控制头部显示
      headFlag:false,

      loginForm:{
        mail:'',
        password:'',
      },

      proviceList:[],

      registerForm:{
        province:"",
        mail: "",
        name: "",
        captcha:"",
        password:"",
      },
      

      //登录对话框
      WechatLoginDialogVisible: false,
      passwordLoginDialogVisible: false,
    }
  },
  created(){
    this.getProvice();
    this.getHead();
  },
  methods: {
    //判断页头
    getHead(){
      const token = window.sessionStorage.getItem('token')
      if(!token) this.headFlag = false;
      else this.headFlag = true;
      // console.log(this.headFlag);
      
    },
    routerToMy(){
      const token = window.sessionStorage.getItem('token')
      if(!token) return this.$message.error('请先登录')
      this.$router.push({
        name: 'MyHomePage'
      })
      
    },

    //获取所有省份
    getProvice(){
      this.proviceList = require('../../altas/data.json');
      // console.log(this.proviceList);
      let i = 0;
      this.proviceList = this.proviceList.map(r => {
          return {
              name: r.provinceShortName,
              value: i+=1,
          }
      })
    },

    //获取验证码
    async getCode(){
      const res = await this.$http.get('user/registerCaptcha?mail=' + this.registerForm.mail)
      if(res.data.status !== 200) return this.$message.error('获取验证码失败')
      this.$message.success('获取验证码成功')
    },
    //注册提交
    async submit(){
      const res = await this.$http.post('user/register',this.registerForm)
      if(res.data.status !== 200) return this.$message.error('注册失败')
      this.$message.success('注册成功')
      this.WechatLoginDialogVisible=false
      this.passwordLoginDialogVisible=false
    },

    // 登录确认
    async loginClick(){
      const res = await this.$http.post('user/login',this.loginForm)
      // console.log(res);
      if(res.data.status!==200) {
        console.log(res);
        return this.$message.error('登录失败')
      }
      console.log(res);
      this.$message.success('登录成功');
      //关闭对话框
      this.passwordLoginDialogVisible = false
      //将用户token存入会话存储
      window.sessionStorage.setItem("token", res.data.data.token)
      // console.log(res.data.data.user.role);
      //切换页头
      this.headFlag = true;
    },

    routeToGallery: function () {
      // 点击图库按钮
      this.$router.push({
        name: 'Gallery'
      })
    },
    routeToData:function () {
      // 点击图库按钮
      this.$router.push({
        name: 'DataBoard'
      })
    },
    routeToHome(){
      this.$router.push({
        name: 'Home'
      })
    }
  },
  mounted() {
  },
  destroyed() {
    clearTimeout(this.timer)
    clearTimeout(this.timer2)
  }
}
</script>
<style  scoped>
.container{
  display: flex;
  /* 吸顶 */
  overflow: hidden;
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 100;
}
.el-button:hover {
  color: #ffd500;
}
.head {
  width: 100%;
  /* margin-top:30px; */
  background: #252525;
  /* height: 100px; */
  display: flex;
  align-items: center;
  justify-content:space-around;
}
.logo {
  height: 58px;
  width: 112px;
}
.home {
  color:white;
  font-size: 18px;
  font-family: AlibabaPuHuiTi-Regular, AlibabaPuHuiTi;
  /* font-size:25px; */
}
.head_logo{
    height: 100px;
}
/* .pictureStorage {
  margin-right: 86.5px;
} */
/* .about {
  margin-right: 62.5px;
} */
/* .application {
  margin-right: 59px;
} */
/* .api {
  margin-right: 86px;
} */
.login {
  display: flex;
  align-items: center;
  border: none;
  margin: 0 !important;
  padding: 0 !important;
}
.loginJpg {
  height: 45px;
  width: 45px;
}

.long_active_in {
  float: right;
  position: absolute;
  margin-left: 574px;
  margin-top: -100px;
  height: 196px;
  width: 2px;
  /* background: #f4f4f4; */
  transition: all 0.6s linear;
}
@keyframes rotateCamera {
  0% {
    width: 64px;
    height: 64px;
    top: 141px;
    left: 544px;
    transform: rotate(0);
    background: white;
  }
  100% {
    width: 342px;
    height: 342px;
    top: 293px;
    transform: rotate(360);
  }
}
.camera_active_in {
  position: absolute;
  width: 64px;
  height: 64px;
  background: white;
  left: 544px;
  top: 168px;
  /* border: 2px solid #f4f4f4; */
  border-radius: 50%;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.6s linear;
  /* transform: rotate(360deg); */
}
.camera_active_in .cameraJpg {
  width: 30px;
  height: 22px;
  transform: rotate(360deg);
  transition: all 0.6s linear;
}

.cameraJpg {
  width: 195px;
  height: 160px;
  transition: all 0.6s linear;
}
.search {
  position: absolute;
  width: 800px;
  height: 100px;
  border: none;
  box-shadow: 2px 2px 10px 10px rgba(31, 30, 30, 0.05) !important;
  border-radius: 20px;
  top: 800px;
  left: 180px;
  font-size: 18px;
  display: flex;
  align-items: center;
  transition: all linear 0.6s;
}
.search_active_in {
  position: absolute;
  width: 800px;
  height: 100px;
  border: none;
  box-shadow: 2px 2px 10px 10px rgba(31, 30, 30, 0.05) !important;
  border-radius: 20px;
  top: 322px;
  left: 180px;
  font-size: 18px;
  display: flex;
  align-items: center;
  transition: all linear 0.6s;
}
.ic_search {
  width: 36px;
  height: 36px;
  margin-left: 36px;
}
.ic_go {
  width: 36px;
  height: 36px;
}
.icgoButton {
  background-color: white;
  border: 0px;
  outline-style: none;
  cursor: pointer;
}
.input {
  width: 650px;
  border: none;
  outline-style: none;
  font-size: 20px;
}

.loginDialog {
  display: flex;
  align-items: center;
  flex-direction:row-reverse
}
.loginLogo {
  width: 112px;
  height: 58px;
  /* margin-left: 175px; */
  margin-top: 56px;
}
.text {
  font-size: 16px;
  color: rgba(0, 0, 0, 0.4);
  margin: 0 auto;
  text-align: center;
  margin-top: 2px;
}
.qrCode {
  width: 200px;
  height: 200px;
  margin: 0 auto;
  text-align: center;
  margin-top: 24px;
}
.qrCode img {
  width: 100%;
  height: 100%;
}
.passwordLoginButton {
  background-color: #ffffff;
  border: none;
  outline-style: none;
  cursor: pointer;
  color: royalblue;
  margin-top: 25px;
  font-size: 8px;
}
.username {
  padding-left: 20px;
  width: 340px;
  height: 58px;
  background: #ffffff;
  box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, 0.05);
  border-radius: 20px;
  font-size: 18px;
  /* margin-top: 40px; */
  margin-bottom: 20px;
}
.userpassword {
  padding-left: 20px;
  width: 340px;
  height: 58px;
  background: #ffffff;
  box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, 0.05);
  border-radius: 20px;
  font-size: 18px;
}
.loginButton {
  width: 300px;
  height: 56px;
  background: #302fa6;
  font-size: 18px;
  color: #ffffff;
  border-radius: 28px !important;
  display: block;
  margin: 0 auto;
  margin-top: 25px;
  outline-style: none;
}

.el-container {
  position: absolute;
  top: 490px;
  left: 180px;

  width: 800px;
  display: flex;
  flex-direction: column;
}
.hotSearch_rows {
  height: 55px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  transition: all 0.2s linear;
}
.hotSearch_labelsRow {
  margin: 15px 0;
  position: relative;
}
.route_labels {
  text-decoration: none;
  color: rgba(0, 0, 0, 0.4);
}
.route_labels span {
  display: inline-block;
  width: 73px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-left: 20px;
}
.route_labels:hover {
  color: #302fa6;
}
.el-icon-arrow-down {
  position: absolute;
  right: 0;
}
.el-icon-arrow-up {
  position: absolute;
  bottom: 20px;
  right: 0;
}
.newHotSearch_labelsRow {
  margin-left: 53px;
  max-width: 720px;
  position: relative;
  animation: newLabelsRowDisplay 0.2s linear;
}
@keyframes newLabelsRowDisplay {
  0% {
    transform: translateY(-50%);
  }
  100% {
    transform: translateY(0);
  }
}
.newHotSearch_labelsRow span {
  margin-bottom: 15px;
}

.em{
  width:20px;
}
.cd{
  width:20px;
}
</style>


