<template>
  <div class="page">
      <el-select class="selectClass" v-model="Id" placeholder="请选择">
        <el-option  
          v-for="item in medicalList"
          :key="item.resourceId"
          :label="item.resourceName"
          :value="item.resourceId">
        </el-option>
      </el-select>
    <div>
      <el-button round class="page-item-button" @click="useMedical()" type="primary">消耗物资</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return { 
      Id:'',
      medicalList:[],
    }
  },
  created(){
    this.getMedicalList();
  },
  methods:{
    //消耗物资
    async useMedical(){
      console.log(this.Id);
      console.log({'resourceId':this.Id});
      const res = await this.$http.delete('chain/resource',{data:{'resourceId':this.Id}})
      if(res.data.status !== 200) {
        console.log(res);
        return this.$message.error("消耗物资失败")
      }
      this.$message.success("删除物资成功")
      console.log(res);
      this.$router.go(0)
    },

    async getMedicalList(){
      const res = await this.$http.get('chain/myResources')
      if(res.data.status!==200){
        console.log(res);
        return this.$message.error("获取物资列表失败")
      }
      // console.log(res);
      this.medicalList = res.data.data
    }
  }
}
</script>

<style lang="less" scoped>
.page {
  width: 840px;
  height: 817px;
  box-sizing: border-box;
  margin: 100px 60px 100px 30px;
  padding: 10px 30px;
  background: #ffffff;
  //   justify-content: space-between;
  box-shadow: 0px 5px 80px 0px rgba(0, 0, 0, 0.05);
  border-radius: 20px;
  overflow-y: auto;
}

.input{
  width: 100px;
}

.page-item-button {
  width: 300px;
  height: 56px;
  background: #302fa6;
  font-size: 18px;
  color: #ffffff;
  border-radius: 28px !important;
  display: block;
  margin: 0 auto;
  margin-top: 25px;
  outline-style: none;
}

.selectClass{
  width: 300px;
  height: 56px;
  font-size: 18px;
  color: #ffffff;
  display: block;
  margin: 0 auto;
  margin-top: 100px;
  outline-style: none;
}
</style>