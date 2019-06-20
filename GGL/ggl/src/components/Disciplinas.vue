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
          <TableEventos v-if="isEvents(content)"/>
          <Files v-if="isFiles(content)"/>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
  import TableEventos from '@/components/TableEventos'
  import Files from '@/components/Files'
  export default {
    props: ['idDisciplina'],
    components:{
      TableEventos,
      Files
    },
    data: () => ({
      fab: false,
      hidden: false,
      tabs: null,
    }),
    methods: {
      isEvents(tab) {
        return tab == 'events';
      },
      isFiles(tab) {
        return tab == 'files'
      }
    }
  }
</script>

<style>
  /* This is for documentation purposes and will not be needed in your application */
  #lateral .v-speed-dial,
  #lateral .v-btn--floating {
    position: absolute;
  }
  #lateral .v-btn--floating {
    margin: 0 0 16px 16px;
  }
</style>