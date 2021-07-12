<template>
  <div style="text-align: right">
    <el-checkbox v-model="checked" @change="changeShow">查看逾期未归还的书</el-checkbox>
  </div>
</template>

<script>
export default {
  name: 'OverdueBook',
  data () {
    return {
      checked: false
    }
  },
  methods: {
    changeShow () {
      this.$emit('indexSelect')
    }
  }
}
</script>

<style scoped>

</style>
