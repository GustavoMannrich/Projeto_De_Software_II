<template>
  <div id="lateral">
    <v-toolbar dark tabs flat color="indigo">
      <v-toolbar-title>{{idDisciplina}}</v-toolbar-title>
      
      <template v-slot:extension>
        <v-tabs
          v-model="tabs"
          align-with-title
          color="transparent"
        >
          <v-tab href="#events">Eventos</v-tab>
          <v-tab href="#files">Arquivos</v-tab>
          <v-tabs-slider color="pink"></v-tabs-slider>
        </v-tabs>
      </template>
    </v-toolbar>
    <v-tabs-items v-model="tabs">
      <v-tab-item
        v-for="content in ['events', 'files']"
        :key="content"
        :value="content"
      >
        <v-card height="200px" flat>                        
          <TableEventos v-if="isEvent(content)"/>
          <!--<Calendar v-if="isCalendar(content)"/>-->
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
  import TableEventos from '@/components/TableEventos'
  import Calendar from '@/components/Calendar'
  export default {
    props: ['idDisciplina'],
    components:{
      TableEventos,
      Calendar
    },
    data: () => ({
      fab: false,
      hidden: false,
      tabs: null,
    }),
    methods: {
      isEvent(tab) {
        return tab == 'events';
      },
      isCalendar(tab) {
        return tab == 'files'
      }
    }
  }
</script>
