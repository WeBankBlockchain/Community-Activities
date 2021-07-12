<template>
  <body id="poster">
  <el-form class="uploadUserInfo-container" label-position="left" label-width="0px">
    <h3 class="uploadUserInfo">上传用户信息</h3>
    <el-form-item>
      <el-input type="text" v-model="uploadUserInfoForm.userName" auto-complete="off" placeholder="姓名"></el-input>
    </el-form-item>
    <el-form-item>
       <el-input type="text" v-model="uploadUserInfoForm.ID" auto-complete="off" placeholder="身份证"></el-input>
    </el-form-item>
    <el-form-item>
<!--       <el-input type="text" v-model="uploadUserInfoForm.residence" auto-complete="off" placeholder="居住地"></el-input>-->
      <template >
        <el-cascader
          style="width: 100%"
          size="large"
          :options="myoptions"
          v-model="selectedOptions"
          @change="handleChange">
        </el-cascader>
      </template>
    </el-form-item>
    <el-form-item>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="uploadUserInfo ()">上传用户信息</el-button>
    </el-form-item>
    <el-form-item style="width: 100%">
    <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="routerReplace ()">获取健康码</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>

<script>
import axios from 'axios'
import { regionDataPlus, CodeToText } from 'element-china-area-data'
export default {
  name: 'UploadUserInfo',
  data () {
    return {
      uploadUserInfoForm: {
        userName: '',
        ID: '',
        residence: ''
      },
      myoptions: regionDataPlus,
      selectedOptions: []
    }
  },
  methods: {
    handleChange (value) {
      this.uploadUserInfoForm.residence = CodeToText[this.selectedOptions[0]] + CodeToText[this.selectedOptions[1]] + CodeToText[this.selectedOptions[2]]
    },
    uploadUserInfo () {
      if (this.uploadUserInfoForm.userName === '') {
        this.showMessageWarning('请输入姓名!')
      } else if (this.uploadUserInfoForm.ID === '') {
        this.showMessageWarning('请输入身份证号!')
      } else if (this.uploadUserInfoForm.residence === '') {
        this.showMessageWarning('请输入居住地!')
      } else {
        this.showMessageInfo('用户信息上传中')
        let param = new URLSearchParams()
        param.append('name', this.uploadUserInfoForm.userName)
        param.append('ID', this.uploadUserInfoForm.ID)
        param.append('residence', this.uploadUserInfoForm.residence)
        console.log(param)
        console.log(this.uploadUserInfoForm)
        axios({
          method: 'post',
          url: '/user/upload',
          data: {
            name: this.uploadUserInfoForm.userName,
            id: this.uploadUserInfoForm.ID,
            residence: this.uploadUserInfoForm.residence
          }
        })
          .then(successResponce => {
            switch (successResponce.data.ret_code) {
              case 0:
                this.showMessageSuccess('用户信息上传成功')
                this.$router.replace({path: '/index'})
                break
              case 1:
                this.showMessageSuccess('用户信息更新成功')
                this.$router.replace({path: '/index'})
                break
              case -1:
                this.showMessageError('用户信息上传失败,未知错误')
                break
            }
          })
          .catch(failResponse => {
            this.showMessageError('用户信息上传失败,无效的用户信息')
          })
      }
    },
    routerReplace () {
      this.$router.replace({path: '/index'})
    },
    showMessageInfo (messageText) {
      this.$message({
        showClose: true,
        message: messageText,
        typr: 'info'
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
  body{
    margin: 0px;
  }
  .uploadUserInfo-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .uploadUserInfo_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
</style>
