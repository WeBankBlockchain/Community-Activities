
Page({
  data: {
    list: [{
        id: 1,
        number: "dagkjda12324524"
      },
      {
        id: 2,
        number: "qwrwqrwq123123"
      },{
        id: 3,
        number: "ggsgasg231321w3"
      }

    ],
    openid:'100002'
  },

  onLoad: function () {
    let that = this
    wx.request({
      url: 'http://121.4.27.229:8080/v1/usr/transfer/get_user_send',
      method: 'GET',
      data:{
        mp_openid:that.data.openid,
      },
      success:function(res){
        console.log(res.data.data)
        let itemlist = []
        that.setData({
          list:[]
        })
        if(res.data.data !== 0) {
          for (let i = 0; i < res.data.data.item_list.length; i++) {
            for (let j = 0; j < res.data.data.item_list[i].length; j++) {
              let temp = {}
              temp.id = itemlist.length + 1
              temp.number = res.data.data.item_list[i][j]
              itemlist.push(temp)
              that.setData({
                list:itemlist
              })
            }
          }
        }
        
      }
    })
  },

  
  jump(e) {
    let that = this
    wx.navigateTo({
      url: '/pages/information/information',
      success:function (res) {
        console.log(that.data.list[e.currentTarget.dataset.index].number)//商品序列号，用于后期的接口调用
        let result = res
        ///item/get_item_process?item_id={item_id}
        wx.request({
          url: 'http://121.4.27.229:8080/v1/usr/item/get_item_process?item_id='+that.data.list[e.currentTarget.dataset.index].number,
          method: 'GET',
          success:function(res){
            const dat = res.data.data
            console.log(res.data.data)
            let emitdata = {}
            emitdata.time = dat.transfer_time
            emitdata.sender_id = dat.sender_id
            emitdata.recevier_id = dat.receiver_id
            emitdata.sender_address = dat.send_from
            emitdata.recevier_address = dat.send_to
            result.eventChannel.emit('acceptDataFromA',emitdata)
          }
        })
        
      }
    })
  },
})