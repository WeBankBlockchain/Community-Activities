var QRCode = require('../../utils/weapp-qrcode')
var qrcode;
var app = getApp()

Page({
    data: {
        text: app.globalData.openid
    },
    onLoad: function (options) {
        var that = this
        qrcode = new QRCode('canvas', {
            // usingIn: this,
            text: that.data.text,
            width: 200,
            height: 200,
            colorDark: "black",
            colorLight: "white",
            correctLevel: QRCode.CorrectLevel.H,
        });
    },
    confirmHandler: function (e) {
        var value = e.detail.value
        qrcode.makeCode(value)
    },
    inputHandler: function (e) {
        var value = e.detail.value
        this.setData({
            text: value
        })
    },
    tapHandler: function () {
        qrcode.makeCode(this.data.text)
    },
    // 长按保存
    save: function () {
        console.log('save')
        wx.showActionSheet({
            itemList: ['保存图片'],
            success: function (res) {
                console.log(res.tapIndex)
                if (res.tapIndex == 0) {
                    qrcode.exportImage(function (path) {
                        wx.saveImageToPhotosAlbum({
                            filePath: path,
                        })
                    })
                }
            }
        })
    }
})