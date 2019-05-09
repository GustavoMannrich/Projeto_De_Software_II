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
        <v-list-tile v-for="item in items" :key="item.text" @click="">
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>
              {{ item.text }}
            </v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
        <v-list>
          <v-list-tile v-for="item in items2" :key="item.text" avatar @click="">
            <v-list-tile-avatar>
              <img :src="`https://randomuser.me/api/portraits/men/${item.picture}.jpg`" alt="">
            </v-list-tile-avatar>
            <v-list-tile-title v-text="item.text"></v-list-tile-title>
          </v-list-tile>
        </v-list>
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
      <v-icon class="mx-3">fab fa-youtube</v-icon>
      <v-toolbar-title class="mr-5 align-center">
        <span class="title">GGL - Gerenciamento de conteúdo</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-layout row align-center style="max-width: 650px">
      </v-layout>
    </v-toolbar>
    <v-content>
      <v-container fill-height>
        <v-layout justify-center align-center>          
             <Calendar></Calendar>                    
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
  import Calendar from '@/components/Calendar'
  export default {
    components: {
      Calendar
    },
    data: () => ({
      drawer: null,
      items: [
        { icon: 'trending_up', text: 'Dashboard' },
        { icon: 'subscriptions', text: 'Cursos' },
        { icon: 'history', text: 'History' },
        { icon: 'featured_play_list', text: 'Calendário' },
        { icon: 'watch_later', text: 'Configurações' }
      ]
    }),
    props: {
      source: String
    }
  }
</script>