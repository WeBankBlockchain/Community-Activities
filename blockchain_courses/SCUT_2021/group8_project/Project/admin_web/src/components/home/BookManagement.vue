<template>
  <div>
    <edit-form @onSubmit="loadBooks()" ref="edit"></edit-form>
    <OverdueBook @indexSelect="listByCategory()" ref="ovrdueUser"></OverdueBook>
    <el-card style="margin: 18px 2%;width: 95%">
      <el-table
        :data="books"
        stripe
        style="width: 100%"
        :max-height="tableHeight">
        <!--        <el-table-column>-->
        <!--          <template slot-scope="props">-->
        <!--            <el-form label-position="left" inline>-->
        <!--              <el-form-item>-->
        <!--                <span>{{ props.row.abs }}</span>-->
        <!--              </el-form-item>-->
        <!--            </el-form>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
        <el-table-column
          prop="name"
          label="书名"
          fit>
        </el-table-column>
        <el-table-column
          prop="id"
          label="书本id"
          fit>
        </el-table-column>
        <el-table-column
          prop="price"
          label="书本价格"
          fit>
        </el-table-column>
        <el-table-column
          prop="borrower_id"
          label="借阅人id"
          fit>
        </el-table-column>
        <el-table-column
          prop="borrow_time"
          label="借出时间"
          fit>
        </el-table-column>
        <el-table-column
          prop="return_time"
          label="归还时间"
          fit>
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          fit>
        </el-table-column>
        <!--        <el-table-column-->
        <!--          prop="author"-->
        <!--          label="作者"-->
        <!--          fit>-->
        <!--        </el-table-column>-->
        <!--        <el-table-column-->
        <!--          prop="date"-->
        <!--          label="出版日期"-->
        <!--          width="120">-->
        <!--        </el-table-column>-->
        <!--        <el-table-column-->
        <!--          prop="press"-->
        <!--          label="出版社"-->
        <!--          fit>-->
        <!--        </el-table-column>-->
        <!--<el-table-column-->
        <!--prop="abs"-->
        <!--label="摘要"-->
        <!--show-overflow-tooltip-->
        <!--fit>-->
        <!--</el-table-column>-->
        <el-table-column
          fixed="right"
          label="操作"
          width="120">
          <template slot-scope="scope">
<!--            <el-button-->
<!--              @click.native.prevent="editBook(scope.row.id)"-->
<!--              type="text"-->
<!--              size="small">-->
<!--              修改借阅情况-->
<!--            </el-button>-->
            <el-button
              @click.native.prevent="deleteBook(scope.row.id)"
              type="text"
              size="small">
              下架图书
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import EditForm from './EditForm'
import OverdueBook from './OverdueBook'
export default {
  name: 'BookManagement',
  components: {EditForm, OverdueBook},
  data () {
    return {
      books: []
    }
  },
  mounted () {
    this.loadBooks()
  },
  computed: {
    tableHeight () {
      return window.innerHeight - 320
    }
  },
  methods: {
    listByCategory () {
      var _this = this
      var cid = this.$refs.ovrdueUser.checked
      var url = ''
      if (cid === true) {
        url = '/getBorrowedBooks'
      } else {
        url = '/getAllBooks'
      }
      this.$axios.get(url).then(resp => {
        if (resp && resp.data.code === '1') {
          _this.books = resp.data.data
          for (var i = 0; i < _this.books.length; i++) {
            if (_this.books[i].status === '1') {
              _this.books[i].status = '借出'
            } else {
              _this.books[i].status = '在库'
            }
          }
        }
      })
    },
    deleteBook (id) {
      this.$confirm('此操作将下架该书本, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios
          .post('/deleteBook', {id: id}).then(resp => {
            if (resp && resp.data.code === '1') {
              this.$message({
                type: 'info',
                message: resp.data.message
              })
              this.loadBooks()
            } else if (resp.data.code !== '1') {
              this.$message({
                type: 'info',
                message: resp.data.message
              })
            }
          })
      }
      ).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // editBook (id) {
    //   this.$confirm('此操作将用户罚款清零, 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //       this.$axios
    //         .post('/clearFine', {id: id}).then(resp => {
    //         if (resp && resp.data.code === '1') {
    //           this.loadBooks()
    //         } else if (resp.data.code !== '1') {
    //           this.$notify({
    //             title: '',
    //             message: resp.data.message
    //           })
    //         }
    //       })
    //     }
    //   ).catch(() => {
    //     this.$message({
    //       type: 'info',
    //       message: '已取消清零'
    //     })
    //   })
    // },
    loadBooks () {
      var _this = this
      this.$axios.get('/getAllBooks').then(resp => {
        if (resp && resp.data.code === '1') {
          _this.books = resp.data.data
          for (var i = 0; i < _this.books.length; i++) {
            if (_this.books[i].status === '1') {
              _this.books[i].status = '借出'
            } else {
              _this.books[i].status = '在库'
            }
          }
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
