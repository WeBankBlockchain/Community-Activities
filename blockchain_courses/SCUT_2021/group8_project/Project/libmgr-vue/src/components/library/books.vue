 <template>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="date_return"
        label="归还期限"
        width="180">
      </el-table-column>
      <el-table-column
        prop="book_name"
        label="书名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="book_id"
        label="书索号"
        width="180">
      </el-table-column>
  </el-table>
  </template>

<script>
export default {
  data () {
    return {
      tableData: []
    }
  },
  mounted: function () {
    /* this.tableData.push({
      date_return: '2016-05-04',
      book_name: '多体',
      book_id: '5'
    }) 增加书的方法 */
    this.loadbooks()
  },
  created () {
    this.$bus.on('LoadBooks', this.loadbooks)
  },
  methods: {
    loadbooks () {
      this.tableData = []
      this.$axios.get('/user/getBooks').then(successResponse => {
        var arr = successResponse.data.data
        for (var i = 0; i < arr.length; i++) {
          this.tableData.push({ date_return: arr[i].return_time, book_name: arr[i].name, book_id: arr[i].id })
          console.log(arr[i].return_time)
          console.log(arr[i].name)
          console.log(arr[i].id)
        }
      })
    }
  }
}
</script>
