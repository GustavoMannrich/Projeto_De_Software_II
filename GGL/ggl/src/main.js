import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css' // Ensure you are using css-loader
import VueResource from 'vue-resource'
import VueRouter from 'vue-router'

import Login from './components/Login.vue'
import Dashboard from './components/Dashboard.vue'
import Calendar from './components/Calendar.vue'
import MinhaConta from './components/MinhaConta.vue'
import Disciplinas from './components/Disciplinas.vue'
import Cursos from './components/Cursos.vue'

Vue.use(Vuetify);
Vue.use(VueResource);
Vue.use(VueRouter);

Vue.config.productionTip = false

//const Teste = { template: '<div>dashboard</div>' }

Vue.http.interceptors.push((request, next) => {
  request.headers.set('Authorization', "Bearer " + localStorage.getItem("user-token"))
  request.headers.set('Accept', 'application/json')
  next()
})

const routes = [
  { path: '/', component: Login },
  { path: '/Dashboard/', redirect: '/Dashboard/Calendar', component: Dashboard, 
  children: [
    { path: 'Calendar', component: Calendar },
    { path: 'MinhaConta', component: MinhaConta },
    { path: 'Disciplinas/:disciplinaId', component: Disciplinas },
    { path: 'Cursos', component: Cursos }
  ] }
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