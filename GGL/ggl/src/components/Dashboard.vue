<template>
  <v-app
    id="inspire"
    dark
  >
    <v-navigation-drawer
      v-model="drawer"
      fixed
      clipped
      app
    >
      <v-list dense>
        <v-list-tile v-for="item in items" :key="item.text" @click="goToPage(item)">
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>
              {{ item.text }}
            </v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>        
      </v-list>
    </v-navigation-drawer>
    <v-toolbar
      color="blue"
      dense
      fixed
      clipped-left
      app
    >
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>      
      <v-toolbar-title class="mr-5 align-center">
        <v-btn color="secundary" left flat @click="goToPageByText('Dashboard')"> 
          GGL - Gerenciamento de conteúdo
        </v-btn>        
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-layout row align-center style="max-width: 650px">
      </v-layout>
      <v-btn color="primary" right>
        <span>Lucas Reinert</span>
      </v-btn>
      <v-btn color="secundary" right flat>
        <v-icon>power_settings_new</v-icon>
      </v-btn>
    </v-toolbar>
    <v-content>
  <!--    <v-container fill-height v-if="verifyScreen('Dashboard')">
        <v-layout justify-center align-center v-if="verifyScreen('Dashboard')">          
                            
        </v-layout>
      </v-container>-->
      <Calendar v-if="verifyScreen('Dashboard')"/>           
      <Cursos v-if="verifyScreen('Cursos')"/>
    </v-content>
  </v-app>
</template>

<script>
  import Calendar from '@/components/Calendar'
  import Cursos from '@/components/Cursos'
  
  export default {
    components: {
      Calendar,
      Cursos
    },
    data: () => ({
      drawer: null,
      items: [
        { icon: 'event', text: 'Dashboard' },
        { icon: 'subscriptions', text: 'Cursos' },
        { icon: 'account_circle', text: 'Minha conta' }
      ],
      janela: 'Dashboard'
    }),
    props: {
      source: String
    },
    methods: {
      goToPage(item) {
        this.janela = item.text;
      },
      goToPageByText(ïtem){
        this.janela = item
      },
      verifyScreen(item){            
        return this.janela === item;
      }
    }
  }
</script>