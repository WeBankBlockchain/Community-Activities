

<template>
    <div>
        <Modal
        v-model="modal1"
        title="您正在发起购买"
        @on-ok="buy"
        @on-cancel="cancel">
        <p>注意价格</p>
    </Modal>
    <Modal
        v-model="modal2"
        fullscreen
        title="资产图片">
        <img v-bind:src='this.good2'>
    </Modal>
    <Modal
        v-model="modal3"
        title="充值"
        @on-ok="setAccount">
        <Input v-model.number="qian" placeholder="输入充值数额..." />
    </Modal>
    <Button type="primary" long @click="modal3 = true">充值</Button>
    <Row :gutter="16" >
        <Col span="6" v-for="item in good1" :key="item.index">
            <Card v-if="item.index==-1">
                <p>欢迎光临</p>
                <p>购买时请注意价格</p>
            </Card>
            <Card v-if="item.index!=-1">
                <p slot="title">{{item.name}}</p>
                <Button type="primary" long @click="modal2 = true,getbanquan2(item.ID)">查看图片</Button>
                <p>{{ item.price }}</p>
                <Button type="primary" long @click="modal1 = true,currentID = item.ID">购买</Button>
            </Card>
        </Col>
    </Row>

</div>
</template>
<script>
export default {
    data() {
        return { // 应该在页面载入前get所有已上架的商品
            modal1: false,
            modal2: false,
            modal3: false,
            myAdds: '',
            qian: '',
            goodsid: '',
            banquanid: [],
            currentID: '',
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
        this.getbanquanID()
        this.getAddress()
        this.getBalance()
    },
    methods: {
        getBalance() {
            this.$axios.get('/myBalance').then((response) => {
                this.myBalance = response.data.data
            })
        },
        getAddress() {
            this.$axios.get('/myAddress').then((response) => {
                this.myAdds = response.data.data
                console.log(response)
            })
        },
        setAccount() {
            this.$axios.post('/newAccount', {
                address: this.myAdds,
                money: this.qian + this.myBalance,
            }).then((response) => {
                console.log(response)
                this.$router.go(0) // 刷新
            })
        },
        getbanquanID() {
            this.$axios.post('/getNtfsForSale', {
            }).then((response) => {
                this.banquanid = response.data.data
                for (let i = 0; i < this.banquanid.length; i++) {
                    this.getNtf1(this.banquanid[i], i)
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
            })
        },
        getbanquan2(id) {
            this.$axios.post('/getbanquanById2', {
                id,
            }).then((response) => {
                this.good2 = response.data.data.value1
            })
        },
        buy() {
            // 传入goodsid
            this.$axios.post('/buybanquan', {
                time: '10',
                id: this.currentID,
            }).then((response) => {
                if (response.data.data.statusOK == false) {
                    this.$Message.info('购买失败,确认您有足够的资金')
                } else {
                    this.$router.go(0) // 刷新
                }
            })
        },
        cancel() {
            this.$Message.info('取消操作')
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
</style>
