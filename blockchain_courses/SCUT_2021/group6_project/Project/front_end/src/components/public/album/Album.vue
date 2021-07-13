<template>
  <div class="album-box"
       @click='routeAlbumDetail()'>
    <img class="only_left"
         v-show="visible_1"
         v-bind:src="value1"
         alt="">
    <div v-show="!visible_1"
         class="inline_img_left">
      <img v-bind:src="value1">
    </div>
    <div v-show="visible_2"
         class="inline_img_right">
      <img class="only_right"
           v-bind:src="value2">
    </div>
    <div v-show="visible_3"
         class="inline_img_right">
      <div class="inline_img_right_top"><img v-bind:src="value2"></div>
      <div class="inline_img_right_bottom"><img v-bind:src="value3"></div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    value1: {
      type: String,
      default() {
        return ''
      }
    },
    value2: {
      type: String,
      default() {
        return ''
      }
    },
    value3: {
      type: String,
      default() {
        return ''
      }
    },
    name:{
      type:String,
    }
  },
  data() {
    return {
      num: 0,
      visible_1: null,
      visible_2: null,
      visible_3: null
    }
  },
  created() {
    this.initThisNum()
    this.visible_1 = this.num == 1
    this.visible_2 = this.num == 2
    this.visible_3 = this.num == 3
  },

  methods: {
    initThisNum: function () {
      if (this.value1) {
        this.num = 1
        if (this.value2) {
          this.num = 2
          if (this.value3) {
            this.num = 3
          }
        }
      }
    },
    routeAlbumDetail: function () {
      // console.log(this.name);
      // 点击图册 直接路由进详情
      this.$router.push({
        name: 'AlbumDetail',
        query: {
          name: this.name,
        },
      })
    }
  }
}
</script>
<style scoped>
.album-box {
  display: flex;
  width: 285px;
  height: 190px;
  cursor: pointer;
}
.only_left {
  width: 285px;
  height: 190px;
  border-radius: 20px;
  margin: 0 !important;
}
.inline_img_left {
  display: inline-block;
  width: 190px;
  height: 190px;
}
.inline_img_left img {
  border-radius: 20px 0px 0px 20px;
  width: 190px;
  height: 190px;
  margin: 0 !important;
}
.inline_img_right {
  display: inline-block;
}
.only_right {
  width: 95px;
  height: 190px;
  border-radius: 0px 20px 20px 0px;
}
.inline_img_right_top {
  width: 95px;
  height: 95px;
}
.inline_img_right_top img {
  border-radius: 0px 20px 0px 0px;
  width: 95px;
  height: 95px;
}
.inline_img_right_bottom {
  width: 95px;
  height: 95px;
}
.inline_img_right_bottom img {
  border-radius: 0px 0px 20px 0px;
  width: 95px;
  height: 95px;
}
</style>
