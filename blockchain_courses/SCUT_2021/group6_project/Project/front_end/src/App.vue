<template>
  <div id="app">
    <!-- 路由占位符 -->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',
  data() {
    return {
    }
  },
  mounted() {
    window.onresize = function () {
      // 定义窗口大小变更通知事件
      if (document.body.clientWidth <= '768') {
        var a = document.getElementById('app')
        a.style.zoom = document.body.clientWidth / 768
      }
    }
  },
  methods: {
  }
}
</script>

<style scoped>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow-x: hidden;
}
</style>
