<template>
  <div class="page">
    <!-- 导航栏 -->
    <inner-header></inner-header>

    <!-- 主要内容 -->
    <div class="main">
      <el-container>
        <el-aside>
          <div class="userInfo"
               @click="onChangeFragment('personal')">
            <img src="@asset/image/img.png"
                 alt="">
            <span class="font-regular-18"
                  :style="{color:(currentcomponentId==='personal'?'#302fa6':'#000000')}">用户账号</span>
          </div>

          <div class="myMessage"
               @click="onChangeFragment('message')">
            <p class="font-regular-18"
               :style="{color:(currentcomponentId==='message'?'#302fa6':'#000000')}">我的物资</p>
               <!-- 显示物资数量 -->
            <span>{{medicalNumber}}</span>
          </div>

          <div class="audit-container">
            <p class="font-regular-18"
               @click="product('audited')"
               :style="{color:(currentcomponentId==='audited'?'#302fa6':'#000000')}">生产物资 
            </p>
            <div class="line"></div>
            <p class="font-regular-18"
               @click="consume('unAudited')"
               :style="{color:(currentcomponentId==='unAudited'?'#302fa6':'#000000')}">消耗物资
            </p>

          </div>

        </el-aside>

        <div class="main-right">

          <keep-alive>
            <!-- 动态组件 -->
            <component :is="currentcomponentId"
                       :ref="currentcomponentId"></component>
          </keep-alive>

        </div>
      </el-container>
    </div>

    <!-- footer -->
    <div class="headfooter clearfix">
      <headfooter></headfooter>
    </div>
  </div>
</template>

<script>
import headfooter from '@/components/headfooter/headfooter'
export default {
  name: 'MyHomePage',
  components: {
    headfooter,
    // 异步组件引入方式，异步组件：只有在需要去展示这个组件的时候才会把组件去进行渲染
    personal: () => import('@/components/personalHomePage/personalMessage'),
    message: () => import('@/components/personalHomePage/myMessage'),
    audited: () => import('@/components/personalHomePage/product'),
    unAudited: () => import('@/components/personalHomePage/consume')
  },
  data() {
    return {
      medicalNumber:0,
      // 表示选中的tab按钮
      selectItemIndex: 0,
      //   表示选中的组件的名称 依赖于state中的homePageCompId
      //   currentcomponentId: ''
      id:'',
    }
  },
  created(){
    this.getNum();
    this.getId();
  },
  computed: {
    user() {
      return this.$store.state.user
    },
    currentcomponentId() {
      return this.$store.state.homePageCompId
    }
    
  },
  methods: {
    async getId(){
      const res = await this.$http.get('user/info')
      if(res.data.status!==200){
        console.log(res);
        return this.$message.error("获取用户信息失败")
      }
      this.id = res.data.data.role
    },
    //获取物资数量
    async getNum(){
      const res = await this.$http.get('chain/myResources')
      if(res.data.status!==200) return this.$message.error("获取医疗物资列表失败")
      this.medicalNumber = res.data.data.length
      // console.log(this.medicalNumber);
    },
    onChangeFragment: function (componentName) {
      this.$store.commit('changeHomePageCompId', componentName)
      //   this.currentcomponentId = componentName
    },
    product(){
      if(this.id!=='factory') return this.$message.error('你不是生产商，无权生产物资')
      this.$store.commit('changeHomePageCompId', 'audited')
    },
    consume(){
      if(this.id!=='hospital') return this.$message.error('你不是医院，无权消耗物资')
      this.$store.commit('changeHomePageCompId', 'unAudited')
    }
  }

}
</script>

<style lang="less" scoped>
.page {
  width: 100%;
  //   height: 100%;
}
.main {
  width: 100%;
  //   height: 100%;
  /* 写了吸顶之后还必须写padding-top */
  padding-top: 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.el-container {
  box-sizing: border-box;
  padding: 0px 300px 0px 300px;
  width: 1920px;
}
.el-aside {
  width: 390px !important;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
  padding: 100px 30px 100px 60px;
  .userInfo,
  .myMessage,
  .audit-container {
    width: 100%;
    background: #ffffff;
    box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
    border-radius: 20px;
    box-sizing: border-box;
    padding: 20px 30px;
  }
  .userInfo {
    height: 120px;
    margin-bottom: 20px;
    display: flex;
    align-items: center;
    cursor: pointer;
    img {
      width: 80px;
      height: 80px;
      border-radius: 50%;
    }
    span {
      margin-left: 30px;
    }
  }
  .myMessage {
    height: 98px;
    margin-bottom: 20px;
    display: flex;
    align-items: center;
    position: relative;
    cursor: pointer;
    &:hover {
      color: #302fa6;
    }
    span {
      position: absolute;
      right: 30px;
      padding: 5px 10px;
      background: #302fa6;
      border-radius: 12px;
      color: white;
      font-size: 14px;
    }
  }
  .audit-container {
    height: 177px;
    .line {
      width: 240px;
      height: 1px;
      background: rgba(0, 0, 0, 0.1);
    }
    p {
      padding: 30px 0;
      position: relative;
      cursor: pointer;
      span {
        font-size: 14px;
        color: rgba(0, 0, 0, 0.4);
        position: absolute;
        right: 0;
      }
      &:hover {
        color: #302fa6;
      }
    }
  }
}

.el-main {
  padding: 0;
  flex: 1;
  flex-basis: auto;
  overflow: hidden;
}

.headfooter > div {
  margin: 0 auto;
}
</style>