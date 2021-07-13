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
        <div class="back_des_title">搜索结果({{resourceList.length}}个医疗资源)</div>

      </div>
    </div>

    <!-- 图册列表展示 -->
    <div class="main">
      <el-table @row-click="routeToDetail" class="font-regular-18" :data="resourceList" style="width: 100%;cursor:pointer">
          <el-table-column prop="batchCode" label="订单号" width="180"> </el-table-column>
          <el-table-column prop="province"  label="所在省份"></el-table-column>
          <el-table-column prop="category"  label="类别"></el-table-column>
          <el-table-column prop="resourceName"  label="名称"></el-table-column>
          <el-table-column prop="resourceNum"  label="数量"></el-table-column>
          <el-table-column prop="isUsed"  label="是否被使用">
             <template slot-scope="scope">
                <el-tag v-if="scope.row.isUsed==0" type="success">未消耗</el-tag>
                <el-tag v-else-if="scope.row.isUsed==1" type="danger">已消耗</el-tag>
              </template>
          </el-table-column>
        </el-table>
    </div>

    <inner-footer></inner-footer>
  </div>

</template>

<script>
export default {
  data() {
    return {
      headFlag:false,
      resourceList:[],
      searchForm:{},
    }
  },
  methods: {
    //判断页头
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
    async getResourceList(){
      const res = await this.$http.get('chain/resources')
      if(res.data.status!==200){
        console.log(res);
        return this.$message.error("搜索失败")
      }
      // console.log(res.data.data);
      // this.$message.success("搜索成功")
      this.resourceList = res.data.data
      let params = this.$route.query
      // console.log(params);
      // console.log(this.resourceList);
      if(params.provinceName!==""){
        this.resourceList = this.resourceList.filter(item=>
          item.province === params.provinceName
        )
      }
      if(params.category!==""){
        this.resourceList = this.resourceList.filter(item=>
          item.category === params.category
        )
      }
      console.log(params);
      if(params.serachContext!==""){
        this.resourceList=this.resourceList.filter(item=>
          // 子字符串查询
          item.resourceName.indexOf(params.serachContext) !== -1
        )
      }
    }
  },
  created() {
    this.getHead();
    // console.log(this.$route.params);
    this.serachForm = this.$route.query;
    this.getResourceList();
    // console.log(this.searchForm);
  }
}
</script>
<!-- 删除了app.vue样式里的样式里的的text-align,margin;放缩功能在style里起作用  -->
<style scoped>
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
  width: 1220px;
  margin: 0 auto;
  background: #ffffff;
  color: #333;
}

.back {
  display: flex;
  padding-bottom: 80px;
  /* 写了吸顶之后还必须写padding-top */
  padding-top: 100px;
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
.albumLayout_content {
  margin-top: 40px;
}
.albumLayout_content div {
  width: 285px;
  height: 190px;
  border-radius: 20px;
}
.albumLayout_content p:nth-child(2) {
  margin-top: 20px;
  margin-left: 20px;
}
.albumLayout_content p:nth-child(3) {
  margin-top: 10px;
  margin-left: 20px;
}

</style>