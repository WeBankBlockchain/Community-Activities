<template>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <SideMenu></SideMenu>
    </el-aside>
    <el-main>
<!--      <BookManagement></BookManagement>-->
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import BookManagement from './BookManagement'
export default {
  name: 'Appindex',
  components: {SideMenu, BookManagement}
}
</script>

<style scoped>

</style>
