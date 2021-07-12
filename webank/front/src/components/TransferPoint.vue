<template>
  <div>
    <el-container>
      <el-header>
        <span style="font-size: 20px;font-weight: bold; margin-left: 48%;">转移积分</span>
      </el-header>
      <el-main>
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="转移的积分：" style="width: 50%; margin-left: 25%;">
            <el-input v-model="form.point" placeholder="请输入要转移的积分"></el-input>
          </el-form-item>
          <el-form-item label="转移的对象：" style="width: 50%; margin-left: 25%;">
            <el-input v-model="form.target" placeholder="请输入转移对象的用户名"></el-input>
          </el-form-item>
          <el-form-item style="width: 50%; margin-left: 37%;">
            <el-button type="primary"  style="width: 30%;" @click="transfer">确认转移</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'TransferPoint',
  data(){
    return{
      form: {
          point: '',
          target: ''
        }
    }
  },

  methods:{
  transfer () {
        var userid = localStorage.getItem('userid');
        this.$axios
          .post('/User/Transfer', {
            _id: userid,
            userName: this.form.target,
            credit: this.form.point,
          })
          .then(res => {
            if (res.data.code === 200) {
              this.$alert('转移积分成功', '结果', {
                confirmButtonText: '确定',
                callback: action => {
                  this.$message({
                    type: 'info',
                    message: `action: ${ action }`
                  });
                }
              });
            }
            else{
              this.$alert('转移积分失败', '结果', {
                confirmButtonText: '确定',
                callback: action => {
                  this.$message({
                    type: 'info',
                    message: `action: ${ action }`
                  });
                }
              });
            }
          })
          .catch(failResponse => {
          })
      }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
