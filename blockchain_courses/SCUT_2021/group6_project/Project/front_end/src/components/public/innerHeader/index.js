import LoginHeader from './innerHeader.vue'
const innerHeader = {
  install: function (Vue) {
    Vue.component('innerHeader', LoginHeader)
  }
}
export default innerHeader