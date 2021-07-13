Page({
  data: {},

  scan() {
    let that = this
    wx.scanCode({
      success(res) {
        console.log(res)
        let dat = res.result
        if (dat === 'NsssmbqN5jntSwb') {
          wx.navigateTo({
            url: "/pages/info/info",
          })
          wx.showToast({
            title: "获取信息成功！",
          })
        }else{
          wx.showModal({
            title: '提示',
            content: '二维码信息错误，\r\n请扫描正确的二维码！',
            showCancel: false
          })
        }
        // if (res.result) {
        //   wx.request({
        //     url: 'http://121.4.27.229:8080/v1/usr/item/get_item_process?item_id=' + res.result,
        //     method: 'GET',
        //     success: function (result) {

        //     }
        //   })
        // }
      }
    })
  }
})