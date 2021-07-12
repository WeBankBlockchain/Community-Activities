<template>
  <el-card class="admin-header">
    <img src="../pic/a.png" alt="" width="55px" style="float: right;margin-top: -5px;">
    <img src="../pic/b.png" alt="" width="70px" style="float: left;margin-top: -5px;">
    <span style="font-size: 32px;font-weight: bold;position:absolute;left: 45%">图书管理系统</span>
  </el-card>
</template>

<script>
export default {
  name: 'Header'
}
</script>

<style scoped>
.admin-header {
  height: 80px;
  opacity: 0.85;
  line-height: 40px;
  min-width: 900px;
}
</style>
