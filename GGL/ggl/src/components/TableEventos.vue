<template>
  <div>
    <!-- <v-toolbar flat color="white"> -->
      <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ on }">
          <v-btn fab small top right absolute color="cyan accent-2" v-on="on">
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
                <v-flex xs12>
                  <v-text-field v-model="editedItem.titulo" label="Titulo"></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field v-model="editedItem.descricao" label="Descrição"></v-text-field>
                </v-flex>
                <v-flex xs12 lg6>
                  <v-menu
                    ref="menu1"
                    v-model="menu1"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    lazy
                    transition="scale-transition"
                    offset-y
                    full-width
                    max-width="290px"
                    min-width="290px"
                  >
                    <template v-slot:activator="{ on }">
                      <v-text-field
                        v-model="dateFormatted"
                        label="Date"
                        hint="MM/DD/YYYY format"
                        persistent-hint
                        prepend-icon="event"
                        @blur="date = parseDate(dateFormatted)"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker v-model="date" no-title @input="menu1 = false"></v-date-picker>
                  </v-menu>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" flat @click="close">Cancelar</v-btn>
            <v-btn color="blue darken-1" flat @click="CadastrarEvento(idDisciplina)">Salvar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    <v-data-table
      :headers="headers"
      :items="eventos"
      class="elevation-1"
    >
      <template v-slot:items="props">
        <td>{{ props.item.titulo }}</td>
        <td class="text-xs-left">{{ props.item.descricao }}</td>
        <td class="text-xs-left" v-html="formatDate(props.item.data)"></td>       
        <td class="justify-center layout px-0">
          <v-btn small fab flat icon ripple @click="editItem(props.item)">
            <v-icon>edit</v-icon>
          </v-btn>
          <v-btn small fab flat icon ripple @click="removerEvento(props.item)">
            <v-icon>delete</v-icon>
          </v-btn>
        </td>
      </template>    
    </v-data-table>
  </div>
</template>

<script>
  export default {
    props: {
      idDisciplina: ""
    },
    data: vm => ({
      dialog: false,
      date: new Date().toISOString().substr(0, 10),
      dateFormatted: vm.formatDate(new Date().toISOString().substr(0, 10)),
      menu1: false,
      headers: [
        { text: 'Titulo', value: 'titulo' },
        { text: 'Descrição', value: 'descricao' },
        { text: 'Data', value: 'data' }
      ],
      eventos: [],
      editedIndex: -1,
      editando: false,
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
      },
      computedDateFormatted () {
        return this.formatDate(this.date)
      }
    },
    watch: {
      date (val) {
        this.dateFormatted = this.formatDate(this.date)
      }
    },
    created () {
      this.carregarEventos()
    },
    methods: {
      carregarEventos () {
        this.$http.get("http://localhost:8080/api/eventos/disciplina/" + this.idDisciplina,
          { headers: { "content-type": "application/json" } }).then(response => {
            this.eventos = response.body.data.content;
            return;
          });

        this.eventos = [];
      },
      editItem (item) {  
        this.editando = true;        
        this.editedItem = Object.assign({}, item)
        this.dialog = true;
      },
      removerEvento(event) {
        // Remove o evento
        this.$http.delete("http://localhost:8080/api/eventos/" + event.id, 
        { headers: { "content-type": "application/json" } }).then(result => {              
            this.response = result.data;
            this.carregarEventos();
            //alert(this.response);
        }, error => {
            //alert(error);
        });
      },
      close () {
        this.dialog = false
        setTimeout(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },
      CadastrarEvento (idDisciplina) {         
        if (this.editando) {
          this.$http.put("http://localhost:8080/api/eventos/" + this.editedItem.id, 
            '{"disciplinaId": ' + idDisciplina + ', "data": "' + this.date + '", "descricao": "' + this.editedItem.descricao + '", "titulo": "' + this.editedItem.titulo + '"}', 
          { headers: { "content-type": "application/json" } }).then(result => {
              this.response = result.data;
              //alert('sucesso' + this.response);
              this.carregarEventos();
          }, error => {
              //alert(error);
          });

          this.editando = false;
        } else {
          this.$http.post("http://localhost:8080/api/eventos", 
            '{"disciplinaId": ' + idDisciplina + ', "data": "' + this.date + '", "descricao": "' + this.editedItem.descricao + '", "titulo": "' + this.editedItem.titulo + '"}', 
          { headers: { "content-type": "application/json" } }).then(result => {
              this.response = result.data;
              //alert('sucesso' + this.response);
              this.carregarEventos();
          }, error => {
              //alert(error);
          });
        }
        
        this.close()
      },
      formatDate (date) {
        if (!date) return null
        const [year, month, day] = date.split('-')
        return `${day}/${month}/${year}`
      },
      parseDate (date) {
        if (!date) return null
        const [day, month, year] = date.split('/')
        return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
      }
    }
  }
</script>