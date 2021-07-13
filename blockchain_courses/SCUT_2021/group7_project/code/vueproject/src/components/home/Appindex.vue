<template>
  <body id="poster">
  <el-form class="isHealth-container" label-position="left" label-width="0px">
    <h3 class="isHealth">健康码</h3>
    <vue-qr :correctLevel="3" :autoColor="false" colorDark="#313a90" :bgSrc="bgSrc" :logoSrc="logoSrc" :text="ishealth" :size="200" :margin="0" :logoMargin="5"></vue-qr>
    <el-form-item>
      <el-input type="" v-model="ishealth" auto-complete="off" placeholder="健康哈希值" style="width: 100%"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="" v-model="id" auto-complete="off" placeholder="身份证" style="width: 100%"></el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="isHealth ()">获取健康码</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>

<script>
import VueQr from 'vue-qr'
import axios from 'axios'
export default {
  name: 'Appindex',
  data () {
    return {
      logoSrc: 'https://img1.baidu.com/it/u=3542266921,2233846408&fm=26&fmt=auto&gp=0.jpg',
      // bgSrc: './../assets/bgSrc.png',
      id: '',
      ishealth: ''
    }
  },
  components: {
    VueQr
  },
  methods: {
    isHealth () {
      if (this.id === '') {
        this.showMessageWarning('请输入身份证号!')
      } else {
        this.showMessageInfo('健康码生成中')
        axios({
          method: 'post',
          url: '/user/generateHealthcode',
          data: {
            id: this.id
          }
        })
          .then(successResponce => {
            switch (successResponce.data.ret_code) {
              case 1:
                this.showMessageSuccess('二维码成功生成')
                this.ishealth = successResponce.data.Hashcode
                break
              case 2:
                this.showMessageError('二维码生成失败,用户不存在')
                break
              case 3:
                this.showMessageError('二维码生成失败,未知问题')
            }
          })
          .catch(failResponse => {
            this.showMessageError('二维码生成失败,无效的身份证')
          })
      }
    },
    showMessageInfo (messageText) {
      this.$message({
        showClose: true,
        message: messageText,
        type: 'info'
      })
    },
    showMessageSuccess (messageText) {
      this.$message({
        shoClose: true,
        message: messageText,
        type: 'success'
      })
    },
    showMessageError (messageText) {
      this.$message({
        shoClose: true,
        message: messageText,
        type: 'error'
      })
    },
    showMessageWarning (messageText) {
      this.$message({
        shoClose: true,
        message: messageText,
        type: 'warning'
      })
    }
  }
}
</script>

<style>
.isHealth-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.isHealth_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
