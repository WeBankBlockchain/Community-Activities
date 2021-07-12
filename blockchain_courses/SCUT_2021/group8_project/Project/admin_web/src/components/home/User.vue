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
            <UserManagement></UserManagement>
    </el-main>
  </el-container>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import UserManagement from './UserManagement'
import Header from './Header'
export default {
  name: 'User',
  components: {SideMenu, UserManagement, Header}
}
</script>

<style scoped>

</style>
