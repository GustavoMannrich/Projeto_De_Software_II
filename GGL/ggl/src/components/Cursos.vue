<template>
  <v-layout row>
    <v-flex offset>
      <v-card>
        <v-toolbar color="light-blue" light >         
          <v-toolbar-title class="white--text">Cursos</v-toolbar-title>
          <v-btn
            fab
            small
            color="cyan accent-2"
            bottom
            right
            absolute
            @click="dialog = !dialog"
          >
            <v-icon>add</v-icon>
          </v-btn>        
        </v-toolbar>
        <v-list one-line subheader>
          <v-subheader inset style="height: 20px"></v-subheader>
          <v-list-tile v-for="item in items" :key="item.title" avatar @click="">
            <v-list-tile-avatar>
              <v-icon :class="[item.iconClass]">{{ item.icon }}</v-icon>
            </v-list-tile-avatar>
            <v-list-tile-content>
              <v-list-tile-title>{{ item.title }}</v-list-tile-title>
            </v-list-tile-content>
            <v-btn fab flat small @click="editCursos(item.title)">
              <v-icon>edit</v-icon>
            </v-btn>
            <v-btn fab flat small>
              <v-icon>delete</v-icon>
            </v-btn>
          </v-list-tile>
        </v-list>
        <v-dialog v-model="dialog" max-width="500px">
          <v-card>
            <v-card-text>
              <v-text-field label="Curso" v-model="dialogText"></v-text-field>              
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn flat color="primary" @click="dialog = false">Cancelar</v-btn>
              <v-btn flat color="secundary" @click="dialog = false">Criar curso</v-btn>             
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
  export default {
    name: 'cursos',
    data () {
      return {
        dialog: false,
        dialogText: '',
        items: [
          { icon: 'folder', iconClass: 'grey lighten-1 white--text', title: 'Sistemas de informação', subtitle: 'Jan 9, 2014' },
          { icon: 'folder', iconClass: 'grey lighten-1 white--text', title: 'Inglês', subtitle: 'Jan 17, 2014' },
          { icon: 'folder', iconClass: 'grey lighten-1 white--text', title: 'Espanhol', subtitle: 'Jan 28, 2014' }
        ]
      }
    },
    methods:{
      editCursos(curso){
        debugger
        this.dialogText = curso
      } 
    }
  }
</script>