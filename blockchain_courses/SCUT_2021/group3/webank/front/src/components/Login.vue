<template>
  <body id="poster">
  <div class="login-register">
    <div class="contain">
      <div class="big-box" :class="{active:isLogin}">
        <div class="big-contain" v-if="isLogin">
          <div class="btitle">账户登录</div>
          <div class="bform">
            <input type="email" placeholder="ID" v-model="form.username">
            <span class="errTips" v-if="emailError">* 用户名不存在 *</span>
            <input type="password" placeholder="密码" v-model="form.userpwd">
            <span class="errTips" v-if="emailError">* 密码填写错误 *</span>
          </div>
          <button class="bbutton" @click="login">登录</button>
        </div>
        <div class="big-contain" v-else>
          <div class="btitle">创建账户</div>
          <div class="bform">
            <input type="text" placeholder="用户名" v-model="form.username">
            <input type="password" placeholder="密码" v-model="form.userpwd">
            <input type="text" placeholder="地址" v-model="form.address">
          </div>
          <button class="bbutton" @click="register">注册</button>
        </div>
      </div>
      <div class="small-box" :class="{active:isLogin}">
        <div class="small-contain" v-if="isLogin">
          <div class="stitle">欢迎使用华工云盘!</div>
          <p class="scontent">注册</p>
          <button class="sbutton" @click="changeType">注册</button>
        </div>
        <div class="small-contain" v-else>
          <div class="stitle">欢迎使用华工云盘!</div>
          <p class="scontent">请登录你的账户</p>
          <button class="sbutton" @click="changeType">登录</button>
        </div>
      </div>
    </div>
  </div>
  </body>
</template>

<script>
  export default{
    name:'Login',
    data(){
      return {
        isLogin:false,
        passwordError: false,
        existed: false,
        form:{
          username:'',
          userpwd:'',
          address:'',
        },
        loginForm:{
          username: '',
          password: '',
        }
      }
    },
    methods:{
      changeType() {
        this.isLogin = !this.isLogin
        this.form.username = ''
        this.form.userpwd = ''
        this.form.address = ''
      },
      login() {
        this.loginForm.username = this.form.username
        this.loginForm.password = this.form.userpwd
        const self = this
        if (self.form.username != "" && self.form.userpwd != "") {
          self.$axios({
            method:'post',
            url: '/User/login',
            data: {
              userName: self.form.username,
              pwd: self.form.userpwd
            }
          })
            .then( res => {
              switch(res.data.code){
                case 200:
                  alert("登陆成功！");
                  self.$store.commit('login', self.loginForm)
                  var path = this.$route.query.redirect
                  self.$router.replace({path: '/datacenter'});
                  localStorage.setItem('username',self.loginForm.username)
                  localStorage.setItem('userid',res.data.data._id)
                  console.log(localStorage.getItem('userid'));
                  break;

                case 400:
                  this.passwordError = true;
                  break;
              }
            })
            .catch( err => {
              console.log(err);
            })
        } else{
          alert("填写不能为空！");
        }
      },
      register(){
              this.$axios.post('/User/register', {userName: this.form.username,
                                                             pwd: this.form.userpwd,
                                                             address: this.form.address}).then(resp => {
                  if (resp && resp.data.code === 200) {
                                    alert("注册成功！");
                                    }
                })
        }
     }
     }

</script>

<style scoped="scoped">
  body{
    margin: 0px;
  }
  .login-register{
    width: 100vw;
    height: 100vh;
    box-sizing: border-box;
  }
  .contain{
    width: 60%;
    height: 60%;
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    background-color: #fff;
    border-radius: 20px;
    box-shadow: 0 0 3px #f0f0f0,
    0 0 6px #f0f0f0;
  }
  .big-box{
    width: 70%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 30%;
    transform: translateX(0%);
    transition: all 1s;
  }
  .big-contain{
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .btitle{
    font-size: 1.5em;
    font-weight: bold;
    color: rgb(57,167,176);
  }
  .bform{
    width: 100%;
    height: 60%;
    padding: 2em 0;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
  }
  .bform .errTips{
    display: block;
    width: 50%;
    text-align: left;
    color: red;
    font-size: 0.7em;
    margin-left: 1em;
  }
  .bform input{
    width: 50%;
    height: 30px;
    border: none;
    outline: none;
    border-radius: 10px;
    padding-left: 2em;
    background-color: #f0f0f0;
  }
  .bbutton{
    width: 20%;
    height: 40px;
    border-radius: 24px;
    border: none;
    outline: none;
    background-color: rgb(57,167,176);
    color: #fff;
    font-size: 0.9em;
    cursor: pointer;
  }
  .small-box{
    width: 30%;
    height: 100%;
    background: linear-gradient(135deg,rgb(57,167,176),rgb(56,183,145));
    position: absolute;
    top: 0;
    left: 0;
    transform: translateX(0%);
    transition: all 1s;
    border-top-left-radius: inherit;
    border-bottom-left-radius: inherit;
  }
  .small-contain{
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .stitle{
    font-size: 1.5em;
    font-weight: bold;
    color: #fff;
  }
  .scontent{
    font-size: 0.8em;
    color: #fff;
    text-align: center;
    padding: 2em 4em;
    line-height: 1.7em;
  }
  .sbutton{
    width: 60%;
    height: 40px;
    border-radius: 24px;
    border: 1px solid #fff;
    outline: none;
    background-color: transparent;
    color: #fff;
    font-size: 0.9em;
    cursor: pointer;
  }

  .big-box.active{
    left: 0;
    transition: all 0.5s;
  }
  .small-box.active{
    left: 100%;
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
    border-top-right-radius: inherit;
    border-bottom-right-radius: inherit;
    transform: translateX(-100%);
    transition: all 1s;
  }
</style>
