<template>
  <div style="height:100%">
    <div class="main">
      <div class="table-item1">
        <div class="title">医疗物资统计</div>
        <div id="topic-echart"
             style="width: 330px;height:250px;"></div>
      </div>
      <div class="table-item2">
        <div class="title">角色统计</div>
        <div id="works-echart"
             style="width: 330px;height:250px;"></div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'dynamic',
  components: {},
  data() {
    return {
      pieColor: [
        '#FFD500',
        '#DF6C4F',
        '#0091FF',
        '#302FA6',
        '#FFD500',
        '#DF6C4F',
        '#0091FF',
        '#302FA6',
        '#FFD500',
        '#DF6C4F',
        '#0091FF',
        '#302FA6'
      ],
      topicData: [],
      workData: []
    }
  },
  async mounted() {
    var dom = document.getElementById('topic-echart')
    //获取角色信息的统计数据
    const res = await this.$http.get('user/roleCnt')
    if(res.data.status!==200){
      console.log(res);
      return this.$error("数据统计-获取角色数量失败")
    }
    let work = res.data.data
    for(let item in work){
      if(item === 'factory') {var tem = "工厂"}
      if(item === 'hospital') {var tem = "医院"}
      if(item === 'user') {var tem = "用户"}
      this.workData.push({name:tem,value:work[item]})
    }
    this.workData.length-=1

    //获取医疗物资的数据
    const res1 = await this.$http.get('chain/resources')
    let topic = res1.data.data
    let flag = false;
    console.log(topic);
    for(let item of topic){
      flag = false;
      for(let item1 of this.topicData){
        if(item1.name === item.category){
          item1.value+=item.resourceNum
          flag = true;
          break;
        }
      }
      if(flag === false){
        this.topicData.push({name:item.category,value:item.resourceNum})
      }
    }
    console.log(this.topicData);
    var myChart = this.echarts.init(dom)
    // 绘制饼图1
    myChart.setOption({
      tooltip: {
        trigger: 'item',
        formatter: '{b}<br/>{d}%',
        backgroundColor: '#FFFFFF',
        textStyle: {
          color: '#302FA6',
          fontSize: '14px',
          fontFamily: 'AlibabaPuHuiTi-Regular, AlibabaPuHuiTi',
          fontWeight: '400'
        }
      },
      series: [
        {
          type: 'pie',
          radius: '92%',
          color: this.pieColor,
          label: {
            show: false
          },
          data: this.topicData
        }
      ]
    })
    var dom = document.getElementById('works-echart')

    var myChart = this.echarts.init(dom)
    // 绘制饼图2
    myChart.setOption({
      tooltip: {
        trigger: 'item',
        formatter: '{b}<br/>{d}%',
        backgroundColor: '#FFFFFF',
        textStyle: {
          color: '#302FA6',
          fontSize: '14px',
          fontFamily: 'AlibabaPuHuiTi-Regular, AlibabaPuHuiTi',
          fontWeight: '400'
        }
      },
      series: [
        {
          type: 'pie',
          radius: '92%',
          color: this.pieColor,
          label: {
            show: false
          },
          data: this.workData
        }
      ]
    })

  },
  methods: {
  }
}
</script>

<style scoped>
.main {
  display: flex;
}
.table-item1 {
  margin-top: 80px;
  margin-left: 60px;
}
.table-item2 {
  margin-top: 80px;
  margin-left: 30px;
}
.title {
  font-size: 18px;
  font-family: AlibabaPuHuiTi-Regular, AlibabaPuHuiTi;
  font-weight: 400;
  color: black;
  margin-bottom: 75px;
}

.trendMain {
  /* position: relative;
  width: 100%; */
  height: 100%;
}

.mytitle {
  position: absolute;
  left: 60px;
  top: 80px;
  z-index: 10;
  color: black;
}
.title-icon {
  margin-left: 20px;
  cursor: pointer;
}
.select-con {
  width: 184px;
  height: 128px;
  background: #ffffff;
  box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  /* justify-content: center; */
}
.select-item {
  margin-top: 30px;
  cursor: pointer;
  transition: all;
}
.select-item:hover {
  font-weight: 600;
}
.com-chart {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
</style>