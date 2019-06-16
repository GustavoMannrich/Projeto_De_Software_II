<template>
  <div>
    <!-- <v-toolbar flat color="white"> -->
      <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ on }">
          <!-- <v-btn right class="mb-2" v-on="on"><v-icon>add</v-icon></v-btn> -->
    
          <v-btn fab
            small            
            top
            right
            absolute
            v-on="on">
            <v-icon>add</v-icon>
          </v-btn>
  
        
        
        </template>
        <v-card>
          <v-card-title>
            <span class="headline">{{ formTitle }}</span>
          </v-card-title>

          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                 <!-- data picker -->
                <v-menu>
                  <v-text-field slot="activator" label="Data" prepend-icon="date_range"></v-text-field>
                  <v-date-picker ></v-date-picker>
                </v-menu>
                <v-flex xs12>
                  <v-text-field v-model="editedItem.titulo" label="Titulo"></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field v-model="editedItem.descricao" label="descrição"></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field v-model="editedItem.data" label="data"></v-text-field>
                </v-flex>

               

              </v-layout>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" flat @click="close">Cancel</v-btn>
            <v-btn color="blue darken-1" flat @click="save">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    <!-- </v-toolbar> -->
    <v-data-table
      :headers="headers"
      :items="desserts"
      class="elevation-1"
    >
      <template v-slot:items="props">
        <td>{{ props.item.titulo }}</td>
        <td class="text-xs-left">{{ props.item.descricao }}</td>
        <td class="text-xs-left">{{ props.item.data }}</td>       
        <td class="justify-center layout px-0">
          <v-icon
            small
            class="mr-2"
            @click="editItem(props.item)"
          >
            edit
          </v-icon>
          <v-icon
            small
            @click="deleteItem(props.item)"
          >
            delete
          </v-icon>
        </td>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="initialize">Reset</v-btn>
      </template>
    </v-data-table>
  </div>
</template>

<script>
  export default {
    data: () => ({
      dialog: false,
      headers: [
        { text: 'Titulo', value: 'titulo' },
        { text: 'Descrição', value: 'descricao' },
        { text: 'Data', value: 'data' }
      ],
      desserts: [],
      editedIndex: -1,
      editedItem: {
        titulo: '',
        descricao: '',
        data: ''
      },
      defaultItem: {
        titulo: '',
        descricao: '',
        data: ''
      }
    }),
    computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'Novo evento' : 'Editar evento'
      }
    },
    watch: {
      dialog (val) {
        val || this.close()
      }
    },
    created () {
      this.initialize()
    },
    methods: {
      initialize () {
        this.$http.get("http://localhost:8080/api/eventos/aluno/1",
          { headers: { "content-type": "application/json" } }).then(response => {
            this.desserts = response.body.data.content;
          });
        // this.desserts = [
        //   {
        //     titulo: 'Prova',
        //     desc: 'Assuntos: Emboscada, Inteligência artificial, Salto com vara, Genética',
        //     dataEvento: '13/06/2019'
        //   },
        //   {
        //     titulo: 'Prova II',
        //     desc: 'Assuntos: Física quântica, Futebol, ',
        //     dataEvento: '13/06/2019'
        //   }
        // ]
      },
      editItem (item) {
        this.editedIndex = this.desserts.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },
      deleteItem (item) {
        const index = this.desserts.indexOf(item)
        confirm('Are you sure you want to delete this item?') && this.desserts.splice(index, 1)
      },
      close () {
        this.dialog = false
        setTimeout(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },
      save () {
        this.editedItem.alunoId = localStorage.getItem("user-ID");        
        this.$http.post("http://localhost:8080/api/eventos", 
            '{"alunoId": ' + localStorage.getItem("user-ID") + ', "nome": "' + this.nomeNovoCurso + '"}', 
          { headers: { "content-type": "application/json" } }).then(result => {              
              this.response = result.data;
              alert(this.response);
              this.listarCursos();
          }, error => {
              alert(error);
          }); 
        this.close()
      }
    }
  }
</script>