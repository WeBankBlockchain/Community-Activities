var app = getApp
Page({
  data: {
    time:'2021/7/2',
    sender_id:'123qwe',
    recevier_id:'123jpg',
    sender_address:'scut',
    recevier_address:'scut'
  },

  onLoad:function(options){
    const eventChannel = this.getOpenerEventChannel()
    let that = this
    eventChannel.on('acceptDataFromA', function (data) {
      console.log(data)
      that.setData({
        time: data.time,
        sender_id: data.sender_id,
        recevier_id: data.recevier_id,
        sender_address: data.sender_address,
        recevier_address: data.recevier_address,
      })
    })
  }
})