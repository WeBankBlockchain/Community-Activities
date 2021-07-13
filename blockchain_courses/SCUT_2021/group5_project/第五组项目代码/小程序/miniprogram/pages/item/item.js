const app = getApp()
Page({
  data: {
    senderID: 100002,
    sendfrom: "广东省广州市华南理工大学大学城校区宿舍楼",
    sendto: "河北省邯郸市邯山区宿舍楼",
    list: [{
      id: 1,
      number: 'QmJQyHF8DU8evZ5'
    }, {
      id: 2,
      number: 'Mhxh4Gw6lzpx6Xg'
    }, {
      id: 3,
      number: 'NsssmbqN5jntSwb'
    }],
    isshow: false,
    openid: app.globalData.mp_openid,
    send_from: '',
    send_to: '',
    itemid: '',
    isid: true,
    receiver: ''
  },
  ok() {
    var that = this
    wx.showModal({
      title: '提示',
      content: "您是否确认接收该交易？",
      showCancel: true,
      success: function (res) {
        if (res.cancel) {
          console.log("点击了取消")
        } else if (res.confirm) {
          wx.request({
            url: 'http://121.4.27.229:8080/v1/usr/transfer/accept_transfer_request',
            method: 'POST',
            header: {
              'Content-Type': 'application/json'
            },
            data: {
              mp_openid: that.data.openid,
              sender_id: that.data.senderID,
              isAccept: true
            },
            success: function (res) {
              wx.showModal({
                title:"提示",
                content:"运输订单已确认！",
                showCancel: false,
                success:function(res){
                  wx.switchTab({
                    url: '/pages/mine/mine',
                  })
                }
              })
            }
          })
        }
      }
    })
  },

  cancel() {
    var that = this
    wx.showModal({
      title: '提示',
      content: "您是否确认拒绝该交易？",
      showCancel: true,
      success: function (res) {
        if (res.cancel) {
          console.log("点击了取消")
        } else if (res.confirm) {
          wx.request({
            url: 'http://121.4.27.229:8080/v1/usr/transfer/accept_transfer_request',
            method: 'POST',
            header: {
              'Content-Type': 'application/json'
            },
            data: {
              mp_openid: that.data.openid,
              sender_id: that.data.senderID,
              isAccept: false
            },
            success: function (res) {
              wx.showToast({
                title: "已拒绝！",
              })
              wx.navigateTo({
                url: '/pages/unhandle/unhandle',
              })
            }
          })
        }
      }
    })
 
  },

})