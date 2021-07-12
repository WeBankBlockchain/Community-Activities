<template>
  <el-menu
    class="categories"
    default-active="0"
    @select="handleSelect"
    active-text-color="red">
    <el-menu-item index="0">
      <ReturnForm></ReturnForm>
    </el-menu-item>
    <el-menu-item index="1">
      <AddForm></AddForm>
    </el-menu-item>
    <el-menu-item index="2">
      <ChangePassword></ChangePassword>
    </el-menu-item>
  </el-menu>
</template>

<script>
import AddForm from './AddForm'
import ChangePassword from './ChangePassword'
import ReturnForm from './ReturnForm'
export default {
  name: 'SideMenu',
  components: {AddForm, ReturnForm, ChangePassword}
}
</script>

<style scoped>
  .categories {
    position: fixed;
    margin-left: 0px;
    left: 0px;
    top: 100px;
    width: 150px;
    z-index: 1;
  }
</style>
