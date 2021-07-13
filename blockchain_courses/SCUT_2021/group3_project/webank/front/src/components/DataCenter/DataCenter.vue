<template>
  <div>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <SideMenu @indexSelect="listByCategory" ref="sideMenu"></SideMenu>
    </el-aside>
    <el-main>
      <Data class="data-area" ref="dataArea"></Data>
    </el-main>
  </el-container>
  </div>
</template>

<script>
  import SideMenu from './SideMenu'
  import Data from './Data'

  export default {
    name: 'DataCenter',
    components: {Data, SideMenu},
    methods: {
      listByCategory () {
        var _this = this
        var cid = this.$refs.sideMenu.cid
        localStorage.setItem('cid',cid)
        if(cid === '0'){
          this.$axios.get('/Data/getAllData').then(resp => {
            if (resp && resp.data.code === 200) {
              _this.$refs.dataArea.data = resp.data.data
              _this.$refs.dataArea.currentPage = 1
            }
          })
        }
        if(cid === '1'){
          this.$axios
              .post('/Data/getData', {dataName: null, type: "文本"}).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.$refs.dataArea.data = resp.data.data
              _this.$refs.dataArea.currentPage = 1
            }
          })
        }
        if(cid === '2'){
          this.$axios
              .post('/Data/getData', {dataName: null, type: "图片"}).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.$refs.dataArea.data = resp.data.data
              _this.$refs.dataArea.currentPage = 1
            }
          })
        }
        if(cid === '3'){
          this.$axios
              .post('/Data/getData', {dataName: null, type: "音频"}).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.$refs.dataArea.data = resp.data.data
              _this.$refs.dataArea.currentPage = 1
            }
          })
        }
        if(cid === '4'){
          this.$axios
              .post('/Data/getData', {dataName: null, type: "视频"}).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.$refs.dataArea.data = resp.data.data
              _this.$refs.dataArea.currentPage = 1
            }
          })
        }
        if(cid === '5'){
          this.$axios
              .post('/Data/getData', {dataName: null, type: "可执行文件"}).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.$refs.dataArea.data = resp.data.data
              _this.$refs.dataArea.currentPage = 1
            }
          })
        }
        if(cid === '6'){
          this.$axios
              .post('/Data/getData', {dataName: null, type: "其他"}).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.$refs.dataArea.data = resp.data.data
              _this.$refs.dataArea.currentPage = 1
            }
          })
        }
      }
    }
  }
</script>

<style scoped>
  .data-area {
    width: 990px;
    margin-left: auto;
    margin-right: auto;
  }
</style>