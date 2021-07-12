<template>
<div>
<div class="bg"></div>
<h1 style="color:#000000">读者借阅情况</h1>
  <el-container>
    <el-aside style="width: 300px;margin-top: 20px">
      <switch></switch>
      <SideMenu></SideMenu>
    </el-aside>
    <el-main>
      <Books></Books>
    </el-main>
  </el-container>
</div>
</template>

<script>
import SideMenu from './SideMenu'
import Books from './books'
export default {
  name: 'AppLibrary',
  components: {SideMenu, Books}
}
</script>

<style scoped>
div.bg{
  background: url("./image/scut_bg.png") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
  opacity: 0.1;
  z-index: 1;
}
</style>
