<template>
  <div class="page">
    <div class="page-item">
      <img src="@asset/image/img.png"
           alt="">
      <span class="font-regular-18 page-item-subTitle">暂未上传图像，默认使用微信头像</span>
    </div>
    <div class="line"></div>

    <div class="page-item">
      <div class="page-item-title">
        <p class="font-medium-24">设置用户名</p>
        <p class="font-regular-18">设置用户名可用于账号密码登录</p>
      </div>
      <el-button class="page-item-button"
                 type="primary"
                 @click="clickSetUsername">设置</el-button>
    </div>

    <div class="page-item">
      <div class="page-item-title">
        <p class="font-medium-24">设置密码</p>
        <p class="font-regular-18">设置密码可用于账号密码登录</p>
      </div>
      <el-button class="page-item-button"
                 type="primary"
                 @click="clickSetPassword">设置</el-button>
    </div>

    <div class="page-item">
      <div class="page-item-title">
        <p class="font-medium-24">绑定邮箱</p>
        <p class="font-regular-18">设置邮箱可用于账号密码登录</p>
      </div>
      <el-button class="page-item-button"
                 type="primary"
                 @click="clickSetMail">设置</el-button>
    </div>

    <el-dialog :visible.sync="showSetUsername"
               center
               :append-to-body='true'
               :lock-scroll="false"
               width="800px"
               class='set_Dialog'>
      <!-- <slide-dialog :imgList="imgList"></slide-dialog> -->
      <div class="dialog-container">
        <div class="font-medium-24"
             style="color: #000000;">修改用户名</div>
        <el-form :model="UsernameForm"
                 :rules="rules"
                 ref="UsernameForm"
                 class="dialog-container-form">
          <el-form-item prop="oldName">
            <el-input v-model="UsernameForm.oldName"
                      readonly></el-input>
          </el-form-item>
          <el-form-item prop="newName">
            <el-input v-model="UsernameForm.newName"
                      placeholder="输入新用户名"></el-input>
          </el-form-item>
          <el-form-item class='dialog-container-form-submit'>
            <el-button class="pri-btn"
                       type="primary"
                       ref="btnSubmitForm"
                       @click="submitForm('UsernameForm')">提交</el-button>

          </el-form-item>

        </el-form>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="showSetPassword"
               center
               :append-to-body='true'
               :lock-scroll="false"
               width="800px"
               class='set_Dialog'>
      <!-- <slide-dialog :imgList="imgList"></slide-dialog> -->
      <div class="dialog-container">
        <div class="font-medium-24"
             style="color: #000000;">修改密码</div>
        <el-form :model="PasswordForm"
                 :rules="rules"
                 ref="PasswordForm"
                 class="dialog-container-form">
          <el-form-item prop="oldPassword">
            <el-input v-model="PasswordForm.oldPassword"
                      placeholder="输入旧密码"
                      type="password"></el-input>
          </el-form-item>
          <el-form-item prop="newPassword">
            <el-input v-model="PasswordForm.newPassword"
                      placeholder="输入新密码"
                      type="password"></el-input>
          </el-form-item>
          <el-form-item prop="newPasswordAgain">
            <el-input v-model="PasswordForm.newPasswordAgain"
                      placeholder="再次输入新密码"
                      type="password"></el-input>
          </el-form-item>
          <el-form-item class='dialog-container-form-submit'>
            <el-button class="pri-btn"
                       type="primary"
                       ref="btnSubmitForm"
                       @click="submitForm('PasswordForm')">提交</el-button>

          </el-form-item>
        </el-form>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="showSetMail"
               center
               :append-to-body='true'
               :lock-scroll="false"
               width="800px"
               class='set_Dialog'>
      <div class="dialog-container">
        <div class="font-medium-24"
             style="color: #000000;">修改邮箱</div>
        <el-form :model="MailForm"
                 :rules="rules"
                 ref="MailForm"
                 class="dialog-container-form">
          <el-form-item prop="oldMail">
            <el-input v-model="MailForm.oldMail"
                      readonly
                      placeholder="输入旧邮箱"></el-input>
          </el-form-item>
          <el-form-item prop="newMail">
            <el-input v-model="MailForm.newMail"
                      placeholder="输入新邮箱"></el-input>
          </el-form-item>
          <el-form-item class="flex-item"
                        prop="authCode">
            <el-input v-model="MailForm.authCode"
                      placeholder="输入验证码"></el-input>
            <el-button class='verif-btn'
                       type="primary">获取验证码</el-button>
          </el-form-item>

          <el-form-item class='dialog-container-form-submit'>
            <el-button class="pri-btn"
                       type="primary"
                       ref="btnSubmitForm"
                       @click="submitForm('MailForm')">提交</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    var validateOldPass = (rule, value, callback) => {
      if (value != this.userMsg.password) {
        callback(new Error('输入密码非原密码，请重新输入！'))
      } else {
        callback()
      }
    }

    var validateNewPassAgain = (rule, value, callback) => {
      if (value != this.PasswordForm.newPassword) {
        callback(new Error('两次输入密码不一致,请重新输入！'))
      } else {
        callback()
      }
    }

    var validateEmailFormat = (rule, value, callback) => {
      const mailReg = /^([A-Za-z0-9_\-\.]+)@([A-Za-z0-9_\-\.]+)\.([A-Za-z]{2,6})$/g
      if (mailReg.test(value)) {
        callback()
      } else {
        callback(new Error('请输入正确的邮箱格式'))
      }
    }
    return {
      showSetUsername: false,
      showSetPassword: false,
      showSetMail: false,
      //   userMsg: null,
      rules: {
        oldName: [{}],
        newName: [
          { required: true, message: '请输入新用户名', trigger: 'blur' },
          { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        oldPassword: [
          { required: true, message: '请输入旧密码', trigger: 'blur' },
          { validator: validateOldPass, trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' }
          //   { validator: validateNewPass, trigger: 'blur' }
        ],
        newPasswordAgain: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          { validator: validateNewPassAgain, trigger: 'blur' }
        ],
        oldMail: [{}],
        newMail: [
          { required: true, message: '请输入新邮箱账号', trigger: 'blur' },
          { validator: validateEmailFormat, trigger: 'blur' }
        ],
        authCode: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
      },
      UsernameForm: {
        oldName: '',
        newName: ''
      },
      PasswordForm: {
        oldPassword: '',
        newPassword: '',
        newPasswordAgain: ''
      },
      MailForm: {
        oldMail: '',
        newMail: '',
        authCode: ''
      }
    }
  },
  computed: {
    userMsg() {
      return this.$store.state.user
    }
  },
  created() {
    // this.userMsg = this.$store.state.user
    this.UsernameForm.oldName = this.userMsg.name
    this.MailForm.oldMail = this.userMsg.email
  },
  methods: {
    clickSetUsername() {
      this.showSetUsername = true
    },
    clickSetPassword() {
      this.showSetPassword = true
    },
    clickSetMail() {
      this.showSetMail = true
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm('确认修改', {
            type: 'warning'
          })
            .then(() => {
              let dispatchName = ''
              let newValue = ''
              if (formName === 'UsernameForm') {
                dispatchName = 'changeUserName'
                newValue = this.UsernameForm.newName
              } else if (formName === 'PasswordForm') {
                dispatchName = 'changeUserPassword'
                newValue = this.PasswordForm.newPassword
              } else if (formName === 'MailForm') {
                dispatchName = 'changeUserEmail'
                newValue = this.MailForm.newMail
              }
              //   let newName = this.UsernameForm.newName
              this.$store
                .dispatch(dispatchName, newValue)
                .then((res) => {
                  if (res.status == 200) {
                    this.$message({
                      type: 'info',
                      message: '修改成功'
                    })
                    this.UsernameForm.oldName = this.userMsg.name
                    this.MailForm.oldMail = this.userMsg.email
                  }
                })
                .catch(() => {
                  this.$message({
                    type: 'info',
                    message: '修改操作出现问题，请重试'
                  })
                })
              //   this.admin.password = this.changePwd.passNew //赋值新密码
              //   let params = this.$qs.stringify({
              //     admin: this.admin
              //   })
              //   this.$http({
              //     url: '/"updateAdmin',
              //     data: params,
              //     method: 'post'
              //   })
            })
            .catch(() => {
              this.$message({
                type: 'info',
                message: '已取消修改'
              })
            })
        } else {
          alert('请先按要求填写信息！')
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.page {
  width: 840px;
  height: 695px;
  box-sizing: border-box;
  margin: 100px 60px 100px 30px;
  padding: 60px 80px;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
  border-radius: 20px;
  &-item {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    &-title {
      p:nth-child(1) {
      }
      p:nth-child(2) {
        margin-top: 29px;
      }
    }
    &-button {
      width: 150px;
      height: 56px;
      background: #302fa6;
      border-radius: 28px;
      color: white;
    }
    &-subTitle {
      color: rgba(0, 0, 0, 0.4);
      margin-left: 60px;
    }
  }
  &-item:nth-child(1) {
    justify-content: flex-start;
  }
  .line {
    width: 780px;
    height: 1px;
    background: rgba(0, 0, 0, 0.1);
  }
}
img {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}

.dialog-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 60px;
  &-form {
    margin-top: 96px;
    /deep/.el-form-item {
      margin-bottom: 40px;
    }
    /deep/.flex-item {
      width: 460px;
      .el-form-item__content {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
      }
      .el-input__inner {
        width: 290px;
      }
    }
    .el-input {
      /deep/&__inner {
        width: 460px;
        height: 60px;
        box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, 0.05);
        border-radius: 20px;
        height: 50px;
        font-size: 18px;
        font-family: AlibabaPuHuiTi-Regular, AlibabaPuHuiTi;
        color: #000000;
      }
    }
    .verif-btn {
      width: 150px;
      height: 56px;
      background: #302fa6;
      border-radius: 28px;
      color: white;
    }
    &-submit {
      text-align: center;
      .pri-btn {
        color: white;
        width: 300px;
        height: 50px;
        background: #302fa6;
        border-radius: 28px;
      }
    }
  }
}
</style>