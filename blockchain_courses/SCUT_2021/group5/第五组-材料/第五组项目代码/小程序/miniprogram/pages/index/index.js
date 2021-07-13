var app = getApp()
Page({
  data: {
    img: [
      'https://i.loli.net/2020/12/23/JIUZfNBXp6Rvtxb.jpg',
      'https://i.loli.net/2020/11/04/nms5zDLoafMT6v8.jpg',
      'https://i.loli.net/2020/11/04/f6Dz71jiJlRgMZ5.jpg',
      'https://i.loli.net/2020/11/04/DEWCazrsP59U3xF.jpg'
    ],
    mp_openid: app.globalData.openid,
    level: '',
  },
  onLoad() {
    var that = this
    wx.request({
      url: 'http://121.4.27.229:8080/v1/usr/user/get_user_level',
      method: 'GET',
      data: {
        mp_openid: that.data.mp_openid,
      },
      success: function (res) {
        that.data.level = res.data.data.user_level
      }
    })
  },
  touch1() {
    wx.navigateTo({
      url: '/pages/trace/trace',
    })
  },
  touch2() {
    if ((this.data.level == 1) || (this.data.level == 3)) {
      wx.navigateTo({
        url: '/pages/enter/enter',
      })
    } else {
      wx.showToast({
        title: '您不具有该功能的权限！',
        icon: 'none',
        duration: 2000
      })
    }
  },


})