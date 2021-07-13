<template>
  <!-- 页脚 -->
  <el-footer>
    <div class="line"></div>
    <div class="footer_des">MST|占位|占位|</div>
    <div class="footer_des">华南理工大学|软件学院|卓越二班|FISCO_BCOS</div>
    <div class="footer_des"
         style="padding-top: 10px;">©️2021 MST占位占位|占位</div>
  </el-footer>
</template>

<script>
export default {}
</script>

<style scoped>
.el-footer {
  margin-top: 120px;
  background: #ffffff;
  color: #333;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: fit-content !important;
}
.line {
  width: 1004px;
  height: 1px;
  background: rgba(0, 0, 0, 0.2);
}
.footer_des {
  padding-top: 5px;
  height: 17px;
  font-size: 12px;
  font-family: AlibabaPuHuiTi-Regular, AlibabaPuHuiTi;
  font-weight: 400;
  color: rgba(0, 0, 0, 0.4);
  line-height: 17px;
}
</style>