<template>
  <div>
    <el-row style="height: 840px;">
      <search-bar @onSearch="searchResult" ref="searchBar"></search-bar>
      <el-tooltip effect="dark" placement="right"
                  v-for="item in data"
                  :key="item.id">
        <p slot="content" style="font-size: 14px;margin-bottom: 6px;">{{item.dataName}}</p>
        <p slot="content" style="font-size: 13px;margin-bottom: 6px">
          <span>作者：{{item.userName}}</span>
        </p>
        <p slot="content" style="font-size: 13px; margin-bottom: 6px;">所需积分：{{item.credit}}</p>
        <p slot="content" style="font-size: 13px; margin-bottom: 6px;">下载次数：{{item.downloadTimes}}</p>
        <p slot="content" style="width: 300px; font-size: 13px; margin-bottom: 6px;" >概述：{{item.dataDescription}}</p>
        <el-card style="width: 135px;margin-bottom: 20px;height: 112px;float: left;margin-right: 15px" class="book"
                 bodyStyle="padding:10px" shadow="hover">
          <div class="info">
            <div class="title">
              {{item.dataName}}
            </div>
          </div>
          <div class="author" style="margin-top: 6px;">{{item.userName}}</div>
          <el-button type="text" @click="open(item.id,item.address)" class="borrow">请求访问</el-button>
          
        </el-card>
      </el-tooltip>
      <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible1"
        width="30%"
        center>
        <span>常规访问将消耗gas及积分，免费访问将消耗gas</span>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="regularRequest()">常规访问</el-button>
            <el-button type="primary" @click="freeRequest()">免费访问</el-button>
        </span>
      </el-dialog>

      <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible2"
        width="30%"
        center>
        <span>你确定要进行常规访问</span>
        <span slot="footer" class="dialog-footer">
            <el-button  @click="centerDialogVisible2 = false">取消</el-button>
            <el-button type="primary" @click="regularRequestConfirm()">确定</el-button>
        </span>
      </el-dialog>

      <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible4"
        width="30%"
        center>
        <span>你确定要进行下载</span>
        <span slot="footer" class="dialog-footer">
            <el-button  @click="centerDialogVisible4 = false">取消</el-button>
            <el-button type="primary" @click="downloadReq()">确定</el-button>
        </span>
      </el-dialog>

      <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible3"
        width="30%"
        center>
        <el-form :model="form">
          <el-form-item label="免费申请理由描述" :label-width="formLabelWidth">
            <el-input v-model="form.description" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button  @click="centerDialogVisible3 = false">取消</el-button>
            <el-button type="primary" @click="freeRequestConfirm()">确定</el-button>
        </span>
      </el-dialog>
    </el-row>
    <el-row>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pagesize"
        :total="data.length">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>
  import SearchBar from './SearchBar'
  export default {
    name: 'Data',
    components: {SearchBar},
    data () {
      return {
        form:{
          description: '',
        },
        userid:'',
        dataid:'',
        mm:'',
        m:'数据访问成功',
        dataAddress:"",
        data: [
          {
          }
        ],
        currentPage: 1,
        pagesize: 10,
        centerDialogVisible1: false,
        centerDialogVisible2: false,
        centerDialogVisible3: false,
        centerDialogVisible4: false,
      }
    },
    mounted: function () {
      this.loaddata()
    },
    methods: {
      downloadReq(){
        console.log(this.dataAddress)
        this.centerDialogVisible4 = false
        this.$axios.get("/file/download", {
          params: {path:this.dataAddress},
          responseType: 'blob'
          }
          ).then((res)=>{
                    console.log('文件下载成功');
                    const blob = new Blob([res.data]);
                    // 获取文件名称
                    const fileName = res.headers['content-disposition'].split(";")[1].split("filename=")[1];
                    //对于<a>标签，只有 Firefox 和 Chrome（内核） 支持 download 属性
                    //IE10以上支持blob，但是依然不支持download
                    if ('download' in document.createElement('a')) {
                        //支持a标签download的浏览器
                        const link = document.createElement('a');//创建a标签
                        link.download = fileName;//a标签添加属性
                        link.style.display = 'none';
                        link.href = URL.createObjectURL(blob);
                        document.body.appendChild(link);
                        link.click();//执行下载
                        URL.revokeObjectURL(link.href); //释放url
                        document.body.removeChild(link);//释放标签
                    } else {
                        navigator.msSaveBlob(blob, fileName);
                    }
                }).catch((res)=>{
                    console.log('文件下载失败');
                });
      },
      loaddata () {
        var _this = this
        this.$axios.get('/Data/getAllData').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.data = resp.data.data
          }
        })
      },
      regularRequest(){
        this.centerDialogVisible2 = true;
        this.centerDialogVisible1 = false;
      },
      regularRequestConfirm(){
        this.centerDialogVisible2 = false;
         this.$axios
              .post('/Request/sharedRequest', {userId: this.userid,dataId: this.dataid}).then(resp => {
          if (resp && resp.data.code === 200) {
            this.centerDialogVisible4 = true
          }
          else{
            this.$alert("用户积分余额不足", '访问结果', {
              confirmButtonText: '确定',
            })
          }
        })
      },
      freeRequest(){
        this.centerDialogVisible3 = true;
        this.centerDialogVisible1 = false;
      },
      freeRequestConfirm(){
        this.centerDialogVisible3 = false;
        this.$axios
              .post('/Request/freeRequest', {userId: this.userid, dataId: this.dataid, message: this.form.description}).then(resp => {
          if (resp && resp.data.code === 200) {
            this.$alert('免费访问申请成功，等待审核结果', '访问申请结果', {
              confirmButtonText: '确定',
            })
          }
          else{
            this.m = resp.data.message;
            this.$alert('免费访问申请失败', '访问申请结果', {
              confirmButtonText: '确定',
            })
          }
        })
      },
      open(id,address) {
        this.centerDialogVisible1 = true;
        this.userid = localStorage.getItem('userid');
        this.dataid = id;
        this.dataAddress = address;
      },
      handleCurrentChange: function (currentPage) {
        this.currentPage = currentPage
      },
      searchResult () {
        console.log("start")
        var _this = this
        var cid = localStorage.getItem('cid')
        console.log(cid)
        var type = ""
        switch(cid){
          case '1':
            type = "文本"
            break
          case '2':
            type = "图片"
            break
          case '3':
            type = "音频"
            break
          case '4':
            type = "视频"
            break
          case '5':
            type = "可执行文件"
            break
          case '6':
            type = "其他"
            break
        }
        console.log(type)
        if(cid === '0'){
          this.$axios
              .post('/Data/getData', {dataName: this.$refs.searchBar.keywords, type: null}).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.data = resp.data.data
              _this.currentPage = 1
            }
          })
        }
        else{
          this.$axios
              .post('/Data/getData', {dataName: this.$refs.searchBar.keywords, type: type}).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.data = resp.data.data
              _this.currentPage = 1
            }
          })
        }
      }   
    }
  }
</script>

<style scoped>
  .cover {
    width: 115px;
    height: 172px;
    margin-bottom: 7px;
    overflow: hidden;
    cursor: pointer;
  }

  img {
    width: 115px;
    height: 172px;
    /*margin: 0 auto;*/
  }

  .title {
    font-size: 14px;
    text-align: left;
  }

  .author {
    color: #333;
    width: 102px;
    font-size: 13px;
    margin-bottom: 6px;
    text-align: left;
  }

  .borrow{
    float: left;
    font-size: 10px;
  }

  a {
    text-decoration: none;
  }

  a:link, a:visited, a:focus {
    color: #3377aa;
  }
</style>