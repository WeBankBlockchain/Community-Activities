<template>
  <div class="page">
      <el-table class="font-regular-18" :data="medicalList" style="width: 100%">
        <el-table-column prop="batchCode" label="批次号"> </el-table-column>
        <el-table-column prop="category" label="类别" ></el-table-column>
        <el-table-column prop="province"  label="所在省份"></el-table-column>
        <el-table-column prop="resourceName"  label="名称"></el-table-column>
        <el-table-column prop="resourceNum"  label="数量"></el-table-column>
        <el-table-column  label="操作">
          <template slot-scope="scope">
          <el-button @click="transferDialog(scope.row.resourceId,scope.row.resourceName)">转移</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog
      :visible.sync="dialogVisible"
      width="30%"
      class="loginDialog">
      <div style="text-align:center;">
      <img class="loginLogo"
           src="@asset/FISCO_BCOS_Logo.svg">
      </div>
      <div style="text-align:center;margin-top:72px">
          <el-select v-model="user" placeholder="请选择用户">
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.name"
              :value="item.userId">
            </el-option>
          </el-select>
      </div>
      <div style="text-align:center;margin-top:30px">
        <el-select disabled v-model="diaName" placeholder="请选择物资">
        <el-option
          v-for="item in medicalList"
          :key="item.resourceId"
          :label="item.resourceName"
          :value="item.resourceId">
        </el-option>
      </el-select>
      </div>
      <el-button type="primary"
                 class="loginButton"
                 round
                 size="mini"
                 @click="transfer()"
                 >转移物资</el-button>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //选择的用户
      user:"",
      //选择的资源
      medical:"",
      // 用户列表
      userList:[],
      // 资源列表
      medicalList:[],
      //对话框显示
      dialogVisible:false,
      diaName:"",
      resourceId:"",
    }
  },
  created(){
    this.getMedicalList();
    this.getUserList();
  }, 
  methods: {
    async transfer(){
      const res = await this.$http.put('/chain/transfter',{"toUserId":this.user,"resourceId":this.resourceId})
      if(res.data.status!==200){
        console.log(res);
        return this.$message.error("转移物资失败")
      }
      this.$message.success("转移物资成功")
      // console.log(res);
      this.$router.go(0);
    },
    transferDialog(Id,name){
      this.diaName = name;
      this.resourceId = Id
      // const token = window.sessionStorage.getItem('token')
      // if(!token) return this.$message.error('请先登录')
      this.dialogVisible = true;
    },
    //获取医疗物资列表
    async getMedicalList(){
      const res = await this.$http.get('chain/myResources')
      // console.log(res);
      if(res.data.status!==200) return this.$message.error("获取医疗物资列表失败")
      this.medicalList = res.data.data
      // console.log(this.medicalList);
    },
    //获取用户列表
    async getUserList(){
        const res = await this.$http.get('user/list')
        // console.log(res);
        if(res.data.status !== 200) return this.$message.error("获取用户列表失败") 
        this.userList = res.data.data;
    },
  }
}
</script>

<style lang="less" scoped>
.loginButton {
  width: 300px;
  height: 56px;
  background: #302fa6;
  font-size: 18px;
  color: #ffffff;
  border-radius: 28px !important;
  display: block;
  margin: 0 auto;
  margin-top: 25px;
  outline-style: none;
}
.loginDialog {
  display: flex;
  align-items: center;
  flex-direction:row-reverse
}
.loginLogo {
  width: 112px;
  height: 58px;
  margin-top: 56px;
}
.page {
  width: 840px;
  height: 817px;
  box-sizing: border-box;
  margin: 100px 60px 100px 30px;
  padding: 10px 30px;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  align-items: center;
  //   justify-content: space-between;
  box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
  border-radius: 20px;
  overflow-y: auto;
}
</style>