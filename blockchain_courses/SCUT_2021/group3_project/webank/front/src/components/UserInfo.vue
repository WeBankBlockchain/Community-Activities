<template>
  <el-container>
    <el-main>

      <el-row>
        <el-row>
          <el-col :span="24"><div class="grid-content bg-purple-light">
            <div class="bform">
              <div style="margin: 20px 0;">用户id</div>
              <input type="text" placeholder="用户id" v-model="form._id":disabled="true">
              <div style="margin: 20px 0;">用户名</div>
              <input type="text" placeholder="用户名" v-model="form.userName":disabled="true">
              <div style="margin: 20px 0;">密码</div>
              <input type="password" placeholder="密码" v-model="form.pwd":disabled="true">
              <div style="margin: 20px 0;">个人简介</div>
              <input type="test" placeholder="个人简介" v-model="form.description":disabled="true">
              <div style="margin: 20px 0;">用户积分</div>
              <input type="text" placeholder="用户积分" v-model="form.credit":disabled="true">
              <div style="margin: 20px 0;">用户地址</div>
              <input type="text" placeholder="用户地址" v-model="form.address":disabled="true">
              <div style="margin: 20px 0;"></div>
              <el-button type="primary" @click="dialogFormVisible = true">修改个人信息</el-button>

              <el-dialog title="个人信息" :visible.sync="dialogFormVisible">
                <el-form :model="newform">
                  <el-form-item label="用户名" :label-width="formLabelWidth">
                    <el-input v-model="newform.username" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="用户密码" :label-width="formLabelWidth">
                    <el-input v-model="newform.userpwd" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="用户描述" :label-width="formLabelWidth">
                    <el-input v-model="newform.info" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="用户地址" :label-width="formLabelWidth">
                    <el-input v-model="newform.address" autocomplete="off"></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button type="primary" @click="userchange">确 定</el-button>
                  <el-button @click="dialogFormVisible = false">取 消</el-button>
                </div>
              </el-dialog>

            </div></div></el-col>
        </el-row>
      </el-row>
      <div style="margin: 20px 0;"></div>
      <el-row><i class="el-icon-s-cooperation">文件列表</i>
        <div style="margin: 20px 0;"></div>
        <el-table
          :data="tableData"
          stripe
          border
          style="width: 60% ;margin-left:20%">
          <el-table-column
            prop="userName"
            label="用户名"
            style="width:50%">
          </el-table-column>
          <el-table-column
            prop="dataName"
            label="数据名"
            style="width:50%">
          </el-table-column>
          <el-table-column
            prop="id"
            label="数据ID"
            style="width:50%">
          </el-table-column>
          <el-table-column
            prop="downloadTimes"
            label="下载次数"
            style="width:50%">
          </el-table-column>
          <el-table-column
            prop="dataDescription"
            label="详情"
            style="width:300%">
          </el-table-column>
          <el-table-column
            prop="address"
            label="地址"
            style="width:300%">
          </el-table-column>
          <el-table-column
            prop="type"
            label="类型"
            style="width:300%">
          </el-table-column>
          <el-table-column
            prop="credit"
            label="积分"
            style="width:300%">
          </el-table-column>
          <el-table-column
            label="操作"
            style="width:100%">
            <template slot-scope="scope">
              <el-button type="text" @click="fileFormVisible = true,change=scope.row.id,changeuser=scope.row.userName">修改文件信息</el-button>
            </template>>
          </el-table-column>
        </el-table>
      </el-row>
      <el-dialog title="文件信息" :visible.sync="fileFormVisible">
        <el-form :model="fileform">
          <el-form-item label="文件名" :label-width="formLabelWidth">
            <el-input v-model="fileform.dataName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="文件描述" :label-width="formLabelWidth">
            <el-input v-model="fileform.dataDescription" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="文件地址" :label-width="formLabelWidth">
            <el-input v-model="fileform.address" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="文件类型" :label-width="formLabelWidth">
            <el-select v-model="fileform.type" style="width: 100%" placeholder="请选择文件类型">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="文件积分" :label-width="formLabelWidth">
            <el-input v-model="fileform.credit" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="filechange()">确 定</el-button>
          <el-button @click="fileFormVisible = false">取 消</el-button>
        </div>
      </el-dialog>

      <div style="margin: 80px 0;"></div>
      <el-row>
        <i class="el-icon-message-solid">接收方消息列表</i>
        <div style="margin: 20px 0;"></div>
        <el-table
          :data="resdata"
          border
          stripe
          style="width: 60%;margin-left:20%">
          <el-table-column
            prop="reqId"
            label="消息发送方"
            style="width:50%">
          </el-table-column>
          <el-table-column
            prop="resId"
            label="消息接收方"
            style="width:50%">
          </el-table-column>
          <el-table-column
            prop="freeId"
            label="消息Id"
            style="width:200%">
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态（0：申请中，1：批准，2：拒绝）"
            style="width:200%">
          </el-table-column>
          <el-table-column
            prop="text"
            label="消息内容"
            style="width:200%">
          </el-table-column>/
          <el-table-column
            fixed="right"
            label="操作"
            style="width:100%">
            <template slot-scope="scope">
              <el-button @click="yes(scope.row.freeId)" type="text" size="small">同意</el-button>
              <el-button @click="no(scope.row.freeId)" type="text" size="small">拒绝</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>

      <div style="margin: 80px 0;"></div>
      <el-row>
        <i class="el-icon-message-solid">发送方消息列表</i>
        <div style="margin: 20px 0;"></div>
        <el-table
          :data="reqdata"
          border
          stripe
          style="width: 60%;margin-left:20%">
          <el-table-column
            prop="reqId"
            label="消息发送方"
            style="width:50%">
          </el-table-column>
          <el-table-column
            prop="resId"
            label="消息接收方"
            style="width:50%">
          </el-table-column>
          <el-table-column
            prop="freeId"
            label="消息Id"
            style="width:200%">
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态（0：申请中，1：批准，2：拒绝）"
            style="width:200%">
          </el-table-column>
          <el-table-column
            prop="text"
            label="消息内容"
            style="width:200%">
          </el-table-column>
        </el-table>
      </el-row>

      <div style="margin: 80px 0;"></div>
      <el-row>
        <i class="el-icon-message-solid">已处理消息列表</i>
        <div style="margin: 20px 0;"></div>
        <el-table
          :data="fdata"
          border
          stripe
          style="width: 60%;margin-left:20%">
          <el-table-column
            prop="reqId"
            label="消息发送方"
            style="width:50%">
          </el-table-column>
          <el-table-column
            prop="resId"
            label="消息接收方"
            style="width:50%">
          </el-table-column>
          <el-table-column
            prop="freeId"
            label="消息Id"
            style="width:200%">
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态（0：申请中，1：批准，2：拒绝）"
            style="width:200%">
          </el-table-column>
          <el-table-column
            prop="text"
            label="消息内容"
            style="width:200%">
          </el-table-column>
        </el-table>
      </el-row>

    </el-main>>
  </el-container>
</template>

<script>
  export default{
    name:'UserInfo',
    data(){
      return {
        isLogin:false,
        passwordError: false,
        existed: false,
        dialogFormVisible: false,
        fileFormVisible:false,

        owner:localStorage.getItem('login/username'),
        input:"",
        change:"",
        changeuser:"",
        form:{//这些用localstorage初始化一下
          _id:'',
          userName:'',
          pwd:'',
          credit:'',
          address:'',
          description:''
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
        fileform:
          {
            dataName: "",
            dataDescription:"",
            type:"",
            credit:"",
            address:"",
            id:"",
            ownerId:""
          },
        mynew:
          {
            dataName: "",
            dataDescription:"",
            type:"",
            credit:"",
            address:"",
            id:"",
            ownerId:""
          },
        newform:{
          username:localStorage.getItem('username'),
          userpwd:'',
          num:'',
          address:'',
          info:''
        },
        formLabelWidth: '120px',
        fdata: [{
          reqId:'',
          resId:'',
          freeId:'',
          status:'',
          text:'',
        }],
        reqdata: [{
          reqId:'',
          resId:'',
          freeId:'',
          status:'',
          text:'',
        }],
        resdata: [{
          reqId:'',
          resId:'',
          freeId:'',
          status:'',
          text:'',
        }],
        tableData: [{
          userName:'',
          ownerId:'',
          id:'',
          credit:'',
          dataName:'',
          dataDescription:'',
          address:'',
          type:'',
          downloadTimes:'',
        }]
      }
    },
    mounted:function() {
      this.handleEdit();
    },
    methods:{
      handleEdit:function(){
        this.$axios(
          {
            method:'post',
            url: '/User/getUser',
            data: {
              userName:localStorage.getItem('username')
            }
          })
          .then(response => {
            this.form = response.data.data
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
        this.$axios(
          {
            method:'post',
            url: '/Data/getSelfData',
            data: {
              _id:localStorage.getItem('userid')
            }
          })
          .then(response => {
            this.tableData = response.data.data
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
        this.$axios(
          {
            method:'post',
            url: '/Message/getAllMessage_finished',
            data: {
              _id:localStorage.getItem('userid')
            }
          })
          .then(response => {
            this.fdata = response.data.data
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
        this.$axios(
          {
            method:'post',
            url: '/Message/getAllMessage_req',
            data: {
              _id:localStorage.getItem('userid')
            }
          })
          .then(response => {
            this.reqdata = response.data.data
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
        this.$axios(
          {
            method:'post',
            url: '/Message/getAllMessage_res',
            data: {
              _id:localStorage.getItem('userid')
            }
          })
          .then(response => {
            this.resdata = response.data.data
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
      },
      yes(freeId)
      {
        this.$axios(
            {
              method:'post',
              url: '/Message/approve',
              data: {
                freeId:freeId
              }
            })
          .then(res => {
            if (res.data.code == 200) {
              this.$alert("同意成功", '提示', {
                confirmButtonText: '确定'
              })
            } else {
              this.$alert("失败", '提示', {
                confirmButtonText: '确定'
              })
            }
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
        this.$router.go(0)
      },
      no(freeId)
      {
        this.$axios(
          {
            method:'post',
            url: '/Message/deny',
            data: {
              freeId:freeId
            }
          })
          .then(res => {
            if (res.data.code == 200) {
              this.$alert("拒绝成功", '提示', {
                confirmButtonText: '确定'
              })
            } else {
              this.$alert("失败", '提示', {
                confirmButtonText: '确定'
              })
            }
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
        this.$router.go(0)
      },
      userchange()
      {
        const self=this;
        this.dialogFormVisible = false
        if(self.newform.username != "" && self.newform.userpwd != ""&& self.newform.info != ""&& self.newform.address != ""){
          self.$axios({
            method:'post',
            url: '/User/alter',
            data: {
              _id:localStorage.getItem('userid'),
              username:self.newform.username,
              pwd: self.newform.userpwd,
              description: self.newform.info,
              address:self.newform.address
            }
          })
            .then( res => {
              switch(res.data.code){
                case 200:
                  alert("上传成功！");
                  this.login();
                  break;
                case -1:
                  this.existed = true;
                  break;
              }
            })
            .catch( err => {
              console.log(err);
            })
        } else {
          alert("填写不能为空！");
        }
        this.$router.go(0)

      },
      filechange()
      {
        this.fileFormVisible = false;
        const self=this;
        if(self.changeuser != ""){
          self.$axios({
            method:'post',
            url: '/Data/alter',
            data: {
              userName:self.changeuser,
              id:self.change,
              credit:self.fileform.credit,
              dataName:self.fileform.dataName,
              dataDescription: self.fileform.dataDescription,
              address:self.fileform.address,
              type: self.fileform.type,
            }
          })
            .then( res => {
              switch(res.data.code){
                case 200:
                  alert("上传成功！");
                  this.login();
                  break;
                case -1:
                  this.existed = true;
                  break;
              }
            })
            .catch( err => {
              console.log(err);
            })
        } else {
          alert("填写不能为空！");
        }
        this.$router.go(0)
      },
    }
  }
</script>

<style scoped="scoped">
  #poster {
    /*background:url("../assets/back.jpg") no-repeat;*/
    height: 100%;
    width: 100%;
    top:0;
    position: absolute;
  }
  body{
    margin: 0px;
  }
  .btitle{
    font-size: 1.5em;
    font-weight: bold;
    color: rgb(57,167,176);
  }
  .bform{
    float:left;
    width: 100%;
    height: 60%;
    padding: 2em 0;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
  }
  .bform .errTips{
    display: block;
    width: 50%;
    text-align: left;
    color: red;
    font-size: 0.7em;
    margin-left: 1em;
  }
  .bform input{
    width: 50%;
    height: 30px;
    border: none;
    outline: none;
    border-radius: 10px;
    padding-left: 2em;
    background-color: #f0f0f0;
  }
  juzhong
  {
    position: absolute;
  }
</style>
