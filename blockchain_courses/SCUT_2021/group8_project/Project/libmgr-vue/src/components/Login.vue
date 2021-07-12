<template>
<body id="poster">
  <el-form class="login-container" label-position="left"
           label-width="0px">
    <h3 class="login_title">读者登录</h3>
    <el-form-item>
      <el-input type="text" v-model="loginForm.username"
                auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="password" v-model="loginForm.password"
                auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login">登录</el-button>
    </el-form-item>
  </el-form>
</body>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    login () {
      /* this.$axios
        .post('/login', { //  login为后端对应的登录接口
          username: this.loginForm.username,
          password: this.loginForm.password
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) { //  判断登录是否成功
            this.$router.replace({path: '/index'})//  /index为登录成功后，需要跳转到的页面
          }
        })
        .catch(failResponse => {
        }) */
      this.$axios
        .post('/user/login', {
          user_id: this.loginForm.username,
          password: this.loginForm.password
        })
        .then(successResponse => {
          var returncode = successResponse.data.code
          // console.log(returncode)
          if (returncode === '1') { // 登录成功
            sessionStorage.setItem('userName', this.loginForm.username)
            // console.log(sessionStorage.getItem('userName'))
            this.$router.replace({path: '/library'})
          } else if (returncode === '0') { // 用户名不存在
            const h = this.$createElement

            this.$notify({
              title: '',
              message: h('i', { style: 'color: teal' }, '用户名不存在')
            })
          } else { // 密码错误
            const h = this.$createElement

            this.$notify({
              title: '',
              message: h('i', { style: 'color: teal' }, '密码错误')
            })
          }
        })
    }
  }
}
</script>

<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
  #poster {
    background:url("../assets/image/bg_scut_lib.png") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }
  body{
    margin: 0px;
  }
</style>
