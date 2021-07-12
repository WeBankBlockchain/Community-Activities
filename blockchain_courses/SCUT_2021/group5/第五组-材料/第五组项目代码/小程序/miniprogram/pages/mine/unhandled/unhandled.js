Page({
  data: {
    list: [{
        id: 1,
        number: "100002",
        sender_id: '114514'
      },
      {
        id: 2,
        number: "100009",
        sender_id: '100003'
      },
      {
        id: 3,
        number: "100001",
        sender_id: '100004'
      }
    ],
    openid: '100003'
  },

  // onLoad: function () {
  //   let that = this
  //   wx.request({
  //     url: 'http://121.4.27.229:8080/v1/usr/transfer/get_receiver_request',
  //     method: 'GET',
  //     data:{
  //       mp_openid:that.data.openid,
  //     },
  //     success:function(res){
  //       console.log(res.data.data)
  //       let itemlist = []
  //       that.setData({
  //         list:[]
  //       })
  //       if(res.data.data !== 0) {
  //         for (let i = 0; i < res.data.data.item_list.length; i++) {
  //           for (let j = 0; j < res.data.data.item_list[i].length; j++) {
  //             let temp = {}
  //               temp.id = itemlist.length + 1
  //               temp.number = res.data.data.item_list[i][j]
  //               temp.sender_id = res.data.data.sender_id[i]
  //               itemlist.push(temp)
  //               that.setData({
  //                 list:itemlist
  //               })
  //           }
  //         }
  //       } 
  //     }
  //   })
  // },
  enter() {
    wx.navigateTo({
      url: '/pages/item/item',
    })
  },

  jump(e) {
    let that = this
    let str = '您是否'
    if (e.currentTarget.dataset.confirm === '1') {
      str += '同意'
    } else {
      str += '拒绝'
    }
    str += '交易请求'
    wx.showModal({
      title: '提示',
      content: str,
      showCancel: true,
      success: function (res) {
        if (res.cancel) {
          console.log("您点击了取消")
        } else if (res.confirm) {
          wx.request({
            url: 'http://121.4.27.229:8080/v1/usr/transfer/accept_transfer_request?mp_openid=' + that.data.openid + '&sender_id=' + that.data.list[e.currentTarget.dataset.index].sender_id + '&isAccept=' + e.currentTarget.dataset.confirm,
            method: 'POST',
            success: function (res) {
              console.log(res)
              that.onLoad()
            }
          })
          console.log("您点击了确定")
        }
      }
    })
  },
})