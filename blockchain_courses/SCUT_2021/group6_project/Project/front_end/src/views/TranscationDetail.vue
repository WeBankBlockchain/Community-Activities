<template>
  <div class="page">
    <!-- 导航栏 -->
    <Header v-if="!headFlag"></Header>
    <inner-header v-if="headFlag"></inner-header>

    <!-- 返回一栏 -->
    <div class="back">
      <img src="@asset/image/icon_back.png"
           style="cursor:pointer"
           @click="goback()">
      <div class="back_des">
        <!-- <div class="back_des_title">{{albumMsg.name}}/{{imgData.name}}</div> -->
        <div>
          <div class="back_des_res">溯源信息</div>
        </div>
      </div>
    </div>

    <div class="main">
      <!-- 图像列表展示 -->
      <div class="bigImg">
        <el-table class="font-regular-18" :data="transcationList" style="width: 100%;cursor:pointer">
          <el-table-column prop="from.name" label="起始地" width="180"></el-table-column>
          <el-table-column prop="from.province"  label="起始省份"></el-table-column>
          <el-table-column   label="事件">
            <template slot-scope="scope">
                <el-tag v-if="scope.row.info=='创建物资' " type="success">创建物资</el-tag>
                <el-tag v-else-if="scope.row.to.name=='god'" type="danger">消耗物资</el-tag>
                <el-tag v-else >转移物资</el-tag>
              </template>
          </el-table-column>
          <el-table-column prop="to.name"  label="目的地"></el-table-column>
          <el-table-column prop="to.province"  label="目的省份"></el-table-column>
          <el-table-column prop="time"  label="时间"></el-table-column>
        </el-table>
      </div>
      <!-- 侧边栏展示 -->
      <div class="siderBar"
           ref="sideBar">
        <div class="siderBar-top font-regular-18">
          <el-container>
            <el-header class="container-header">
              <!-- 创建者 -->
              <p>生产单位:{{createForm.authorName}}</p>
            </el-header>
            <!-- 创建于 -->
            <el-main>
              <p>生产于:</p>
                <p>{{createForm.createTime}}</p>
            </el-main>
            <el-footer>
              <p >生产地:{{createForm.createPlace}}</p>
            </el-footer>
          </el-container>
        </div>

        <div class="siderBar-bottom">
          <el-container>
            <el-header>
              <p class="p-title">资源名称</p>
              <p class="font-regular-18">{{resourceName}}</p>
            </el-header>
            <el-footer>
              <p class="p-title">资源类型</p>
              <p class="font-regular-18">{{category}}</p>
            </el-footer>
          </el-container>
        </div>
      </div>
    </div>
    <inner-footer></inner-footer>
  </div>

</template>

<script>
export default {
  components: {},
  data() {
    return {
      //获取图册创建者信息 可能包含在albumMsg中....待定
      createForm:{
        authorName:"",
        createTime:"",
        createPlace:"",
      },
      resourceId:"",
      resourceName:"",
      category:"",
      transcationList:[],
      headFlag:false,
    }
  },
  methods: {
    getHead(){
      const token = window.sessionStorage.getItem('token')
      if(!token) this.headFlag = false;
      else this.headFlag = true;
    },
    goback() {
      this.$router.go(-1)
    },
    async getTransactions(){
      const res = await this.$http.get('chain/transactions?resourceId='+this.resourceId)
      if(res.data.status!==200){
        console.log(res);
        return this.$message.error("获取溯源信息失败")
      }
      console.log(res);
      // 获取创建者名称,创建时间,创建地点
      this.createForm.authorName = res.data.data[0].from.name;
      this.createForm.createTime = res.data.data[0].time;
      this.createForm.createPlace = res.data.data[0].from.province;
      this.transcationList = res.data.data;
    },
  },

  created() {
    this.getHead()
    this.resourceId = this.$route.query.resourceId
    this.resourceName = this.$route.query.resourceName
    this.category = this.$route.query.category
    console.log(this.$route.query);
    //根据resourceId获取溯源信息
    this.getTransactions();
  }
}
</script>
<!-- 删除了app.vue样式里的样式里的的text-align,margin;放缩功能在style里起作用  -->
<style lang="less" scoped>
template {
  margin: 0;
  padding: 0;
}
html,
body {
  width: 100%;
  height: 100%;
}
.page {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}
.main {
  width: 100%;
  display: flex;
}
.back {
  display: flex;
  width: 1920px;
  padding-bottom: 80px;
  /* 写了吸顶之后还必须写padding-top */
  padding-top: 100px;
  position: relative;
}
.back img {
  width: 72px;
  height: 72px;
  z-index: 50;
  /* 在back里写了padding-top还要在以下两个地方分别写 */
  padding-top: 100px;
  padding-left: 360px;
}
.back_des {
  padding-top: 105px;
  padding-left: 30px;
}

.back_des_title {
  height: 24px;
  font-size: 24px;
  font-family: AlibabaPuHuiTi-Medium, AlibabaPuHuiTi;
  font-weight: 500;
  color: #000000;
  line-height: 24px;
}
.back_des_res {
  display: inline-block;
  margin-right: 21px;
  margin-top: 19px;
  height: 18px;
  font-size: 18px;
  font-family: AlibabaPuHuiTi-Regular, AlibabaPuHuiTi;
  font-weight: 400;
  color: rgba(0, 0, 0, 0.4);
  line-height: 18px;
}
.btn-pri {
  position: absolute;
  top: 208px;
  right: 720px;
  width: 300px;
  height: 56px;
  background: #302fa6;
  border-radius: 28px;
  color: white;
}

.siderBar {
  margin-left: 60px;
  margin-top: -150px;
  &-top {
    width: 300px;
    background: #ffffff;
    box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
    border-radius: 20px;

    .el-container {
      .el-header {
        height: fit-content !important;
        box-sizing: content-box;
        margin: 10px 30px;
        padding: 20px 0px;
        display: flex;
        align-items: center;
        border-bottom: 1px solid rgba(0, 0, 0, 0.1);
        img {
          width: 80px;
          height: 80px;
          border-radius: 50%;
        }
        span {
          margin-left: 30px;
        }
      }
      .el-main {
        height: fit-content !important;
        box-sizing: content-box;
        margin: 10px 30px;
        padding: 10px 0px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.1);
        p {
          margin-bottom: 15px;
        }
      }
      .el-footer {
        height: fit-content !important;
        box-sizing: content-box;
        margin: 10px 30px;
        padding: 10px 0px;
      }
    }
  }
  &-bottom {
    margin-top: 30px;
    width: 300px;
    // height: 903px;
    background: #ffffff;
    box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
    border-radius: 20px;
    .el-container {
      .el-header {
        height: fit-content !important;
        box-sizing: content-box;
        margin: 10px 30px;
        padding: 20px 0px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.1);
        p:nth-child(2) {
          margin-bottom: 10px;
        }
      }
      .el-main {
        height: fit-content !important;
        box-sizing: content-box;
        margin: 10px 30px;
        padding: 10px 0px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.1);
        p:nth-child(2) {
          margin-bottom: 10px;
        }
      }
      .el-footer {
        height: fit-content !important;
        box-sizing: content-box;
        margin: 10px 30px;
        padding: 10px 0px;
        p:nth-child(2) {
          margin-bottom: 15px;
        }
      }
    }
  }
}

.p-title {
  font-size: 24px;
  font-family: AlibabaPuHuiTi-Medium, AlibabaPuHuiTi;
  font-weight: 500;
  color: #000000;
  line-height: 24px;
  margin-bottom: 30px;
}

.bigImg {
  width: 840px;
  height: 630px;
  margin-left: 360px;
  // box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
  img {
    width: 100%;
    height: 100%;
    object-fit: scale-down;
    overflow: hidden;
    border-radius: 20px;
  }
}
</style>