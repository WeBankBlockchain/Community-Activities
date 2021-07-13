<template>
  <div>
    <div id="long"></div>
    <!-- <div class="right">youbian</div> -->
    <div id="container">
      <!-- 头部 -->
      <Header v-if="!headFlag"></Header>
      <inner-header v-if="headFlag"></inner-header>

      <!-- 轮播图 -->
      <swiper
        class="swiper_pic"
        id="home_pic"
        ref="mySwiper" 
        :options="swiperOptions"
      >
        <swiper-slide>
          <img src="@/assets/image/home/home1.jpg" class='homepic' alt="">
        </swiper-slide>
        <swiper-slide>
          <img  src="@/assets/image/home/home2.jpg" class='homepic' alt="">
        </swiper-slide>
        <swiper-slide>
          <img  src="@/assets/image/home/home3.jpg" class='homepic' alt="">
        </swiper-slide>
      </swiper>
      
      
      <!-- 搜索 -->
      <div id="searchModule"
           @click="getSearchInputFocus">
        <div class="search"
             id="search">
          <img class="ic_search"
               src="@/assets/image/ic_search.png"
               alt="">
          <input class="input"
                 placeholder="搜索物资"
                 prefix-icon="el-icon-search"
                 suffix-icon="el-icon-right"
                 v-model="serachContext">
          <button class="icgoButton"
                  @click="clickSeachButton"> <img class="ic_go"
                 src="@/assets/image/ic_go.png"
                 alt=""> </button>
        </div>
        <el-container v-show="isShowHotSearch"
                      class="hotSearch_container font-regular-18"
                      id="hotSearch">
          <el-row v-for="(data,index) in labelsList"
                  :key="index"
                  class="hotSearch_rows">
            <span style="width:53px;height:18px;">{{data.name}}</span>
            <span class="hotSearch_labelsRow">
              <span v-for="(item,j) in data.labelList"
                           :key="j"
                           :to="{}"
                           :title="item.name"
                           :class="[item.selected? 'route_labels_selected':'route_labels']"
                           @click="selectHotSearchLabel(index,j)">
                <span v-if="j<7">{{item.name}}</span>
              </span>
            </span>
            <i v-show="data.labelList.length>7&&data.iconArrow=='down'"
               class="el-icon-arrow-down"
               @click="clickMoreHotLabels(index)"></i>
            <!-- <div v-show="data.showMoreLabels"></div> -->
            <span v-show="data.showMoreLabels"
                  class="newHotSearch_labelsRow">
              <span v-for="(item,j) in data.labelList"
                           :key="j"
                           :to="{}"
                           :title="item.name"
                           :class="[item.selected? 'route_labels_selected':'route_labels']"
                           @click="selectHotSearchLabel(index,j)">
                <span v-if="j>=7">{{item.name}}</span>
              </span>
            </span>
            <i v-show="data.iconArrow=='up'"
               class="el-icon-arrow-up"
               @click="clickLessHotLabels(index)"></i>
          </el-row>
        </el-container>
      </div>
    </div>

  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'vue-awesome-swiper';
import "swiper/css/swiper.css";
export default {
  components: {
    Swiper,
    SwiperSlide,
  },
  name: 'v-login',
  data() {
    return {
      serachContext:"",
      // 控制头部显示
      headFlag:false,
      
      swiperOptions: {

        loop: true,
        //切换速度
        speed: 3000,
        autoplay: {
          delay: 2000,
          stopOnLastSlide: false,
          disableOnInteraction: false,
        },
      },

      //显示热搜
      isShowHotSearch: false,
      labelsList: [
        {
          name: '类别:',
          labelList: [
            { name: '口罩', selected: false },
            { name: '纱布', selected: false },
            { name: '创可贴', selected: false },
            { name: '防护服', selected: false },
            { name: '呼吸机', selected: false },
            { name: '红外体温计', selected: false },
            { name: '洗手液', selected: false },
            { name: 'COVID-19检测试剂', selected: false },
          ],
          showMoreLabels: false,
          iconArrow: 'down'
        },
        
        {
          name: '省份:',
          labelList: [
            { name: '台湾', selected: false },
            { name: '广东', selected: false },
            { name: '香港', selected: false },
            { name: '福建', selected: false },
            { name: '云南', selected: false },
            { name: '浙江', selected: false },
            { name: '四川', selected: false },
            { name: '上海', selected: false },
            { name: '北京', selected: false },
            { name: '湖南', selected: false },
            { name: '江苏', selected: false },
            { name: '陕西', selected: false },
            { name: '天津', selected: false },
            { name: '河南', selected: false },
            { name: '安徽', selected: false },
            { name: '澳门', selected: false },
            { name: '湖北', selected: false },
            { name: '辽宁', selected: false },
            { name: '内蒙古', selected: false },
            { name: '重庆', selected: false },
            { name: '山西', selected: false },
            { name: '甘肃', selected: false },
            { name: '海南', selected: false },
            { name: '宁夏', selected: false },
            { name: '黑龙江', selected: false },
            { name: '河北', selected: false },
            { name: '新疆', selected: false },
            { name: '江西', selected: false },
            { name: '山东', selected: false },
            { name: '吉林', selected: false },
            { name: '广西', selected: false },
            { name: '贵州', selected: false },
            { name: '青海', selected: false },
            { name: '西藏', selected: false },
          ],
          showMoreLabels: false,
          iconArrow: 'down'
        },
      ],
      provinceName:"",
      category:"",
    }
  },
  created(){
    this.getHead();
  },
  methods: {
    selectHotSearchLabel(index, subIndex) {
      // console.log(index,subIndex);
      if(this.labelsList[index].labelList[subIndex].selected){
        this.labelsList[index].labelList[subIndex].selected = !this.labelsList[index].labelList[subIndex].selected
        if(index === 1) this.provinceName = ""
        if(index === 0) this.category = ""
      }
      else{
        this.labelsList[index].labelList.map(x => { {x.name,x.selected = false} })
        this.labelsList[index].labelList[subIndex].selected = !this.labelsList[index].labelList[subIndex].selected
        if(index === 1) this.provinceName = this.labelsList[index].labelList[subIndex].name
        if(index === 0) this.category = this.labelsList[index].labelList[subIndex].name
      }
      console.log(this.provinceName,this.category,this.serachContext);

    },
    //判断页头
    getHead(){
      const token = window.sessionStorage.getItem('token')
      if(!token) this.headFlag = false;
      else this.headFlag = true;
    },

    onSwiper(swiper) {
      console.log(swiper);
    },
    onSlideChange() {
      console.log('slide change');
    },
    getSearchInputFocus: function () {
      document.getElementById('long').classList = 'long_active_in'
      document.getElementById('search').classList = 'search_active_in'
      // document.getElementById('upload').style = 'display:none;'
      document.getElementById('home_pic').style = 'display:none;'
      this.timer = setTimeout(() => {
        this.isShowHotSearch = true
      }, 600)
    },
    clickMoreHotLabels: function (index) {
      //点击搜索热词的下拉按钮：
      //   console.log('aha')
      //   console.log(this.labelsList[index].showMoreLabels)
      this.labelsList[index].showMoreLabels = true
      this.labelsList[index].iconArrow = 'up'
      let newHeight =
        Math.floor(this.labelsList[index].labelList.length / 8) * 40 + 40
      document.getElementsByClassName('hotSearch_rows')[
        index
      ].style = `height:${newHeight}px;`
    },
    clickLessHotLabels: function (index) {
      //点击搜索热词的上滑按钮：
      this.labelsList[index].showMoreLabels = false
      this.labelsList[index].iconArrow = 'down'
      document.getElementsByClassName('hotSearch_rows')[index].style = ''
    },
    //搜索函数
    clickSeachButton() {
      this.$router.push({
        name: 'Search',
        query: {
          provinceName: this.provinceName,
          category: this.category,
          serachContext: this.serachContext,
        },
       
      })
    },

  },
  mounted() {
  },
  destroyed() {
    clearTimeout(this.timer)
    clearTimeout(this.timer2)
  }
}
</script>
<style  scoped>
#searchModule{
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
#container{
  width: 100%;
  height: 1200px;
  /* display: flex; */
  /* align-items: center; */
  /* flex-direction:row-reverse */
}

.swiper_pic{
  margin-top: 136px;
  width:1200px;
}
.swiper_pic1{
  width:1200px;
  margin-top:150px;
}
.homepic{
  height: 600px;
  width: 1200px;
  margin-top:40px;
  /* margin-left:180px; */
  /* border:2px solid; */
  border-radius:25px;
}

.right {
  width: 768px;
  height: 1080px;
  background: #302fa6;
  margin-left: 1152px;
  position: absolute;
  margin-top: -73px;
}

.logo {
  height: 58px;
  width: 112px;
}

.loginJpg {
  height: 45px;
  width: 45px;
}

.long_active_in {
  float: right;
  position: absolute;
  margin-left: 574px;
  margin-top: -100px;
  height: 196px;
  width: 2px;
  /* background: #f4f4f4; */
  transition: all 0.6s linear;
}
@keyframes rotateCamera {
  0% {
    width: 64px;
    height: 64px;
    top: 141px;
    left: 544px;
    transform: rotate(0);
    background: white;
  }
  100% {
    width: 342px;
    height: 342px;
    top: 293px;
    transform: rotate(360);
  }
}
.camera_active_in {
  position: absolute;
  width: 64px;
  height: 64px;
  background: white;
  left: 544px;
  top: 168px;
  /* border: 2px solid #f4f4f4; */
  border-radius: 50%;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.6s linear;
  /* transform: rotate(360deg); */
}
.camera_active_in .cameraJpg {
  width: 30px;
  height: 22px;
  transform: rotate(360deg);
  transition: all 0.6s linear;
}

.cameraJpg {
  width: 195px;
  height: 160px;
  transition: all 0.6s linear;
}
.search {
  position: absolute;
  width: 800px;
  height: 100px;
  border: none;
  box-shadow: 2px 2px 10px 10px rgba(31, 30, 30, 0.05) !important;
  border-radius: 20px;
  top: 800px;
  /* left: 80px; */
  font-size: 18px;
  display: flex;
  align-items: center;
  transition: all linear 0.6s;
}
.search_active_in {
  position: absolute;
  width: 800px;
  height: 100px;
  border: none;
  box-shadow: 2px 2px 10px 10px rgba(31, 30, 30, 0.05) !important;
  border-radius: 20px;
  top: 322px;
  left: 180px;
  font-size: 18px;
  display: flex;
  align-items: center;
  transition: all linear 0.6s;
}
.ic_search {
  width: 36px;
  height: 36px;
  margin-left: 36px;
}
.ic_go {
  width: 36px;
  height: 36px;
}
.icgoButton {
  background-color: white;
  border: 0px;
  outline-style: none;
  cursor: pointer;
}
.input {
  width: 650px;
  border: none;
  outline-style: none;
  font-size: 20px;
}

.text {
  font-size: 16px;
  color: rgba(0, 0, 0, 0.4);
  margin: 0 auto;
  text-align: center;
  margin-top: 2px;
}
.qrCode {
  width: 200px;
  height: 200px;
  margin: 0 auto;
  text-align: center;
  margin-top: 24px;
}
.qrCode img {
  width: 100%;
  height: 100%;
}



.el-container {
  position: absolute;
  top: 490px;
  left: 180px;

  width: 800px;
  display: flex;
  flex-direction: column;
}
.hotSearch_rows {
  height: 55px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  transition: all 0.2s linear;
}
.hotSearch_labelsRow {
  margin: 15px 0;
  position: relative;
}
.route_labels {
  text-decoration: none;
  color: rgba(0, 0, 0, 0.4);
}
.route_labels span {
  display: inline-block;
  width: 73px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-left: 20px;
}
.route_labels_selected span {
  display: inline-block;
  width: 73px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-left: 20px;
  cursor: default;
}
.route_labels_selected {
  color: #302fa6;
}
.route_labels:hover {
  color: #302fa6;
}
.el-icon-arrow-down {
  position: absolute;
  right: 0;
}
.el-icon-arrow-up {
  position: absolute;
  bottom: 20px;
  right: 0;
}
.newHotSearch_labelsRow {
  margin-left: 53px;
  max-width: 720px;
  position: relative;
  animation: newLabelsRowDisplay 0.2s linear;
}
@keyframes newLabelsRowDisplay {
  0% {
    transform: translateY(-50%);
  }
  100% {
    transform: translateY(0);
  }
}
.newHotSearch_labelsRow span {
  margin-bottom: 15px;
}

.em{
  width:20px;
}
.cd{
  width:20px;
}
</style>


