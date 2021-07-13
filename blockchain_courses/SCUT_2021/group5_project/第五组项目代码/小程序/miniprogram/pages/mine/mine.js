var app = getApp()
Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUseGetUserProfile: false,
    level: '',
    mp_openid: app.globalData.openid
  },

  onLoad() {
    var that = this
    wx.request({
      url: 'http://121.4.27.229:8080/v1/usr/user/get_user_level',
      method: 'GET',
      data: {
        mp_openid: app.globalData.openid,
      },
      success: function (res) {
        if ((res.data.data.user_level == 1) || (res.data.data.user_level == 3)) {
          that.setData({
            level: res.data.data.user_level
          })
        }
      }
    })
    if (wx.getUserProfile) {
      this.setData({
        canIUseGetUserProfile: true
      })
    }
  },

  getUserProfile(e) {
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
    // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
    let that=this;
    wx.getUserProfile({
      desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (res) => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true,
        })
        //gsxt把头像等信息传入后端
      }
    })
  },

  popevent: function () {
    wx.showModal({
      title: '提示',
      content: '请先授权登录',
      showCancel: false,

    })
  },

  go_intro() {
    wx.navigateTo({
      url: '/pages/mine/introduction/introduction',
    })
  },

  go_know() {
    wx.navigateTo({
      url: '/pages/mine/knowledge/knowledge',
    })
  },

  go_advice() {
    wx.navigateTo({
      url: '/pages/mine/advice/advice',
    })
  },

  go_handled() {
    wx.navigateTo({
      url: '/pages/mine/handled/handled',
    })
  },

  go_unhandled() {
    wx.navigateTo({
      url: '/pages/mine/unhandled/unhandled',
    })
  },
  code() {
    wx.navigateTo({
      url: '/pages/code/code',
    })
  },
})