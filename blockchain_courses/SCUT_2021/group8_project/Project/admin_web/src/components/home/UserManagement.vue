<template>
  <div>
    <edit-user @onSubmit="loadBooks()" ref="edit"></edit-user>
    <OverdueUser @indexSelect="listByCategory()" ref="ovrdueUser"></OverdueUser>
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
          label="用户名"
          fit>
        </el-table-column>
        <el-table-column
          prop="id"
          label="用户id"
          fit>
        </el-table-column>
        <el-table-column
          prop="fine"
          label="罚款"
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
            <el-button
              @click.native.prevent="editBook(scope.row.id)"
              type="text"
              size="small">
              清除罚款
            </el-button>
            <el-button
              @click.native.prevent="deleteBook(scope.row.id)"
              type="text"
              size="small">
              注销
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import EditUser from './EditUser'
import OverdueUser from './OverdueUser'
export default {
  name: 'UserManagement',
  components: {EditUser, OverdueUser},
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
        url = '/getFinedUsers'
      } else {
        url = '/getAllUsers'
      }
      this.$axios.get(url).then(resp => {
        if (resp && resp.data.code === '1') {
          _this.books = resp.data.data
        }
      })
    },
    deleteBook (id) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios
          .post('/deleteUser', {id: id}).then(resp => {
            if (resp && resp.data.code === '1') {
              this.loadBooks()
              this.$message({
                type: 'info',
                message: resp.data.message
              })
            } else if (resp.data.code !== '1') {
              this.$notify({
                title: '',
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
    editBook (id) {
      this.$confirm('此操作将用户罚款清零, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios
          .post('/clearFine', {id: id}).then(resp => {
            if (resp && resp.data.code === '1') {
              this.loadBooks()
              this.$message({
                type: 'info',
                message: resp.data.message
              })
            } else if (resp.data.code !== '1') {
              this.$notify({
                title: '',
                message: resp.data.message
              })
            }
          })
      }
      ).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消清零'
        })
      })
    },
    loadBooks () {
      var _this = this
      this.$axios.get('/getAllUsers').then(resp => {
        if (resp && resp.data.code === '1') {
          _this.books = resp.data.data
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
