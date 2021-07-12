<template>
<body id = "poster">
    <el-form class="market-contain" label-position="left" label-width="0px">
        <el-form-item>
            <el-input v-model="hashValue" auto-complete="false" placeholder="用户健康哈希值"></el-input>
        </el-form-item>
        <!-- <el-form-item>
          <el-input v-model="codeContent" @blur="createQRcode" placeholder="用户哈希值"></el-input>
        </el-form-item> -->
        <el-form-item>
          <img v-if="hashValue" id="qrcodeImg"/>
          <div class="image-box">
              <input accept="image/*" id="file" type="file" @change="handleImageUpload" />
          </div>
        </el-form-item>
        <el-form-item style="width: 100%">
            <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="uploadHashValue ()">上传用户信息</el-button>
        </el-form-item>
    </el-form>
</body>
</template>

<script>
// npm install --save jimp
// npm install jsqr --save
import axios from 'axios'
import QRCode from 'qrcode'
import QrcodeDecoder from 'qrcode-decoder'
export default {
  name: 'Market',
  data () {
    return {
      hashValue: '',
      result: '',
      type: '',
      // imgurl:
      //   'https://raw.githubusercontent.com/MuGuiLin/QRCode/master/VueQRCode/src/assets/github.com.png',
      // imgurl2:
      //   'https://raw.githubusercontent.com/MuGuiLin/QRCode/master/VueQRCode/src/assets/github.com.png',
      // cvsele: Object,
      // canvas: Object
      codeContent: '',
      codeImage: ''
    }
  },
  // mounted: {
  //   cvsele = this.$refs.canvas,
  //   canvas = this.cvsele.getContext("2d")
  // },
  methods: {
    uploadHashValue () {
      if (this.hashValue === '') {
        this.$message({
          shoClose: true,
          message: '请输入健康哈希值！',
          type: 'warning'
        })
      } else {
        let param = new URLSearchParams()
        param.append('userHealthHash', this.hashValue)
        console.log(this.hashValue)
        // axios.post('/user/isHealthy', param)// this.result = 'keyi'
        axios({
          method: 'post',
          url: 'user/checkIsHealthy',
          data: {
            userHealthHash: this.hashValue
          }
        })
          .then(successResponse => {
            if (successResponse.data.ret_code === 0) {
              // if (successResponse.data.ret_info === '0') {
              //   if (successResponse.data.is_health === '0') {
              //     this.result = '该用户健康'
              //     this.type = 'success'
              //   }
              //   if (successResponse.data.is_health === '1') {
              //     this.result = '该用户可能存在感染风险！'
              //     this.type = 'error'
              //   }
              //   if (successResponse.data.is_health === '2') {
              //     this.result = '未知'
              //     this.type = ''
              //   }
              // }
              // if (successResponse.data.is_valid === '1') {
              //   this.result = '该用户的健康码不存在'
              //   this.type = 'error'
              // }
              // if (successResponse.data.is_valid === '2') {
              //   this.result = '该用户的健康码已过期'
              //   this.type = 'error'
              // }
              this.result = successResponse.data.ret_info
              if (successResponse.data.ret_info === 'Healthy') {
                this.type = 'success'
              }
              if (successResponse.data.ret_info === 'Unhealthy') {
                this.type = 'error'
              }
              this.$message({
                shoClose: true,
                message: this.result,
                type: this.type
              })
            }
          })
          .catch(failResponse => {
            this.result = '404'
            this.type = 'error'
            this.$message({
              shoClose: true,
              message: this.result,
              type: this.type
            })
          })
      }
    },
    // uploadFile (e) {
    //   const file = e.target.files[0]
    //   const createObjectURL =
    //     window.createObjectURL ||
    //     window.URL.createObjectURL ||
    //     window.webkitURL.createObjectUR
    //   this.hashValueFromMa = ''
    //   this.imgurl = createObjectURL(file)

    //   const fReader = new FileReader()
    //   fReader.readAsDataURL(file) // Base64 8Bit字节码
    //   // fReader.readAsBinaryString(file);  // Binary 原始二进制
    //   // fReader.readAsArrayBuffer(file);   // ArrayBuffer 文件流
    //   fReader.onload = (e) => {
    //     this.imgurl2 = e.target.result
    //     e.target.result && Jimp.read(e.target.result).then(async (res) => {
    //       const { data, width, height } = res.bitmap
    //       try {
    //         const resolve = await jsQR(data, width, height)
    //         this.hashValueFromMa = resolve.data
    //         console.info('识别结果：', resolve.data)
    //       } catch (err) {
    //         this.hashValueFromMa = '识别失败，请检查二维码是否正确！'
    //         console.error('识别失败，请检查二维码是否正确！', err)
    //       } finally {
    //         console.info('读取到的文件：', res)
    //       }
    //     })
    //       .catch((err) => {
    //         console.error('文件读取错误：', err)
    //       })
    //   }
    // },
    // 生成二维码
    createQRcode () {
      let qrcodeDom = document.getElementById('qrcodeImg')
      if (this.codeContent) {
        QRCode.toDataURL(this.codeContent).then(url => {
          qrcodeDom.setAttribute('src', url)
        })
          .catch(err => {
            console.error(err)
          })
      }
    },
    // 处理导入的图片
    async handleImageUpload (file) {
      let imgUpload = document.getElementById('file')
      if (imgUpload.files[0].type.indexOf('image/') >= 0) {
        let qr = new QrcodeDecoder()
        let res = await qr.decodeFromImage(this.getObjectURL(imgUpload.files[0]))
        if (res) {
          let reader = new FileReader()
          reader.readAsDataURL(imgUpload.files[0])
          if (res.data !== '') {
            reader.onload = function (e) {
              let url = this.result.substring(this.result.indexOf(',') + 1)
              let qrcodeDom = document.getElementById('qrcodeImg')
              qrcodeDom.setAttribute('src', 'data:image/png;base64,' + url)
            }
            this.hashValue = res.data
          }
        } else {
          imgUpload.value = ''
          this.$message.error('二维码格式错误，请重新上传')
        }
      } else {
        this.$message.error('请上传图片')
      }
    },
    getObjectURL (file) {
      var url = null
      if (window.createObjectURL !== undefined) { // basic
        url = window.createObjectURL(file)
      } else if (window.URL !== undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file)
      } else if (window.webkitURL !== undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file)
      }
      return url
    }
  }
}
</script>

<style>
  .market-contain {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 50px auto;
    width: 350px;
    text-align: center;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
</style>
