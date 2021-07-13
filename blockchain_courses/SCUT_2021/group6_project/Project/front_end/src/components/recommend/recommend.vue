
<template>
  <div id="migrate">
    
    <div class="placeholder_bottom_include">
      <div class="placeholder_bottom">
        <div class="font-medium-24">医疗物资</div>
        <div class="link_include">
          <div class="link font-medium-18">更多</div>
          <img class="link_img"
               :src="require(`@asset/image/ic_go.png`)"
               alt="">
        </div>
      </div>
    </div>
    
    
      <el-row :gutter="20"
              class="albumLayout">
        <el-col :span="6"
                v-for="(data,index) in albumList"
                :key="index">
          <div class="albumLayout_content">
            <div>
              <!-- <img :src="data.coverImg" alt=""> -->
              <albumbox :value1="data.coverImg1"
                        :value2="data.coverImg2"
                        :value3="data.coverImg3"
                        :name="data.name"></albumbox>
            </div>
            <p class="font-regular-18">{{data.name}}</p>
          </div>
        </el-col>
      </el-row>
  
  
  </div>
</template>

<script>
export default {
  name: 'recommend',
  data() {
    return {
      change:0,

      flag_4: 100,
      flag4:false,
      albumList: [
        {
          name: '口罩',
          coverImg1: require('@asset/image/albums/口罩/img1.jpg'),
          coverImg2: require('@asset/image/albums/口罩/img2.jpg'),
          coverImg3: require('@asset/image/albums/口罩/img3.jpg')
        },
        {
          name: '纱布',
          coverImg1: require('@asset/image/albums/纱布/img1.jpg'),
          coverImg2: require('@asset/image/albums/纱布/img2.jpg'),
          coverImg3: require('@asset/image/albums/纱布/img3.jpg')
        },
        {
          name: '创可贴',
          coverImg1: require('@asset/image/albums/创可贴/img1.jpg'),
        },
        {
          name: '防护服',
          coverImg1: require('@asset/image/albums/防护服/img1.jpg')
        },
        {
          name: '呼吸机',
          coverImg1: require('@asset/image/albums/呼吸机/img1.jpg')
        },
        {
          name: '红外体温计',
          coverImg1: require('@asset/image/albums/红外体温计/img1.jpg')
        },
        {
          name: '洗手液',
          coverImg1: require('@asset/image/albums/洗手液/img1.jpg')
        },
        {
          name: 'COVID-19检测试剂',
          coverImg1: require('@asset/image/albums/COVID-19检测试剂/img1.jpg')
        },
      
      ]
    }
  },
  methods:{

  },

  mounted(){
  },

  destroyed() {
  },

}
</script>

<style scoped>
.font-regular-18 :hover{
  cursor:pointer
}
.albumLayout_content :hover{
  transform: scale(1.1);
}
.placeholder_bottom_include {
  width: 1200px;
  margin-top: 20px;
}
.placeholder_bottom {
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
  background-color: #ffffff;
  height: 80px;
}
.link_include {
  display: flex;
}
.link_include img {
  width: 30px;
  height: 30px;
}
.link {
  padding-top: 5px;
  padding-right: 10px;
  color: #302fa6;
}

.albumLayout {
  width: 1200px;
}
.albumLayout_content {
  margin-top: 40px;
}
.albumLayout_content div {
  width: 285px;
  height: 190px;
  border-radius: 20px;
}
.albumLayout_content p {
  margin-top: 20px;
  margin-left: 20px;
}

.albumLayout_content div:hover{
  background: #ECF5FF;
}

</style>