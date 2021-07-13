<template>
<body id="sda">
        <el-button v-on:click="uploadNucleicAcidResult ()">test</el-button>
</body>
</template>

<script>
import axios from 'axios'
export default {
  name: 'test',
  //   data () {
  //     return {
  //       uploadNucleicAcidResultForm: {
  //         time: '',
  //         result: '',
  //         userName: '',
  //         ID: ''
  //       },
  //       info: '检测结果上传中',
  //       reponseResult: []
  //     }
  //   },
  methods: {
    uploadNucleicAcidResult () {
      let param = new URLSearchParams()
      param.append('ID', 'sa')
      param.append('userName', 'dsd')
      param.append('time', 'dsd')
      param.append('result', 1)
      console.log(param)
      axios.post('/hospital/upload?', param)
        .then(successResponce => {
          this.$message({
            shoClose: true,
            message: successResponce.data.ret_code
          })
        })
        .catch(failResponse => {
          this.$message({
            shoClose: true,
            message: '不行'
          })
        })
      // ({
      //   url: 'http://192.168.124.79:8888/hospital/upload',
      //   method: 'post',
      //   data: {
      //     ID: 'sda',
      //     userName: 'ada',
      //     time: 'ada',
      //     result: 1
      //   }
      // })
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
