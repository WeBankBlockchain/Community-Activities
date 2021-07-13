
<template>
  <div id="headfooter">
    <!-- 核心部分,实现了主要数据的显示 -->
    <div class="content">
      <div class="contentIn"
           v-for="(item, i) in footList"
           :key="i">
        <div class="contentInTitle font-medium-white-18">
          {{ item.title }}
        </div>
        <div class="contentInItems font-regular-white-14"
             v-for="(value, key, j) in item.items"
             :key="j">
          <a :href="value">{{ key }}</a>
        </div>
      </div>
    </div>

    <div class="justLine">
    </div>
    <div class="down">
      <div class="downIn font-regular-white-12">
        <div class="one">MST | 占位符 | 占位符 | 占位符 | 占位符</div>
        <div class="two">华南理工大学 | 软件学院 | 卓越二班 | FISCO_BCOS</div>
        <div class="three">
          <b>
            &copy;2021 MST占位占位 | XXXXXXXXXXX号 !--
            <a href="http://fisco-bcos.org/zh/">占位占 位</a> --
          </b>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'headfooter',
  data() {
    return {
      footList: [
        {
          title: '占位占位',
          items: {
            占位占位1: 'https://www.baidu.com/',
            占位占位2: 'https://www.baidu.com/',
            占位占位3: 'https://www.baidu.com/',
            占位占位4: 'https://www.baidu.com/',
            占位占位5: 'https://www.baidu.com/'
          }
        },
        {
          title: '占位占位',
          items: {
            占位占位1: 'https://www.baidu.com/',
            占位占位2: 'https://www.baidu.com/',
            占位占位3: 'https://www.baidu.com/',
            占位占位4: 'https://www.baidu.com/'
          }
        },
        {
          title: '占位占位',
          items: {
            占位占位1: 'https://www.baidu.com/',
            占位占位2: 'https://www.baidu.com/',
            占位占位3: 'https://www.baidu.com/',
            占位占位4: 'https://www.baidu.com/',
            占位占位5: 'https://www.baidu.com/'
          }
        },
        {
          title: '占位占位',
          items: {
            占位占位1: 'https://www.baidu.com/',
            占位占位2: 'https://www.baidu.com/',
            占位占位3: 'https://www.baidu.com/',
            占位占位4: 'https://www.baidu.com/'
          }
        }
      ]
    }
  }
}
</script>

<style scoped>
* {
  color: #ffffff;
}
#headfooter {
  width: 1920px;
  height: 428px;
  background-color: #252525;
  overflow: hidden;
  position: relative;
}
.content {
  width: 800px;
  height: 198px;
  position: absolute;
  margin-top: 60px;
  left: 50%;
  transform: translate(-50%, 0);
}
.contentIn {
  width: 200px;
  height: 198px;
  float: left;
}
.contentInTitle {
  margin-left: 20px;
}
.contentInItems {
  margin-left: 20px;
  margin-top: 20px;
}
.contentInItems a {
  text-decoration: none;
  color: rgba(255, 255, 255, 0.4);
}

.justLine {
  margin: auto;
  width: 62.5%;
  height: 1px;
  position: absolute;
  margin-top: 318px;
  left: 50%;
  transform: translate(-50%, 0);
  background-color: #ffffff;
}

.down {
  width: 800px;
  height: 109px;
  position: absolute;
  margin-top: 318px;
  left: 50%;
  transform: translate(-50%, 0);
}
.downIn {
  display: flex;
  /* 使用 flex-direction: column; 这个失效*/
  /* justify-content: center; */
  align-items: center;
  flex-direction: column;
  width: auto;
  height: 94px;
  line-height: 17px;
}
.one {
  position: relative;
  margin-top: 20px;
}
.two {
  position: relative;
  margin-top: 5px;
}
.three {
  position: relative;
  margin-top: 10px;
}

</style>
