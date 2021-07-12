App({
  onShow: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    wx.checkSession({
        success: function () {
          //session 未过期，并且在本生命周期一直有效
        },
        fail: function () {
          //登录态过期
          wx.login() //重新登录
        }
      }),
      // 获取用户信息
      wx.getSetting({
        success: res => {
          if (res.authSetting['scope.userInfo']) {
            wx.getUserInfo({
              success: res => {
                if (this.userInfoReadyCallback) {
                  this.userInfoReadyCallback(res)
                }
              }
            })
          }
        }
      })
  },
  onLaunch: function () {
    this.login();
  },
  login: function () {
    var that = this
    wx.login({
      success: (res) => {
        let code = res.code;
        wx.request({
          url: "https://api.weixin.qq.com/sns/jscode2session?appid=wxce04f78054d6091a&secret=f35a59240b2e57dc7fc104202e7264f8&js_code=" + code + "&grant_type=authorization_code",
          method: 'GET',
          success: function (res) {
            that.globalData.openid = res.data.openid;
            wx.request({
              url: 'http://121.4.27.229:8080/v1/usr/user/insert_User',
              method: 'POST',
              data: {
                mp_openid: that.globalData.openid,
                nick_name: " ",
                image_url: " ",
              },
              success: function (res) {
                if (res.data == 1) {
                  wx.showToast({
                    title: '欢迎使用天下溯源系统！',
                    icon: 'none',
                    duration: 1000
                  })
                }
              }
            })
          },
        })

      }
    })
  },

  globalData: {
    openid: 0
  }
})