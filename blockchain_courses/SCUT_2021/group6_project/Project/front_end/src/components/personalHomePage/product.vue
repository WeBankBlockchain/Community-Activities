<template>
  <!-- <div class="page"></div> -->
  <!-- 图册列表展示 -->
  <div class="mainPage">
    <div>
      <span class="font-regular-18">资源名称：</span>
    <el-input class="createInput" style="width"  v-model="createList.resourceName" placeholder="请输入资源名称"></el-input>
    </div>
    <!-- 选择类 -->
    <div>
      <span class="font-regular-18">资源类别：</span>
    <el-select class="createInput" v-model="createList.category" placeholder="请选择物资类别">
      <el-option
        v-for="item in category"
        :key="item.label"
        :label="item.label"
        :value="item.label">
      </el-option>
    </el-select>
    </div>
    <div>
      <span class="font-regular-18">资源数量：</span>
    <el-input class="createInput" v-model="createList.resourceNum" placeholder="请输入资源数量"></el-input>
    </div>
    <div>
      <span class="font-regular-18">批次号：</span>
      <el-input class="batchInput" style="width:60px" v-model="batchCode.batchCode1"></el-input>
      <el-input class="batchInput" v-model="batchCode.batchCode2">
        <template slot="prepend">-</template>
      </el-input>
      <el-input class="batchInput" v-model="batchCode.batchCode3">
        <template slot="prepend">-</template>
      </el-input>
    <!-- <el-input class="createInput" v-model="createList.batchCode" placeholder="请输入订单号"></el-input> -->
    </div>
    <div>
    <el-button class="font-regular-18" style="margin-top:30px" @click="createResource">创建物资</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      batchCode:{
        batchCode1:'',
        batchCode2:'',
        batchCode3:'',
      },
      createList:{
        "resourceNum":'',
        "resourceName":"",
        "category":"",
        "batchCode":""
      },
      category:[
        {label:"口罩"},
        {label:"防护服"},
        {label:"纱布"},
        {label:"创可贴"},
        {label:"呼吸机"},
        {label:"红外体温计"},
        {label:"洗手液"},
        {label:"COVID-19检测试剂"},
      ]
    }
  },
  methods:{
    async createResource(){
      this.createList.batchCode=this.batchCode.batchCode1+"-" + this.batchCode.batchCode2+ "-" + this.batchCode.batchCode3
      console.log(this.createList.batchCode);
      const res = await this.$http.post('chain/resource',this.createList)
      if(res.data.status!==200) return this.$message.error("生产物资失败")
      this.$message.success("生产物资成功")
      this.$router.go(0)
    }
  }

}
</script>

<style lang="less" scoped>

.mainPage {
  height: 817px;
  width: 840px;
  box-sizing: border-box;
  margin: 100px 60px 100px 30px;
}

.createInput{
  margin-top:30px;
  width:200px;
}

.batchInput{
  margin-top:30px;
  width: 100px;
}
</style>