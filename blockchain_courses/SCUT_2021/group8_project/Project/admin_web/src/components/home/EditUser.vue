<template>
  <div style="text-align: left">
    <el-button class="add-button" type="primary" @click="dialogFormVisible = true">注册用户</el-button>
    <el-dialog
      title="注册用户"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-form v-model="form" style="text-align: left" ref="dataForm">
        <el-form-item label="id" :label-width="formLabelWidth" prop="id">
          <el-input v-model="form.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'EditUser',
  data () {
    return {
      dialogFormVisible: false,
      form: {
        id: '',
        name: ''
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    clear () {
      this.form = {
        id: '',
        name: ''
      }
    },
    onSubmit () {
      this.$axios
        .post('/insertUser', {
          id: this.form.id,
          name: this.form.name
        }).then(resp => {
          if (resp && resp.data.code === '1') {
            this.dialogFormVisible = false
            this.$emit('onSubmit')
            this.$message({
              type: 'info',
              message: resp.data.message
            })
          } else if (resp.data.code !== '1') {
            this.$notify({
              title: '',
              message: resp.data.message
            })
          }
        })
    }
  }
}
</script>

<style scoped>
.add-button {
  margin: 18px 0 0 10px;
}
</style>
