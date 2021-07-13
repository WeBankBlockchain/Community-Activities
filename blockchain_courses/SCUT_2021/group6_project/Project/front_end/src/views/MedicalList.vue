<template>
  <div class="page">
    <!-- 导航栏 -->
    <Header v-if="!headFlag"></Header>
    <inner-header v-if="headFlag"></inner-header>

    <!-- 搜索框 -->
    <div class="search-container">
      <div class=search-prefix
           @click="getSearchInputFocus">
          <span class="el-dropdown-link">医疗物资</span>
      </div>
      <div class="search"
           id="search"
           @click="getSearchInputFocus">
        <img class="ic_search"
             src="@asset/image/ic_search.png"
             alt="">
        <input class="input"
               placeholder="搜索医疗物资"
               prefix-icon="el-icon-search"
               suffix-icon="el-icon-right"
               v-model="serachContext" >
        <button class="icgoButton"
                @click="clickSeachButton"> <img class="ic_go"
               src="@asset/image/ic_go.png"
               alt=""> </button>
      </div>
    </div>


    <!-- 搜索框点击后下拉框 -->
    <div class="hotSearch"
         id="hotSearch"
         @click="getSearchInputFocus">
      <el-container v-show="isShowHotSearch"
                    class="hotSearch_container font-regular-18">
        <span class="el-icon-close"
              @click="loseSearchInputFocus"></span>
        <el-row v-for="(data,index) in labelsList"
                :key="index"
                class="hotSearch_rows">
          <span class="hotSearch_title">{{data.name}}</span>
          <span class="hotSearch_labelsRow">
            <span v-for="(item,j) in data.labelList"
                  :key="j"
                  :to="{}"
                  :title="item.name"
                  :class="[item.selected? 'route_labels_selected':'route_labels']"
                  @click="selectHotSearchLabel(index,j)">
              <span>{{item.name}}</span>
            </span>
          </span>
        </el-row>
      </el-container>
    </div>

    <!-- 图册列表展示 -->
    <div class="main">
      <el-row :gutter="20"
              class="albumLayout">
        <el-col :span="6"
                v-for="(data,index) in albumList"
                :key="index">
          <div class="albumLayout_content">
            <div>
              <!-- <img :src="data.coverImg" alt=""> -->
              <albumbox :value1="data.coverImg1"
                        :value2="data.coverImg2"
                        :value3="data.coverImg3"
                        :name="data.name"></albumbox>
            </div>
            <p class="font-regular-18">{{data.name}}</p>
          </div>
        </el-col>
      </el-row>
    </div>

    <inner-footer></inner-footer>
  </div>

</template>

<script>
export default {
  data() {
    return {
      serachContext:"",
      headFlag:false,
      // 图册列表数据
      albumList: [
        {
          name: '口罩',
          coverImg1: require('@asset/image/albums/口罩/img1.jpg'),
          coverImg2: require('@asset/image/albums/口罩/img2.jpg'),
          coverImg3: require('@asset/image/albums/口罩/img3.jpg')
        },
        {
          name: '纱布',
          coverImg1: require('@asset/image/albums/纱布/img1.jpg'),
          coverImg2: require('@asset/image/albums/纱布/img2.jpg'),
          coverImg3: require('@asset/image/albums/纱布/img3.jpg')
        },
        {
          name: '创可贴',
          coverImg1: require('@asset/image/albums/创可贴/img1.jpg'),
        },
        {
          name: '防护服',
          coverImg1: require('@asset/image/albums/防护服/img1.jpg')
        },
                {
          name: '呼吸机',
          coverImg1: require('@asset/image/albums/呼吸机/img1.jpg')
        },
        {
          name: '红外体温计',
          coverImg1: require('@asset/image/albums/红外体温计/img1.jpg')
        },
        {
          name: '洗手液',
          coverImg1: require('@asset/image/albums/洗手液/img1.jpg')
        },
        {
          name: 'COVID-19检测试剂',
          coverImg1: require('@asset/image/albums/COVID-19检测试剂/img1.jpg')
        },
      ],
      //是否显示热搜
      isShowHotSearch: false,
      //   热搜数据集
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
          showMoreLabels: false
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
          iconArrow: 'down',
          
        },
      ],
      provinceName:'',
      category:'',
    }
  },
  methods: {
    //判断页头
    getHead(){
      const token = window.sessionStorage.getItem('token')
      if(!token) this.headFlag = false;
      else this.headFlag = true;
    },
    getSearchInputFocus: function (event) {
      this.isShowHotSearch = true
    },
    loseSearchInputFocus: function (event) {
      this.isShowHotSearch = false
      event.stopPropagation()
    },
    //选择模糊搜索函数
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

    handleSearchPrefix(newValue) {
      this.searchPrefix = newValue
    }
  },
  created() {
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
.search-container {
  padding-top: 100px;
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 80px;
  margin-bottom: 60px;
  .search-prefix {
    width: 200px;
    height: 100px;
    background: #302fa6;
    border-radius: 20px 0px 0px 20px;
    text-align: center;
    font-family: AlibabaPuHuiTi-Regular, AlibabaPuHuiTi;
    font-weight: 400;
    line-height: 100px;
    span {
      font-size: 18px;
      color: white;
    }
    .el-icon--right {
      margin-left: 30px;
      font-size: 18px;
    }
  }
  .search {
    width: 800px;
    height: 100px;
    border: none;
    box-shadow: 2px 2px 10px 10px rgba(31, 30, 30, 0.05) !important;
    border-radius: 0 20px 20px 0;
    font-size: 18px;
    display: flex;
    align-items: center;
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
}

.main {
  width: 1220px;
  margin: 0 auto;
  background: #ffffff;
  color: #333;
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
}

.el-dropdown {
  cursor: pointer;
  &-menu {
    top: 250px !important;
  }
}
.hotSearch {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  .el-container {
    width: 1200px;
    box-sizing: border-box;
    padding: 20px 30px;
    display: flex;
    flex-direction: column;
    background: #ffffff;
    box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
    border-radius: 20px;
    position: relative;
    .el-icon-close {
      position: absolute;
      top: 20px;
      right: 30px;
      font-size: 30px;
      color: rgba(0, 0, 0);
      cursor: pointer;
      z-index: 10;
    }
  }

  .hotSearch_rows {
    width: 100%;
    padding-bottom: 20px;
    //   border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    display: flex;
  }
  .hotSearch_rows:not(:last-child) {
    width: 100%;
    padding-bottom: 20px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    display: flex;
  }
  .hotSearch_title {
    margin-top: 20px;
    width: 53px;
    height: 18px;
  }
  .hotSearch_labelsRow {
    flex: 1;
    flex-wrap: wrap;
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
    margin-top: 20px;
    cursor: default;
  }
  .route_labels_selected span {
    display: inline-block;
    width: 73px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    margin-left: 20px;
    margin-top: 20px;
    cursor: default;
  }
  .route_labels_selected {
    color: #302fa6;
  }
  .route_labels:hover {
    color: #302fa6;
  }
}
</style>