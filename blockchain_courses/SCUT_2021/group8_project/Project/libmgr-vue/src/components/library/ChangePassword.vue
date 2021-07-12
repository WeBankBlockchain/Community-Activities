<template>
<div>
<i class="el-icon-menu"></i>
<el-button type="text" @click="dialogVisible = true">修改密码</el-button>
<el-dialog
  title=""
  :append-to-body="true"
  :visible.sync="dialogVisible"
  width="30%"
  :before-close="handleClose">
  <h2>修改密码</h2>
  <el-input type="text" v-model="change_psw_data.old_password"
                auto-complete="off" placeholder="现在的密码"></el-input>
  <h4></h4>
  <el-input type="text" v-model="change_psw_data.new_password"
                 auto-complete="off" placeholder="新密码"></el-input>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false" v-on:click="QueDing">确 定</el-button> <!--可以在这里绑定确定后的函数-->
  </span>
</el-dialog>
</div>
</template>

<script>
export default {
  data () {
    return {
      dialogVisible: false,
      change_psw_data: {
        username: '',
        old_password: '',
        new_password: ''
      }
    }
  },
  methods: {
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    QueDing () {
      // console.log(sessionStorage.getItem('userName'))
      // 修改密码，若旧密码不对，出现通知框：旧密码错误
      if (sessionStorage.getItem('userName') === null) { // 没有登录，用户名不存在
        const h = this.$createElement

        this.$notify({
          title: '',
          message: h('i', { style: 'color: teal' }, '请重新登录')
        })
        this.$router.replace({path: '/login'})
      } else {
        this.$axios
          .post('user/changePassword', {
            user_id: sessionStorage.getItem('userName'),
            old_password: this.change_psw_data.old_password,
            new_password: this.change_psw_data.new_password
          }).then(successResponse => {
            if (successResponse.data.code === '-1') { // 旧密码输入错误
              const h = this.$createElement

              this.$notify({
                title: '',
                message: h('i', { style: 'color: teal' }, '旧密码输入错误')
              })
            } else { // 旧密码输入正确
              const h = this.$createElement

              this.$notify({
                title: '',
                message: h('i', { style: 'color: teal' }, '修改成功')
              })
              this.$router.replace({path: '/login'})
            }
          })
      }
    }
  }
}
</script>

<style>
h2 {
  text-align: center;
}
</style>
