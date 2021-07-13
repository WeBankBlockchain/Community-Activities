
<!-- 这是包括首页所有内容的组件 -->
<template>
  <div id="home">
    <!-- 首页内容被分成6个部分，每个部分是一个单独的组件,按设计稿顺序 -->
    <!-- 这里已经解决 absolute 的父容器没有定位的问题，即在各自的组件中
           的第一个已定位的父容器就是你的组件的 template 下的第一个 div，你可以在
           自己的组件中使用 absolute 定位，而不需要考虑已定位的父容器问题 -->
    <!-- 组件的名称和文件名一样，这可以帮助你较快速地找到自己的组件 -->
    <div class="homeheader clearfix">
      <homeheader></homeheader>
    </div>

    <div class="bodyContent claerfix">
      <div  class="recommend clearfix">
        <recommend></recommend>
      </div>
      <div class="altas clearfix">
        <altas></altas>
      </div>
      <div class="dynamic clearfix">
        <dynamic></dynamic>
      </div>
    </div>
    
    <div class="headfooter clearfix">
      <headfooter></headfooter>
    </div>
  </div>
</template>

<script>
import homeheader from '@/components/homeheader/homeheader'
import recommend from '@/components/recommend/recommend'
import altas from '@/components/altas/altas'
import dynamic from '@/components/dynamic/dynamic'
import headfooter from '@/components/headfooter/headfooter'
import $ from 'jquery'

export default {
  name: 'home',
  components: {
    homeheader,
    recommend,
    altas,
    dynamic,
    headfooter
  },
  data(){
    return{
      flag_1:700,
      flag1:false,
    }
  },
  methods:{
    handleScroll() {
      function slideIn(obj,top){
        var targetHeight = obj.offset().top;   //目标元素到顶部的距离
        var scrollTop = $(window).scrollTop(); //页面滚动的距离

      if(scrollTop>targetHeight-600){
          obj.animate({top:top+'px',opacity:1},2000);
        }
      }
      slideIn($(".recommend"),-200);
      slideIn($(".altas"),-200);
      slideIn($(".dynamic"),-200);
    },
  },

  mounted(){
    window.addEventListener("scroll", this.handleScroll, true);
  },

  destroyed() {
    window.removeEventListener("scroll", this.handleScroll, true);
  },
}
</script>

<style scoped>
.homeheader > div,
.bodyContent,
.headfooter > div {
  margin: 0 auto;
}
#home > div,
#home > div > div {
  position: relative;
  top: 0;
}
.bodyContent{
  margin-top: 50px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.recommend{
  opacity: 0;
}

.altas{
  opacity: 0;
  margin-top:50px;
}

.dynamic{
  margin-top:50px;
  opacity: 0;
}


</style>