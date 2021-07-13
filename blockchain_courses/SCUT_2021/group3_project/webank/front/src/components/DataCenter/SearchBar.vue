<template>
    <div style="margin-bottom: 30px;display: flex;justify-content: center;align-items: center">
        <el-input placeholder="请输入文件名进行搜索" v-model="keywords" >
            <el-button slot="append" icon="el-icon-search" @click="searchClick"></el-button>
        </el-input>
    </div>
</template>

<script>
  export default {
    name: 'SearchBar',
    data () {
      return {
        keywords: '',
        data: [],
        cardLoading: [],
        value: ''
      }
    },
    methods: {
      searchClick () {
        this.$emit('onSearch')
      }
    }
  }
</script>
