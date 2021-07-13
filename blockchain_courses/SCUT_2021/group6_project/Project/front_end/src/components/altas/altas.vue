
<template>
  <div id="migrate">
    
    <div class="placeholder_bottom_include">
      <div class="placeholder_bottom">
        <div class="title font-medium-24">疫情地图</div>
      </div>
    </div>
    

    <div class="map_bigContainer">
    <div id="main" class="map" style="width: 600px;height:400px;background"></div>
    <el-container class="map_provideDetail">
        <el-header>
          <span class="font-medium-24">{{this.name}}</span>
          
          <span @click="transferDialog()" class="font-regular-18"
                style="color:#302FA6;cursor:pointer;">我要转移物资</span>
        </el-header>
        <el-main>
          <div style="margin-top:18px" class="font-regular-18">口罩:{{medicalForm.mask}}</div>
          <div  style="margin-top:18px" class="font-regular-18">纱布:{{medicalForm.gauze}}</div>
          <div  style="margin-top:18px" class="font-regular-18">创可贴:{{medicalForm.cloth}}</div>
          <div  style="margin-top:18px" class="font-regular-18">防护服:{{medicalForm.aid}}</div>
        </el-main>
        <!-- 写个输入框，输入捐献物资类别，数量，目标人 -->
        <span></span>
      </el-container>
    </div>


    <el-dialog
      :visible.sync="dialogVisible"
      width="30%"
      class="loginDialog">
      <div style="text-align:center" >
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
        <el-select v-model="medical" placeholder="请选择物资">
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
                 @click="transfer()"
                 >转移物资</el-button>
    </el-dialog>

  </div>
</template>

<script>
import echarts from 'echarts'
import './china.js'
export default {
  name: 'altas',
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
      medicalForm:{
        // 口罩
        mask:0,
        // 纱布
        gauze:0,
        // 防护服
        cloth:0,
        // 创可贴
        aid:0,
      },
      pList:[],
      name:'广东',

    }
  },
  created(){
    this.getProvinceResource("广东");
    this.getUserList();
    this.getMyResource();
  },
  mounted(){
    this.initMap()
  },
  methods: {
    //转移物资
    async transfer(){
      const res = await this.$http.put('chain/transfter',{'toUserId':this.user,'resourceId':this.medical})
      if(res.data.status!==200){
        console.log(res);
        return this.$message.error("转移物资失败")
      }
      // console.log(res);
      this.$message.success("转移物资成功")
      this.medical="";
      this.getMyResource();
    },
    //获取用户列表
    async getUserList(){
        const res = await this.$http.get('user/list')
        // console.log(res);
        if(res.data.status !== 200) return this.$message.error("获取用户列表失败") 
        this.userList = res.data.data;
    },
    //获取资源列表
    async getMyResource(){
      const res = await this.$http.get('chain/myResources')
      // console.log(res);
      if(res.data.status!==200){
        console.log(res);
        return
        //  return this.$message.error("获取医疗物资列表失败")
      }
      this.medicalList = res.data.data
      // console.log(this.medicalList);
    },

    transferDialog(){
      const token = window.sessionStorage.getItem('token')
      if(!token) return this.$message.error('请先登录')
      this.dialogVisible = true;
    },
    //根据点击事件获取当前省份
    get_params(params){
      this.medicalForm.mask = 0;
      this.medicalForm.gauze = 0;
      this.medicalForm.cloth = 0;
      this.medicalForm.aid = 0;
      // this.name = params.name;
      this.getProvinceResource(params.name);
    },
    //根据当前省份获取资源信息
    async getProvinceResource(province){
      const res = await this.$http.get('chain/resources/province?province='+province)
      if(res.data.status !== 200){
        console.log(res);
        return this.$message.error("获取资源信息错误")
      }
      for(let item of res.data.data){
        if(item.category === '口罩'){
          this.medicalForm.mask += item.resourceNum;
        }
        else if(item.category === '防护服'){
          this.medicalForm.gauze += item.resourceNum;
      
        }
        else if(item.category === '创可贴'){
          this.medicalForm.cloth += item.resourceNum;
        }
        else if(item.category === '纱布'){
          this.medicalForm.aid += item.resourceNum;
        }
      }
      this.name = province;
    },
    //初始化地图
    initMap(){
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('main'));
      var that = this
      myChart.on('click',function(params){
        // methods[get_params()]
        that.get_params(params)
        // console.log(params.name);
      });
      
      this.pList = require('./data.json');
      // console.log(this.pList);
      let data = this.pList.map(r => {
        // console.log(r.provinceShortName);
          return {
              name: r.provinceShortName,
              value: [r.currentConfirmedCount,r.suspectedCount,r.confirmedCount,r.curedCount,r.deadCount]
          }
      })

      // 指定图表的配置项和数据
      var option = {
          visualMap:{
              dimension: 0,
              pieces: [
                  { gt:10000 ,label:'>10000',color:"#480F10"},
                  { gt:1000,lte:10000,label:'1000-10000',color:"#772526"},
                  { gt:500,lte:999,label:'1000-9999',color:"#BB3937"},
                  { gt:100,lte:499,label:'100-499',color:"#D56355"},
                  { gt:10,lte:99,label:'10-99',color:"#E9A188"},
                  { gt:0,lte:9,label:'0-9',color:"#FDEBCF"},
                  { value:0,color:'#FFFFFF',label:"0"}
              ]                   
          },
          title:{
              // text:'疫情地图',
              textStyle:{
                  fontSize: 30
              },
              left: "center",
              padding: 15
          },
          tooltip:{
              formatter(res) {
                  let {data,marker} = res

                  // console.log(data.data);
                  return `
                  ${data.name}<br>
                  当前确诊:${data.value[0]}<br>
                  当前疑似:${data.value[1]}<br>
                  总确诊:${data.value[2]}<br>
                  治愈:${data.value[3]}<br>
                  死亡:${data.value[4]}<br>
                  `
              }
          },
          series:[
              {
                  name:'疫情地图',
                  type:'map',
                  map:'china',
                  data
              }
          ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);

    },

  }
}
</script>

<style scoped>
.passwordLoginButton {
  background-color: #ffffff;
  border: none;
  outline-style: none;
  cursor: pointer;
  color: royalblue;
  margin-top: 25px;
  font-size: 8px;
}
.loginButton {
  width: 300px;
  height: 56px;
  background: #302fa6;
  font-size: 18px;
  color: #ffffff;
  border-radius: 28px !important;
  display: block;
  margin: 0 auto;
  margin-top: 50px;
  outline-style: none;
}
.userpassword {
  padding-left: 20px;
  width: 340px;
  height: 58px;
  background: #ffffff;
  box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, 0.05);
  border-radius: 20px;
  font-size: 18px;
}
.username {
  padding-left: 20px;
  width: 340px;
  height: 58px;
  background: #ffffff;
  box-shadow: 0px 5px 20px 0px rgba(0, 0, 0, 0.05);
  border-radius: 20px;
  font-size: 18px;
  /* margin-top: 40px; */
  margin-bottom: 20px;
}
.loginLogo {
  width: 112px;
  height: 58px;
  margin-top: 56px;
  
  /* margin-left: 230px; */
}
.loginDialog {
  display: flex;
  flex-direction: column;
  align-items: center;
  /* flex-direction:row-reverse */
}
.map{
  width: 600px;
  height:400px;
  /* background: #000000; */
}
.placeholder_bottom_include {
  width: 1200px;
  margin-top: 20px;
}
.placeholder_bottom {
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
  background-color: #ffffff;
  height: 80px;
}
.link_include {
  display: flex;
}
.link_include img {
  width: 30px;
  height: 30px;
}
.link {
  padding-top: 5px;
  padding-right: 10px;
  color: #302fa6;
}
.buttonList_container {
  box-sizing: border-box;
  padding-left: 28px;
  width: 1200px;
  display: flex;
  justify-content: space-between;
}
.topic_button {
  box-sizing: border-box;
  padding: 0;
  width: 100px;
  height: 44px;
  background-color: #dce2f1;
  color: #ffffff;
  border-radius: 8px;
}
.topic_button:hover {
  border: 2px solid #302fa6;
}

.map_bigContainer {
  width: 1200px;
  /* height: 700px; */
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}
.map_provideDetail {
  width: 460px;
  height: 331px;
  box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
  border-radius: 24px;
  overflow: hidden;
  box-sizing: border-box;
  padding: 30px 40px;
}
.map_provideDetail .el-header {
  padding: 0;
  width: 100%;
  height: 40px !important;
  display: flex;
  justify-content: space-between;
}
.map_provideDetail .el-main {
  width: 100%;
  padding: 0;
}
.map_provideDetail .el-main span {
  width: 100%;
  padding: 0;
  line-height: 28px;
}
.map_provideDetail .el-footer {
  padding: 0;
  width: 100%;
  height: 100px;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
  position: relative;
}
.map_provideDetail .el-footer span {
  position: absolute;
  bottom: 0;
}
.albumLayout {
  width: 1200px;
  margin-top: 20px;
}
.albumLayout_content {
  margin-top: 40px;
}
.albumLayout_content div {
  width: 285px;
  height: 190px;
  /* background: url('~@asset/image/img.png');
  background-position: center;
  background-repeat: no-repeat;
  background-size: 100%; */
  border-radius: 20px;
}
.albumLayout_content p {
  margin-top: 20px;
  margin-left: 20px;
}
/* 实现768px的响应式,媒介查询了解一下 */
@media only screen and (max-width: 768px) {
  .placeholder_bottom_include {
    width: 768px;
    margin: 0 auto;
  }
  .placeholder_bottom {
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    background-color: #ffffff;
    height: 100px;
    padding: 0 40px;
  }
  .title {
    height: 24px;
    font-size: 24px;
    font-family: AlibabaPuHuiTi-Medium, AlibabaPuHuiTi;
    font-weight: 500;
    color: #000000;
    line-height: 24px;
  }
  .link_include {
    display: flex;
  }
  .link_include img {
    width: 30px;
    height: 30px;
  }
  .link {
    padding-top: 5px;
    height: 18px;
    font-size: 18px;
    font-family: AlibabaPuHuiTi-Regular, AlibabaPuHuiTi;
    font-weight: 400;
    color: #302fa6;
    line-height: 18px;
  }
}
</style>