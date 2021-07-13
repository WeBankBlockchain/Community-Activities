// pages/enter/enter.js
const app = getApp()
Page({
  data: {
    list: [],
    isshow: false,
    openid: app.globalData.mp_openid,
    send_from: '',
    send_to: '',
    itemid: '',
    isid: true,
    receiver: ''
  },
  //清空列表
  clear() {
    this.setData({
      list: []
    })
    wx.showToast({
      title: '列表成功清空',
      icon: "success"
    })
  },
  //删除某一商品
  del(e) {
    var id = e.currentTarget.dataset.index
    var list1 = this.data.list
    list1.splice(id, 1);
    for (let i = 0; i < this.data.list.length; i++) {
      list1[i].id = i + 1;
    }
    this.setData({
      list: list1,
    })
    wx.showToast({
      title: '删除成功',
      icon: "success"
    })
  },
  //扫码添加
  scan() {
    let that = this
    wx.scanCode({
      success(res) {
        let temp = {}
        let list_temp = that.data.list
        temp.id = list_temp.length + 1
        temp.number = res.result
        let isrepeat = false
        for (let i = 0; i < list_temp.length; i++) {
          if (list_temp[i].number == res.result) isrepeat = true
        }
        if (!isrepeat) {
          list_temp.push(temp)
          that.setData({
            list: list_temp,
          })
          wx.showToast({
            title: '添加成功',
            icon: "success"
          })
        } else {
          wx.showModal({
            title: '提示',
            content: '货物已在出库列表中，\r\n无需重复扫码登记！',
            showCancel: false
          })
        }
      }
    })
  },
  search() {
    var isre = false;
    let that = this;
    wx.scanCode({
      success: function (res) {
        var sea = res.result;
        for (let i = 0; i < that.data.list.length; i++) {
          if (that.data.list[i].number == sea) isre = true
        }
        if (isre) {
          wx.showModal({
            title: '提示',
            content: '当前货物已在出库列表中!',
            showCancel: false
          })
        } else {
          wx.showModal({
            title: '提示',
            content: '当前货物不在出库列表中!',
            showCancel: false
          })
        }

      }
    })
  },
  submit() {
    let item_list = ''
    for (let i = 0; i < this.data.list.length; i++) {
      item_list += this.data.list[i].number
      item_list += '\n'
    }
    console.log(item_list)
    console.log(this.data.list)
    this.setData({
      isshow: true,
      itemid: item_list
    })
  },

  scanid() {
    let that = this
    wx.scanCode({
      success: function (res) {
        console.log(res)
        that.setData({
          receiver: res.result,
          isid: false
        })
        wx.showToast({
          title: "成功获取",
        })
      }
    })
  },
  send_from(e) {
    this.setData({
      send_from: e.detail.value
    })
  },
  send_to(e) {
    this.setData({
      send_to: e.detail.value
    })
  },

  ok() {
    let that = this
    let item_list = []
    for (let i = 0; i < this.data.list.length; i++) {
      item_list.push(this.data.list[i].number)
    }
    wx.request({
      url: 'http://121.4.27.229:8080/v1/usr/transfer/send_transfer_request?mp_openid=' + that.data.openid,
      method: 'POST',
      header: {
        'Content-Type': 'application/json'
      },
      data: {
        receiver_id: that.data.mp_openid,
        send_from: that.data.send_from,
        send_to: that.data.send_to,
        item_list: item_list
      },
      success: function (res) {
        console.log(res)
        if (res.data.data == -100) {
          wx.showModal({
            title: "提示",
            content: "发送方ID错误或存在非法货物！\r\n请检查您提交的信息！"
          })
          that.setData({
            isshow: false,
            list:[]
          })
          wx.showToast({
            title: "请求发送成功！",
          })
        } else {
          that.setData({
            isshow: false
          })
          wx.showToast({
            title: "请求发送成功！",
          })
        }
      }
    })
  },

  cancel() {
    this.setData({
      isshow: false
    })
  },

})