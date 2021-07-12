<template>
  <el-menu
    router
    class="categories"
    :default-active="currentPath"
    active-text-color="red">
    <el-menu-item index="/user">
      <i class="el-icon-tickets"></i>
      <span slot="title">用户管理</span>
    </el-menu-item>
    <el-menu-item index="/book">
      <i class="el-icon-tickets"></i>
      <span slot="title">查看书本情况</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'SideMenu',
  computed: {
    currentPath () {
      return this.$route.path
    }
  }
}
</script>

<!--<style scoped>-->
<!--.categories {-->
<!--  position: fixed;-->
<!--  margin-left: 50%;-->
<!--  left: -600px;-->
<!--  top: 100px;-->
<!--  width: 150px;-->
<!--}-->
<!--</style>-->
