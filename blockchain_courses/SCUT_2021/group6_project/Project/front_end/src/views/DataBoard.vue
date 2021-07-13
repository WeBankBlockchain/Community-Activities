<template>
  <div class="page">
    <!-- 导航栏 -->
    <Header v-if="!headFlag"></Header>
    <inner-header v-if="headFlag"></inner-header>

    <!--  数据看板标题和总数显示-->
    <div class="outline">
      <div class="outline-title">
        <p class="font-medium-32">数据统计</p>
      </div>
      <div class="outline-amount">
        <div class="outline-amount-left">
          <p class="font-medium-24">用户总量</p>
          <p>
            <span>{{userNum}}</span>
          </p>
        </div>
        <div class="line"></div>
        <div class="outline-amount-right">
          <p class="font-medium-24">资源总量</p>
          <p>
            <span>{{resourceNum}}</span>
          </p>
        </div>
      </div>
    </div>

    <!-- 这里放条形统计图 -->
    <!-- 图库动态折线图 -->
    <div class="main">
      <div class="trendMain">
        <span class="mainTitle font-medium-24">物资占比</span>
        <!-- <div class="trendTitle">
          <span class="font-regular-18">{{showTitle}}</span>
          <span class="el-icon-arrow-down title-icon"
                @click="showChoice=!showChoice"></span>
          <div class="select-con"
               v-show="showChoice">
            <div class="select-item font-regular-16"
                 v-for="(item,index) in selectTypes"
                 :key="index"
                 @click="handleSelect(index)">{{item}}</div>
          </div>
        </div> -->
        <div class="com-chart"
             id="main"></div>
      </div>
    </div>

    <!-- 专题占比饼图和top5分类与标签 -->
    <div class="container">
      <div class="container-pie">
        <p class="font-medium-24">用户占比</p>
        <div class="com-chart"
             id="pie"></div>
      </div>
      <div class="container-top">
        <div class="categoryRank">
          <p class="font-medium-24">TOP 5 省</p>
          <el-button type="pri"
                     v-for="(item,index) in topCategory"
                     :key="index"
                     class="font-regular-18"
                     @click="getRoleData(item)">{{item}}</el-button>
        </div>
        <!-- <div class="line2"></div>
        <div class="tagRank">
          <p class="font-medium-24">TOP 5 标签</p>
          <el-button type="pri"
                     v-for="(item,index) in topTag"
                     :key="index"
                     class="font-regular-18">{{item}}</el-button>
        </div> -->
      </div>
    </div>
    <inner-footer></inner-footer>
  </div>

</template>

<script>
import echarts from 'echarts'
export default {
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
      resourceNum:0,
      userNum:0,
      resourceNum:0,
      headFlag:false,
      showChoice: false, //是否显示选择框
      selectTypes: [`期间图像新增数量`, `期间图像访问数量`],
      currentSelectedType: 0, //当前组件的标题是选择框中的0或1
      allData: null, //通过getData获得的数据集
      pieData: null,
      topCategory: [
        //   top5分类
        '广东',
        '北京',
        '上海',
        '重庆',
        '台湾'
      ],
      roleData:[
        {name:"普通用户",value:0},
        {name:"生产商",value:0},
        {name:"医院",value:0},
      ],
      option:{
        tooltip: {
            trigger: 'item'
        },
        series: [
            {
                type: 'pie',
                radius: '50%',
                color: this.pieColor,
                data: [
                    {value: 3, name: '用户'},
                    {value: 4, name: '生产商'},
                    {value: 5, name: '医院'},
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
      },
    }
  },
  computed: {
    showTitle() {
      return this.selectTypes[this.currentSelectedType]
    }
  },
  methods: {
    //判断页头
    getHead(){
      const token = window.sessionStorage.getItem('token')
      if(!token) this.headFlag = false;
      else this.headFlag = true;
    },

    // 以下是饼图的初始化和更新流程
    initPieChart() {
      var chartDom = document.getElementById('pie');
      var myChart = echarts.init(chartDom);
      this.option.series[0].color = this.pieColor
      myChart.setOption(this.option);
    },
    
    async getUserList(){
      const res = await this.$http.get("user/list")
      if(res.data.status!==200){
        console.log(res);
        return this.$message.error('获取用户列表失败')
      }
      this.userNum = res.data.data.length
    },
    async getRCnt(){
      const res = await this.$http.get("user/roleCnt")
      if(res.data.status!==200){
        console.log(res);
        return this.$message.error('获取资源总数失败')
      }
      this.resourceNum = res.data.data.resource    
    },
    async getRoleData(province){
      this.roleData[0].value =0;
      this.roleData[1].value =0;
      this.roleData[2].value =0;
      const res = await this.$http.get('/user/list?province='+ province)
      if(res.data.status!==200){
        console.log(res);
        return this.$message.error('获取省份信息失败')
      }
      let temp = res.data.data
      for(let item of temp){
        if(item.role==="factory"){
          this.roleData[1].value+=1
          continue
        }
        if(item.role==="hospital"){
          this.roleData[2].value+=1
          continue
        }
        if(item.role==="user"){
          this.roleData[0].value+=1
        }
        
      }
      this.option.series[0].data = this.roleData
      this.option.series[0].color = this.pieColor
      this.initPieChart()
      console.log(this.roleData);
    }
  },
  created() {
    this.getHead();
    this.getUserList();
    this.getRCnt()
  },
  async mounted() {
    this.initPieChart()
    // this.getPieData()
    var app = {};

  var chartDom = document.getElementById('main');
  var myChart = echarts.init(chartDom);
  var option;

  var posList = [
      'left', 'right', 'top', 'bottom',
      'inside',
      'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
      'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
  ];

  app.configParameters = {
      rotate: {
          min: -90,
          max: 90
      },
      align: {
          options: {
              left: 'left',
              center: 'center',
              right: 'right'
          }
      },
      verticalAlign: {
          options: {
              top: 'top',
              middle: 'middle',
              bottom: 'bottom'
          }
      },
      position: {
          options: posList.reduce(function (map, pos) {
              map[pos] = pos;
              return map;
          }, {})
      },
      distance: {
          min: 0,
          max: 100
      }
  };

  app.config = {
      rotate: 90,
      align: 'left',
      verticalAlign: 'middle',
      position: 'insideBottom',
      distance: 15,
      onChange: function () {
          var labelOption = {
              normal: {
                  rotate: app.config.rotate,
                  align: app.config.align,
                  verticalAlign: app.config.verticalAlign,
                  position: app.config.position,
                  distance: app.config.distance
              }
          };
          myChart.setOption({
              series: [{
                  label: labelOption
              }, {
                  label: labelOption
              }, {
                  label: labelOption
              }, {
                  label: labelOption
              }]
          });
      }
  };


  var labelOption = {
      show: true,
      position: app.config.position,
      distance: app.config.distance,
      align: app.config.align,
      verticalAlign: app.config.verticalAlign,
      rotate: app.config.rotate,
      formatter: '{c}  {name|{a}}',
      fontSize: 16,
      rich: {
          name: {
          }
      }
  };

  option = {
      tooltip: {
          trigger: 'axis',
          axisPointer: {
              type: 'shadow'
          }
      },
      legend: {
          data: ['口罩', '纱布', '创可贴', '防护服']
      },
      toolbox: {
          show: true,
          orient: 'vertical',
          left: 'right',
          top: 'center',
          feature: {
              mark: {show: true},
              dataView: {show: true, readOnly: false},
              magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
              restore: {show: true},
              saveAsImage: {show: true}
          }
      },
      xAxis: [
          {
              type: 'category',
              axisTick: {show: false},
              //横坐标
              data: ['广东', '上海', '台湾', '重庆', '北京']
          }
      ],
      yAxis: [
          {
              type: 'value'
          }
      ],
      series: [
          {
              color:this.pieColor[0],
              name: '口罩',
              type: 'bar',
              barGap: 0,
              // label: labelOption,
              emphasis: {
                  focus: 'series'
              },
              data: [0, 0, 0, 0, 0]
          },
          {
              color:this.pieColor[1],
              name: '纱布',
              type: 'bar',
              // label: labelOption,
              emphasis: {
                  focus: 'series'
              },
              data: [0, 0, 0, 0, 0]
          },
          {
              color:this.pieColor[2],
              name: '创可贴',
              type: 'bar',
              // label: labelOption,
              emphasis: {
                  focus: 'series'
              },
              data: [0, 0, 0, 0, 0]
          },
          {
              color:this.pieColor[3],
              name: '防护服',
              type: 'bar',
              // label: labelOption,
              emphasis: {
                  focus: 'series'
              },
              data: [0, 0, 0, 0, 0]
          }
      ]
  };
  for(let item in option.xAxis[0].data){
    let res = await this.$http.get('chain/resources/province?province='+option.xAxis[0].data[item])
    if(res.data.status!==200){
      console.log(res);
      return this.$message.error("物资占比-获取信息失败")
    }
    // console.log(res);
    let temp = res.data.data
    for(let i of temp){
      if(i.category === '口罩'){
        option.series[0].data[item]+=i.resourceNum
      }
      if(i.category === '纱布'){
        option.series[1].data[item]+=i.resourceNum
      }
      if(i.category === '创可贴'){
        option.series[2].data[item]+=i.resourceNum
      }
      if(i.category === '防护服'){
        option.series[3].data[item]+=i.resourceNum
      }
    }
  }
  option && myChart.setOption(option);

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

.outline {
  padding-top: 100px;
  margin-top: 80px;
  display: flex;
  width: 100%;
  flex-direction: column;
  align-items: center;
  &-title {
    display: flex;
    flex-direction: column;
    align-items: center;
    p:nth-child(2) {
      margin-top: 20px;
      color: rgba(0, 0, 0, 0.4);
    }
    margin-bottom: 65px;
  }
  &-amount {
    box-sizing: border-box;
    padding: 60px 0;
    width: 1200px;
    height: 364px;
    background: #ffffff;
    box-shadow: 5px 5px 120px 0px rgba(0, 0, 0, 0.1);
    border-radius: 20px;
    display: flex;
    .line {
      width: 1px;
      height: 244px;
      background: rgba(0, 0, 0, 0.1);
    }
    &-left,
    &-right {
      padding-left: 60px;
      flex: 1 1 50%;
      p:nth-child(2) {
        margin-top: 80px;
        text-align: center;
        span:nth-child(1) {
          color: #302fa6;
          font-size: 100px;
          font-family: AlibabaPuHuiTi-Medium, AlibabaPuHuiTi;
          line-height: 100px;
        }
        span:nth-child(2) {
          color: rgba(0, 0, 0, 0.4);
          margin-left: 15px;
        }
      }
    }
  }
}

.main {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #333;
  margin-top: 40px;
  .trendMain {
    box-sizing: border-box;
    position: relative;
    width: 1200px;
    height: 700px;
    background: #ffffff;
    box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
    border-radius: 20px;
  }
  .mainTitle {
    position: absolute;
    left: 60px;
    top: 60px;
    z-index: 10;
    color: black;
  }
  .trendTitle {
    position: absolute;
    left: 180px;
    top: 60px;
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
}

.com-chart {
  width: 100%;
  height: 80%;
  margin-top:10%;
  // overflow: hidden;
}
.container {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 40px;
  &-top,
  &-pie {
    width: 590px;
    height: 514px;
    background: #ffffff;
    box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
    border-radius: 20px;
    box-sizing: border-box;
    padding: 60px 60px;
  }
  &-top {
    margin-left: 10px;
    display: flex;
    .categoryRank,
    .tagRank {
      box-sizing: border-box;
      flex: 1;
      display: flex;
      flex-direction: column;
      align-items: center;
      p {
        text-align: center;
        margin-bottom: 60px;
      }
      .el-button {
        width: fit-content;
        height: 38px;
        background: #df6c4f;
        border-radius: 19px;
        color: white;
        margin-left: 0;
        margin-top: 20px;
      }
      .el-button:nth-child(3) {
        background: #0091ff;
      }
      .el-button:nth-child(4) {
        background: #302fa6;
      }
      .el-button:nth-child(5) {
        background: #ffd500;
      }
    }
    .line2 {
      width: 1px;
      height: 414px;
      background: rgba(0, 0, 0, 0.1);
    }
  }
}
</style>