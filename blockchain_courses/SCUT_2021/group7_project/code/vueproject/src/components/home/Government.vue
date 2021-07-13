<template>
  <body id="poster">
  <el-form class="government-container" label-position="left" label-width="0px">
    <h3 class="uploadRegionInfo">上传地区安全信息</h3>
    <el-form-item>
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
      <el-select v-model="uploadRegionInfoForm.result" auto-complete="off" filterable placeholder="是否高危" @change="select" style="width: 100%">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-date-picker type="datetime"  v-model="uploadRegionInfoForm.date" placeholder="检测时间" value-format="yyyy-MM-dd HH:mm:ss" style="width: 100%;"></el-date-picker>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="uploadRegionInfo ()">上传地区安全信息</el-button>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="resetResult ()">信息重置</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>

<script>
import axios from 'axios'
import { regionDataPlus, CodeToText } from 'element-china-area-data'
export default {
  name: 'Government',
  data () {
    return {
      value: '',
      item: {},
      info: '地区安全信息上传准备中',
      uploadRegionInfoForm: {
        regionName: '',
        date: '',
        result: 0
      },
      options: [
        { label: '安全', value: 0 },
        { label: '存在风险', value: 1 }
      ],
      myoptions: regionDataPlus,
      selectedOptions: []
    }
  },
  methods: {
    handleChange (value) {
      this.uploadRegionInfoForm.regionName = CodeToText[this.selectedOptions[0]] + CodeToText[this.selectedOptions[1]] + CodeToText[this.selectedOptions[2]]
    },
    select (value) {
      this.item = this.options.find((item) => {
        return item.value === value
      })
      this.uploadRegionInfoForm.result = this.item.value
    },
    uploadRegionInfo () {
      if (this.uploadRegionInfoForm.regionName === '') {
        this.showMessageWarning('请上传区域名')
      } else if (this.uploadRegionInfoForm.result === '') {
        this.showMessageWarning('请上传高危信息')
      } else if (this.uploadRegionInfoForm.date === '') {
        this.showMessageWarning('请上传检测时间')
      } else {
        // this.showMessageInfo('地区安全信息上传中')
        let param = new URLSearchParams()
        param.append('regionName', this.uploadRegionInfoForm.regionName)
        param.append('isDangerous', this.item.value)
        param.append('date', this.uploadRegionInfoForm.date)
        console.log(param)
        console.log(this.uploadRegionInfoForm)
        // axios.post('/upload_region_info?', param)
        axios({
          method: 'post',
          url: '/government/upload',
          data: {
            regionName: this.uploadRegionInfoForm.regionName,
            isDangerous: this.uploadRegionInfoForm.result,
            date: this.uploadRegionInfoForm.date
          }
        })
          .then(successResponce => {
            switch (successResponce.data.ret_code) {
              case 0:
                this.showMessageSuccess('地区安全信息上传成功')
                break
              case 1:
                this.showMessageSuccess('地区安全信息更新成功')
                break
              case -1:
                this.showMessageError('地区安全信息上传失败,检测时间晚于当前时间或早于最近一次检测时间')
                break
              case -2:
                this.showMessageError('地区安全信息上传失败,未知错误')
                break
              case -3:
                this.showMessageError('地区安全信息上传失败,无效的区域安全信息')
            }
          })
          .catch(failResponse => {
            this.showMessageError('地区安全信息上传失败')
          })
      }
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
    },
    resetResult () {
      Object.assign(this.$data, this.$options.data())
    }
  }
}
</script>

<style>
.government-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.goverment_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
