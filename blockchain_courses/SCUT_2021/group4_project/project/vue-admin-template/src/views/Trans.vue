<template>
    <div>
    <Modal
        v-model="modal1"
        fullscreen
        title="资产图片">
        <img v-bind:src='this.good2'>
    </Modal>
        <List header="交易列表" footer="没有更多了" border >
            <ListItem>
                <Col span="8">卖出或买入</Col>
                <Col span="8">商品ID(点击查看图片)</Col>
                <Col span="8">交易价格</Col>
            </ListItem>
            <ListItem v-for="item in record" :key="item.recordID">
                <Col span="8" v-if="item.seller==myAdds">卖出</Col>
                <Col span="8" v-if="item.buyer==myAdds">买入</Col>
                <Col span="8" ><div style="color:#00F"><u>
                    <p @click="modal1 = true,getbanquan2(item.ntfID)">{{item.recordID}}</p>
                </u></div></Col>
                <Col span="8">{{item.price}}</Col>
            </ListItem>
        </List>
    </div>
</template>
<script>
export default {
    data() {
        return { // 在页面载入前get所有账户的交易
            modal1: false,
            RecordID: [],
            myAdds: '',
            good2: '',
            record: [{
                recordID: '',
                buyer: '',
                seller: '',
                ntfID: '',
                price: '',
                status: '',
                time: '',
                reason: '',
            }],
        }
    },
    created() {
        this.getMyrecordID()
        this.getMyAddress()
    },
    methods: {
        getMyAddress() {
            this.$axios.get('/myAddress').then((response) => {
                this.myAdds = response.data.data
                console.log(response)
            })
        },
        getMyrecordID() {
            this.$axios.post('/ getMyRecords', {
            }).then((response) => {
                this.RecordID = response.data.data
                for (let i = 0; i < this.RecordID.length; i++) {
                    this.getRecord(this.RecordID[i])
                }
                console.log(response)
            })
        },
        getRecord(id) {
            this.$axios.post('/ getRecordById', {
                id,
            }).then((response) => {
                let p = {
                    recordID: id,
                    buyer: response.data.data.value1,
                    seller: response.data.data.value2,
                    ntfID: response.data.data.value3,
                    price: response.data.data.value4,
                    status: response.data.data.value5,
                    time: response.data.data.value6,
                    reason: response.data.data.value7 }
                this.record.push(p)
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
    },
}
</script>
