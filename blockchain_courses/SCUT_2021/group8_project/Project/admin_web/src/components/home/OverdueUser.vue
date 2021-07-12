<template>
  <div style="text-align: right">
    <el-checkbox v-model="checked" @change="changeShow">查看借书超期的用户</el-checkbox>
  </div>
</template>

<script>
export default {
  name: 'OverdueUser',
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
