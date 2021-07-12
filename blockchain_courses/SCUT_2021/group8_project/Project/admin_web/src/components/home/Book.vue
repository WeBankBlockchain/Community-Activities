<template>
  <el-container>
    <el-header>
      <Header></Header>
    </el-header>
    <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <SideMenu></SideMenu>
    </el-aside>
    <el-main>
            <BookManagement></BookManagement>
    </el-main>
  </el-container>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import BookManagement from './BookManagement'
import Header from './Header'
export default {
  name: 'Book',
  components: {SideMenu, BookManagement, Header}
}
</script>

<style scoped>

</style>
