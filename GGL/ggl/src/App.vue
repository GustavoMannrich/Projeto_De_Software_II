<template>
<div id="app">
  <router-view></router-view>
</div>

</template>

<script>
import Login from '@/components/Login'
import Dashboard from '@/components/Dashboard'
import Calendar from '@/components/Calendar'
import SignUp from '@/components/SignUp'
import Cursos from '@/components/Cursos'
import Disciplinas from '@/components/Disciplinas'

export default {
   name: 'app',
   components:{
     Login,
     Dashboard,
     Calendar,
     SignUp,
     Cursos,
     Disciplinas
   },
   data(){
     return{
       drawer: true
     }
   }
}


</script>