<template>
    <div>
        <Modal
        v-model="modal1"
        title="您正在申请上架"
        @on-ok="upOfGoods"
        @on-cancel="cancel">
        <p>设置价格</p>
        <Input v-model.number="money" placeholder="输入商品价格..." type="number"/>
    </Modal>
    <Modal
        v-model="modal2"
        title="您正在申请下架"
        @on-ok="downOfGoods"
        @on-cancel="cancel">
        <p>是否下架商品</p>
    </Modal>
    <Modal
        v-model="modal3"
        title="创建资产"
        @on-ok="uploadOfGoods"
        >
    <Input v-model="nameOfnew" placeholder="输入商品名字..." />
    <Input v-model="urlOfnew" placeholder="输入图片URL..." />
    </Modal>
    <Modal
        v-model="modal4"
        fullscreen
        title="资产图片"
        @on-cancel="cancel">
        <img v-bind:src='this.good2'>
    </Modal>
    <Row :gutter="16" >
        <Col span="6" v-for="item in good1" v-bind:key="item.index">
            <Card v-if="item.index==-1">
                <Divider />
                <Button type="primary" long @click="modal3 = true">创建资产</Button>
                <Divider />
            </Card>
            <Card v-if="item.index!=-1">
                <p slot="title">{{item.name}}</p>
                <Button type="primary" long @click="modal4 = true,getbanquan2(item.ID)">查看图片</Button>
                <p>价格:{{ item.price }}</p>
                <div v-if="item.selling==false"><Button type="primary" long @click="modal1 = true,currentID = item.ID">上架</Button></div>
                <div v-if="item.selling==true"><Button type="warning" long @click="modal2 = true,currentID = item.ID">下架</Button></div>
            </Card>
        </Col>
    </Row>
</div>
</template>
<script>
export default {
    data() {
        return { // 应该在页面载入前get账户所有的资产
            modal1: false,
            modal2: false,
            modal3: false,
            modal4: false,
            currentID: '',
            nameOfnew: '',
            urlOfnew: '',
            money: 0,
            goods: [],
            goodsID: '',
            good1: [{
                ID: '',
                index: '-1',
                selling: '',
                species: '',
                price: '',
                name: '',
            }],
            good2: '',
        }
    },
    created() {
        this.getMybanquanID()
    },
    methods: {
        getMybanquanID() {
            this.$axios.post('/getMybanquans', {
            }).then((response) => {
                this.goods = response.data.data
                for (let i = 0; i < this.goods.length; i++) {
                    this.getbanquan1(this.goods[i], i)
                }
            })
        },
        getbanquan1(id, i) {
            this.$axios.post('/getbanquanById1', {
                id,
            }).then((response) => {
                let p = {
                    ID: id,
                    index: i,
                    selling: response.data.data.value1,
                    species: response.data.data.value2,
                    price: response.data.data.value3,
                    name: response.data.data.value4 }
                this.good1.push(p)
                console.log(response)
            })
        },
        getbanquan2(id) {
            this.$axios.post('/getbanquanById2', {
                id,
            }).then((response) => {
                this.good2 = response.data.data.value1
                console.log(response)
            })
        },
        upOfGoods() {
            // 上架商品，传入商品相关参数，更新商品状态
            this.$axios.post('/sellbanquan', {
                time: '1',
                remark: '1',
                id: this.currentID,
                price: this.money,
            }).then((response) => {
                console.log(response)
            })
            this.$router.go(0) // 刷新
        },
        cancel() {
            this.$Message.info('取消操作')
        },
        downOfGoods() {
            //  下架商品,传入
            this.$axios.post('/offSale', {
                id: this.currentID,
            }).then((response) => {
                console.log(response)
            })
            this.$router.go(0) // 刷新
        },
        uploadOfGoods() {
            this.$axios.post('/newbanquan', {
                disc: '1',
                name: this.nameOfnew,
                picURL: this.urlOfnew,
                kind: 1,
            }).then((response) => {
                console.log(response.data)
            })
            this.$router.go(0)
        },
    },
}
</script>
<style scoped>
img {
    display: block;
    margin: auto;
}
p {
    font-size: 20px;
    text-align: center;
}
Icon {
    margin: auto;
}
</style>
