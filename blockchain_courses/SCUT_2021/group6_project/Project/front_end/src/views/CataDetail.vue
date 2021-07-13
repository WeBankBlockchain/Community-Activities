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
        <div class="back_des_title">资源列表</div>
        <div>
          <div class="back_des_res">{{category}}</div>
        </div>
      </div>


    </div>

    <div class="main">
      <!-- 资源列表展示 -->
      <div class="waterFallImg">
        <el-table @row-click="routeToDetail" class="font-regular-18" :data="categoryList" style="width: 100%;cursor:pointer">
          <el-table-column prop="batchCode" label="批次号" width="180"> </el-table-column>
          <el-table-column prop="province"  label="所在省份"></el-table-column>
          <el-table-column prop="resourceName"  label="名称"></el-table-column>
          <el-table-column prop="resourceNum"  label="数量"></el-table-column>
          <el-table-column  label="是否被使用">
             <template slot-scope="scope">
                <el-tag v-if="scope.row.isUsed==0" type="success">未消耗</el-tag>
                <el-tag v-else-if="scope.row.isUsed==1" type="danger">已消耗</el-tag>
              </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 侧边栏展示 -->

    </div>

    <inner-footer></inner-footer>
  </div>

</template>

<script>
export default {

  data() {
    return {
      category:'',
      headFlag:false,

      //资源列表
      categoryList:[],
    }
  },
  methods: {
    getHead(){
      const token = window.sessionStorage.getItem('token')
      if(!token) this.headFlag = false;
      else this.headFlag = true;
    },
    routeToDetail(row){
      // console.log(row);
      this.$router.push({
        name: 'ImageDetail',
        query: {
          resourceId: row.resourceId,
          resourceName: row.resourceName,
          category: row.category,
        },
      })
    },
    goback() {
      this.$router.go(-1)
    },
    async getCategoryList(){
      const res = await this.$http.get('chain/resources/category?category=' + this.category)
      if(res.data.status!==200){
        console.log(res);
        return this.$message.error("获取资源列表失败")
      }
      this.categoryList = res.data.data;
    },
  },
  created() {
    this.category = this.$route.query.name
    console.log(this.category);
    this.getCategoryList();
    this.getHead();
  }
}
</script>
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
  // height:1200px;
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

.waterFallImg {
  width: 840px;
  margin-left: 360px;
}


.p-title {
  font-size: 24px;
  font-family: AlibabaPuHuiTi-Medium, AlibabaPuHuiTi;
  font-weight: 500;
  color: #000000;
  line-height: 24px;
  margin-bottom: 30px;
}
.btnList {
  display: flex;
  flex-wrap: wrap;
  .btnItem {
    width: 95px;
    height: 38px;
    margin: 0 10px 10px 10px;
    //   background: #df6c4f;
    //   border-radius: 19px;
    //   color: white;
  }
}
.knowledgGraph {
  width: 1200px;
  margin: 100px 0 60px 360px;
}
.knowGraph_List {
  width: 100%;
  display: flex;
  //   align-items: center;
  justify-content: center;
}
</style>