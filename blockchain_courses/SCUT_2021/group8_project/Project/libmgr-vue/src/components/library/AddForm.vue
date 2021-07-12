<template>
<div>
<i class="el-icon-menu"></i>
<el-button type="text" @click="dialogVisible = true">借书</el-button>
<el-dialog
  title=""
  :append-to-body="true"
  :visible.sync="dialogVisible"
  width="30%"
  :before-close="handleClose">
  <h2>借书登记</h2>
  <el-input type="text" v-model="borrow.book_name"
                auto-complete="off" placeholder="书索号"></el-input>
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
      borrow: {
        username: '',
        book_name: ''
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
      // console.log('你好')
      // console.log(sessionStorage.getItem('userName')) // 可以取出用户名,之后调用后端接口借阅即可
      if (sessionStorage.getItem('userName') === null) { // 没有登录，用户名不存在
        const h = this.$createElement

        this.$notify({
          title: '',
          message: h('i', { style: 'color: teal' }, '请重新登录')
        })
        this.$router.replace({path: '/login'})
      } else {
        this.$axios
          .post('/user/borrowBook', {
            book_id: this.borrow.book_name
          }).then(successResponse => {
            if (successResponse.data.code === '0') {
              const h = this.$createElement

              this.$notify({
                title: '',
                message: h('i', { style: 'color: teal' }, '有书逾期，请往管理员处交付罚款')
              })
            } else if (successResponse.data.code === '-2') {
              const h = this.$createElement

              this.$notify({
                title: '',
                message: h('i', { style: 'color: teal' }, '此书不存在')
              })
            } else if (successResponse.data.code === '-3') {
              const h = this.$createElement

              this.$notify({
                title: '',
                message: h('i', { style: 'color: teal' }, '此书已借出')
              })
            } else {
              const h = this.$createElement

              this.$notify({
                title: '',
                message: h('i', { style: 'color: teal' }, '借阅成功')
              })
            }
          })
        this.$bus.emit('LoadBooks') // 借阅后，重新加载书本
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
