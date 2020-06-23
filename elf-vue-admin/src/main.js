import Vue from 'vue'

import Cookies from 'js-cookie'
import Element from 'element-ui'

import App from './App.vue'

import store from './store'
import router from './router/routers'

import './assets/styles/element-variables.scss'
// global css
import './assets/styles/index.scss'
//icon
import './assets/icons'
import './router/index' // permission control

Vue.use(Element, {
  size: Cookies.get('size') || 'small' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
