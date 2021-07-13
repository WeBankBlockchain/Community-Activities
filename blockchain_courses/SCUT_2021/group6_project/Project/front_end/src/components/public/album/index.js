import Album from './Album.vue'
const albumbox = {
  install: function (Vue) {
    Vue.component('albumbox', Album)
  }
}
export default albumbox