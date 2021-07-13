<template>
    <div>
        <el-card>
            <el-table
            :data="userList"
            style="width: 100%">
            <el-table-column prop="userId" label="ID" width="180"></el-table-column>
            <el-table-column prop="name" label="角色名" width="180"></el-table-column>
            <el-table-column prop="province" label="所在省份" width="180"></el-table-column>
            <el-table-column prop="mail" label="邮箱" width="180"></el-table-column>
            <el-table-column label="操作" width="180">
                <template slot-scope="scope">
                    <el-select :disabled="scope.row.userId===1" v-model="scope.row.role" placeholder="请选择">
                        <el-option
                        v-for="item in powerList"
                        :key="item.label"
                        :label="item.label"
                        :value="item.label"
                        @change="changePower(scope.row.userId)" >
                        </el-option>
                    </el-select>
                </template>
            </el-table-column>
            <el-table-column prop="role" label="操作" width="180">
                <template slot-scope="scope">
                <el-button v-if="scope.row.userId!==1" size="mini" round>
                    <span style="font-size:10px" @click="submit(scope.row.userId,scope.row.role)">提交</span>
                </el-button>
                </template>
            </el-table-column>

            </el-table>
        </el-card>
    </div>
</template>

<script>
export default {
    data(){
        return{
            userList:[],
            power:'',
            powerList:[
                {
                    label: '医院',
                },{
                    label: '普通用户',
                },{
                    label: '生产商',
                }
            ]
        }
    },
    created(){
        this.getUserList();
    },
    methods:{
        //提交修改权限表单
        async submit(userId,role){
            // console.log(1);
            if(role === '生产商') role = 'factory'
            if(role === '医院') role = 'hospital'
            if(role === '普通用户') role = 'user'
            // console.log({'userId':userId,'role':role});
            const res = await this.$http.put('chain/role',{'userId':userId,'role':role})
            if(res.data.status !== 200){
                console.log(res);
                return this.$message.error('修改失败')
            }
            return this.$message.success("修改成功")
            // console.log(res);
        },
        //获取所有用户列表
        async getUserList(){
            const res = await this.$http.get('user/list')
            if(res.data.status !== 200) return this.$message.error("获取用户列表失败") 
            this.userList = res.data.data;
            for(let item of this.userList){
                // console.log(item);
                if(item.role === 'god') item.role = '管理员'
                if(item.role === 'factory') item.role = '生产商'
                if(item.role === 'hospital') item.role = '医院'
                if(item.role === 'user') item.role = '普通用户'
            }
        },
    }
}
</script>

<style scoped>

</style>