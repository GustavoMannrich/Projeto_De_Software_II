<template>
<div id="app">
  <Calendar/>
</div>

</template>

<script>
import Login from '@/components/Login'
import Dashboard from '@/components/Dashboard'
import Calendar from '@/components/Calendar'

export default {
   name: 'app',
   components:{
     Login,
     Dashboard,
     Calendar
   },
   data(){
     return{
       drawer: true
     }
   }
}


</script>