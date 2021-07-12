<template>
  <el-container>
    <el-main>
      数据名
      <div style="margin: 20px 0;"></div>
      <el-input
        style="width: 50%"
        :autosize="{ minRows: 1, maxRows: 1}"
        placeholder="数据名"
        v-model="dataName">
      </el-input>
      <div style="margin: 20px 0;"></div>
      所需积分
      <div style="margin: 20px 0;"></div>
      <el-input
        style="width: 50%"
        :autosize="{ minRows: 1, maxRows: 1}"
        placeholder="所需积分"
        v-model="credit">
      </el-input>
      <div style="margin: 20px 0;"></div>
      文件类型
      <div style="margin: 20px 0;"></div>
      <el-select v-model="value" style="width: 50%" placeholder="请选择文件类型">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <div style="margin: 20px 0;"></div>
      描述
      <div style="margin: 20px 0;"></div>
      <el-input
        style="width: 50%"
        :autosize="{ minRows: 5, maxRows: 10}"
        placeholder="描述"
        v-model="dis">
      </el-input>
      <div style="margin: 20px 0;"></div>
      不允许访问名单
      <div style="margin: 20px 0;"></div>
      <el-input
        style="width: 50%"
        :autosize="{ minRows: 1, maxRows: 5}"
        placeholder="不许访问的用户名单"
        v-model="not">
      </el-input>
      <div style="margin: 20px 0;"></div>
      选择文件
      <div style="margin: 20px 0;"></div>
      <input class="file" type="file" title="请选择文件" value="请选择文件">
      </el-main>
    <el-footer>

      <el-button style="margin-left: 10px;" size="small" type="success" @click="upload">上传到服务器</el-button>

    </el-footer>
  </el-container>
</template>

<script>
  let formData = new window.FormData()
  export default {
    name: "UpLoad",
    data() {
      return {
        fileList: [{}],
        dataName: "",
        not:"",
        dis:"",
        credit:"",
        form:{
          owner:localStorage.getItem('userid'),
          address:""
        },
        options: [{
          value: '文本',
          label: '文本'
        }, {
          value: '图片',
          label: '图片'
        }, {
          value: '音频',
          label: '音频'
        }, {
          value: '视频',
          label: '视频'
        }, {
          value: '可执行文件',
          label: '可执行文件'
        }, {
          value: '其它',
          label: '其它'
        }],
        value: ""
      };
    },
    methods: {
      upload()
      {
          const self = this
        formData.append('file', document.querySelector('input[type=file]').files[0])
        formData.append('ownerId',self.form.owner)
        formData.append('credit',self.credit)
        formData.append('not',self.not)
        formData.append('dataName',self.dataName)
        formData.append('dataDescription',self.dis)
        formData.append('address',self.address)
        formData.append('type',self.value)
        
        if(self.dataName != ""){
          self.$axios({
            method:'post',
            url: '/file/uploadFile',
            data: formData
              /*ownerId:self.form.owner,
              credit:self.credit,
              not: self.not,
              dataName: self.dataName,
              dataDescription: self.dis,
              address:self.address,
              type:self.value,*/
              
          })
            .then( res => {
              switch(res.code){
                case 200:
                  alert("上传成功！");
                  break;
                case -1:
                  this.existed = true;
                  break;
              }
            })
            .catch( err => {
              console.log(err);
            })
            this.$router.go(0)
        } else {
          alert("填写不能为空！");
        }
      },
      submitUpload() {
        this.$refs.upload.submit();
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },

    }
  }
</script>

<style scoped>

</style>
