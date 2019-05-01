<template>
<div id="app">
  <Login/>
</div>

</template>

<script>
import Login from '@/components/Login'
import Dashboard from '@/components/Dashboard'
import Calendar from '@/components/Calendar'
import SignUp from '@/components/SignUp'

export default {
   name: 'app',
   components:{
     Login,
     Dashboard,
     Calendar,
     SignUp
   },
   data(){
     return{
       drawer: true
     }
   }
}


</script>