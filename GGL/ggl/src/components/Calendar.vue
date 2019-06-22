<template>
  <v-layout wrap>
    <v-toolbar color="light-blue" light>
        <v-toolbar-title class="white--text">Calendário</v-toolbar-title>
    </v-toolbar>
    <v-flex
      sm4
      xs12
      class="text-sm-left text-xs-center"
    >
      <v-btn fab small @click="$refs.calendar.prev()">
        <v-icon dark>
          keyboard_arrow_left
        </v-icon>
        
      </v-btn>
    </v-flex>
    <v-flex
      sm4
      xs12
      class="text-xs-center"
      
    >
    <h1> <span> {{getCurrentMonth()}} </span> </h1>
    
    </v-flex>
    <v-flex
      sm4
      xs12
      class="text-sm-right text-xs-center"      
    >
      <v-btn fab small  @click="$refs.calendar.next()">
        
        <v-icon dark>
          keyboard_arrow_right
        </v-icon>
      </v-btn>
    </v-flex>    
    <v-flex
      xs12
      class="mb-3">
      <v-sheet height="500">
        <v-calendar
          ref="calendar"
          :value="today"
          color="primary"
          locale="pt-br"
          v-model="today"
          dark         
        >
          <template v-slot:day="day">
            <template v-for="event in events.filter(item => item.data === day.date)">
            <div :key="event.titulo">    
              <v-dialog :key="event.titulo" v-model="event.open" max-width="600px">
                <template v-slot:activator="{ on }">
                  <div
                    v-if="!event.time"
                    v-ripple
                    class="my-event"
                    v-on="on"
                    v-html="event.titulo"
                  ></div>
                </template>
                <v-card
                  color="grey lighten-4"
                  min-width="350px"
                  flat
                >
                  <v-toolbar
                    color="primary"
                  >                    
                    <v-toolbar-title v-html="event.titulo"></v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn fab flat icon ripple @click="editarEvento(event)">
                      <v-icon>edit</v-icon>
                    </v-btn>
                    <v-btn fab flat icon ripple @click="removerEvento(event)">
                      <v-icon>delete</v-icon>
                    </v-btn>
                  </v-toolbar>
                  <v-card-title primary-title>
                    <v-text-field light v-model="event.descricao" solo></v-text-field>
                  </v-card-title>
                  <v-card-actions>
                    <v-btn
                      flat
                      color="primary"
                      @click="event.open = false"
                    >
                      Cancelar
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </div>
            </template>
          </template>
        </v-calendar>     
      </v-sheet>
    </v-flex>
    
  </v-layout>
</template>

<script>
  export default {
    data: vm => ({
      today: undefined,      
      buscouEventos: false,
      date: new Date().toISOString().substr(0, 10),
      dateFormatted: vm.formatDate(new Date().toISOString().substr(0, 10)),
      events: []
    }),
    beforeMount() {
        this.listarEventos();
        this.initialize()
    },
    created () {
      
    },
    methods: {
    initialize() {
        this.today = this.getCurrentDate();          
    },
    listarEventos(){           
      this.$http.get("http://localhost:8080/api/eventos/aluno/" + localStorage.getItem("user-ID"),
      { headers: { "content-type": "application/json" } }).then(response => {
            this.events = [];

            response.data.data.content.forEach(e => {
                e[open] = false;
                this.events.push(e);
            });

        /* result.body.data.content.forEach(e => {      
            e[open] = false;     
            this.events.push(e);
        });*/
      })              
    },    
    editarEvento(event) {
      // Edita o evento      
      this.$http.put("http://localhost:8080/api/eventos/" + event.id, 
        '{"disciplinaId": ' + event.disciplina_Id + ', "data": "' + event.data + '", "descricao": "' + event.descricao + '", "titulo": "' + event.titulo + '"}', 
      { headers: { "content-type": "application/json" } }).then(result => {              
          this.response = result.data;
          this.listarEventos();
          //alert(this.response);
      }, error => {
          //alert(error);
      });
    },
    removerEvento(event) {
        // Remove o evento
        this.$http.delete("http://localhost:8080/api/eventos/" + event.id, 
        { headers: { "content-type": "application/json" } }).then(result => {              
            this.response = result.data;
            this.listarEventos();
            //alert(this.response);
        }, error => {
            //alert(error);
        });
    },
    getCurrentDate(){
      var hoje = new Date();
      var dd = String(hoje.getDate()).padStart(2, '0');
      var mm = String(hoje.getMonth() + 1).padStart(2, '0'); //January is 0!
      var yyyy = hoje.getFullYear();

      hoje = mm + '/' + dd + '/' + yyyy;
      return hoje;
    },
    getCurrentMonth(){                
      var meses = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];     
      var hoje = this.today === undefined ? new Date() : new Date(this.today);
      return meses[hoje.getMonth()] + ' de ' + hoje.getFullYear().toString();
    },
    formatDate (date) {
      if (!date) return null
      const [year, month, day] = date.split('-')
      return `${day}/${month}/${year}`
    },
      parseDate (date) {
      if (!date) return null
      const [month, day, year] = date.split('/')
      return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
    } 
    }
  }
</script>

<style lang="stylus" scoped>
  .my-event {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    border-radius: 2px;
    background-color: #1867c0;
    color: #ffffff;
    border: 1px solid #1867c0;
    width: 100%;
    font-size: 12px;
    padding: 3px;
    cursor: pointer;
    margin-bottom: 1px;
  }
</style>

