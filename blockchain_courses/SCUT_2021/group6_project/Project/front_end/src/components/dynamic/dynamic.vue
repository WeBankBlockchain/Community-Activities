<template>
  <div class="dynamic">
    <div class="title font-medium-24">统计数据</div>
    <div class="content">
      <div class="left">
        <div class="front">
          <div class="front-time">2021年07月</div>
        </div>
        <div class="statistics">
          <div class="sta-item">
            <div class="circle" style="color:#302FA6;borderColor:#302FA6;">{{data.factory}}</div>
            <div class="sta-text">生产商</div>
          </div>
          <div class="sta-item">
            <div class="circle" style="color:#FFD500;borderColor:#FFD500">{{data.user}}</div>
            <div class="sta-text">用户</div>
          </div>
          <div class="sta-item">
            <div class="circle" style="color:#DF6C4F;borderColor:#DF6C4F">{{data.hospital}}</div>
            <div class="sta-text">医院</div>
          </div>
          <div class="sta-item">
            <div class="circle" style="color:#0091FF;borderColor:#0091FF">{{data.resource}}</div>
            <div class="sta-text">医疗物资</div>
          </div>
        </div>
      </div>
      <div class="cross-line"></div>
      <div class="right">
        <DynamicEchart ref="dynamicEchart"></DynamicEchart>
      </div>
    </div>

  </div>
</template>

<script>
import DynamicEchart from './dynamic-echart.vue'

export default {
  name: 'dynamic',
  components: {
    DynamicEchart
  },
  data() {
    return {
      currentStaPage: 0,
      data:{},
    }
  },
  created(){
    //获取角色列表
    this.getDataList();
    //获取资源列表以统计各个类的医疗物资

  },
  methods: {
    //获取角色以及资源数
    async getDataList(){
      const res = await this.$http.get('/user/roleCnt')
      if(res.data.status!==200){
        console.log(res);
        return this.$message.error("获取角色及资源数量失败")
      }
      this.data = res.data.data;
    },
  }
}
</script>

<style scoped>
/* 占位css,自行处理 */
.dynamic {
  width: 1920px;
  height: 800px;
}
.title {
  margin-left: 360px;
}
.content {
  width: 1200px;
  height: 555px;
  box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
  margin-left: 360px;
  margin-top: 60px;
  display: flex;
  border-radius: 24px;
}
.left {
  width: 440px;
  height: 100%;
}
.front {
  display: flex;
  margin-top: 40px;
  margin-left: 40px;
  width: 440px;
  height: 40px;
  justify-items: center;
}
.front-time {
  font-size: 18px;
  font-family: AlibabaPuHuiTi-Regular, AlibabaPuHuiTi;
  font-weight: 400;
  color: rgba(0, 0, 0, 0.4);
  line-height: 18px;
  margin-left: 30px;
  margin-right: 192px;
}
.statistics {
  display: flex;
  flex-wrap: wrap;
}
.sta-item {
  width: 220px;
  height: 220px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
}
.circle:hover {
  width: 130px;
  height: 130px;
}
.circle {
  width: 120px;
  height: 120px;
  color: black;
  border: 5px solid black;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all;
  cursor: pointer;
}
.sta-text {
  /* width: 36px; */
  height: 18px;
  font-size: 18px;
  font-family: AlibabaPuHuiTi-Regular, AlibabaPuHuiTi;
  font-weight: 400;
  color: rgba(0, 0, 0, 0.4);
  line-height: 18px;
  margin-top: 20px;
}
.cross-line {
  width: 1px;
  height: 396px;
  background: rgba(0, 0, 0, 0.1);
  margin-top: 119px;
}
.right {
  width: 759px;
  height: 100%;
  position: relative;
}
</style>