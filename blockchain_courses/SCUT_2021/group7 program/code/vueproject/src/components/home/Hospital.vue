<template>
  <body id="poster">
  <el-form class="hospital-container" label-position="left" label-width="0px">
    <h3 class="uploadNucleicAcidResult">上传核酸检测信息</h3>
    <el-form-item>
       <el-date-picker type="datetime"  v-model="uploadNucleicAcidResultForm.time" placeholder="检测时间" value-format="yyyy-MM-dd HH:mm:ss" style="width: 100%;"></el-date-picker>
    </el-form-item>
    <el-form-item>
      <el-select v-model="uploadNucleicAcidResultForm.result" filterable placeholder="检测结果" @change="select" style="width: 100%">
        <el-option v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-input type="text" v-model="uploadNucleicAcidResultForm.userName" auto-complete="off" placeholder="姓名"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="text" v-model="uploadNucleicAcidResultForm.ID" auto-complete="off" placeholder="身份证号"></el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="uploadNucleicAcidResult ()">上传核酸检测信息</el-button>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="resetResult ()">重置信息</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Hospital',
  data () {
    return {
      options: [{
        label: '健康',
        value: 0
      }, {
        label: '可能存在风险',
        value: 1
      }],
      uploadNucleicAcidResultForm: {
        ID: '',
        userName: '',
        time: '',
        result: ''
      },
      info: '检测结果上传中',
      reponseResult: [],
      item: {}
    }
  },
  methods: {
    select (value) {
      this.item = this.options.find((item) => {
        return item.value === value
      })
      this.uploadNucleicAcidResultForm.result = this.item.value
    },
    uploadNucleicAcidResult () {
      if (this.uploadNucleicAcidResultForm.time === '') {
        this.showMessageWarning('请选择日期！')
      } else if (this.uploadNucleicAcidResultForm.result === '') {
        this.showMessageWarning('请上传检测结果！')
      } else if (this.uploadNucleicAcidResultForm.userName === '') {
        this.showMessageWarning('请输入姓名！')
      } else if (this.uploadNucleicAcidResultForm.ID === '') {
        this.showMessageWarning('请输入身份证号！')
      } else {
        let param = new URLSearchParams()
        param.append('time', this.uploadNucleicAcidResultForm.time)
        param.append('result', this.uploadNucleicAcidResultForm.result)
        param.append('userName', this.uploadNucleicAcidResultForm.userName)
        param.append('id', this.uploadNucleicAcidResultForm.ID)
        console.log(param)
        console.log(this.uploadNucleicAcidResultForm)
        // axios.post('/hospital/upload', param)
        // var dateNew = this.uploadNucleicAcidResultForm.time + ' 00:00:00'
        console.log(this.uploadNucleicAcidResultForm.time)
        axios({
          method: 'post',
          url: '/hospital/upload',
          data: {
            time: this.uploadNucleicAcidResultForm.time,
            result: this.uploadNucleicAcidResultForm.result,
            userName: this.uploadNucleicAcidResultForm.userName,
            id: this.uploadNucleicAcidResultForm.ID
          }
        })
        // this.info = '检测结果上传成功'
        // 根据upload_user_info返回的状态码判断上传信息是否成功
          .then(successResponce => {
            switch (successResponce.data.ret_code) {
              case 1:
                this.showMessageSuccess('此人已做过检测，检测结果更新成功！')
                break
              case 0:
                this.showMessageSuccess('检测结果上传成功！')
                break
              case -1:
                this.showMessageError('上传失败，检测时间晚于当前时间或早于最近一次检测时间')
                break
              case -2:
                this.showMessageError('遭遇未知错误')
                break
              case -3:
                this.showMessageError('上传失败，无效的检测结果')
                break
            }
            // if (successResponce.data.ret_code === 1) {
            //   this.$message({
            //     shoClose: true,
            //     message: '结果上传成功！',
            //     type: 'success'
            //   })
            // } else if ()
          })
          .catch(failResponse => {
            this.info = '检测结果上传失败'
          })
      }
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
    },
    resetResult () {
      Object.assign(this.$data, this.$options.data())
    }
  }
}
</script>

<style>
.hospital-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.hospital_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
