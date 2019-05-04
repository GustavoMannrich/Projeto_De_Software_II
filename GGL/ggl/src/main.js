import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css' // Ensure you are using css-loader
import VueResource from 'vue-resource'
import VueRouter from 'vue-router'

import Login from './components/Login.vue'
import Dashboard from './components/Dashboard.vue'

Vue.use(Vuetify);
Vue.use(VueResource);
Vue.use(VueRouter);

Vue.config.productionTip = false

//const Teste = { template: '<div>dashboard</div>' }

const routes = [
  { path: '/', component: Login },
  { path: '/dashboard', component: Dashboard }
]

const router = new VueRouter({
  mode: 'history',
  routes 
})

new Vue({
  router,
  components: { App },
  render: h => h(App)
}).$mount('#app')