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
        <v-icon
          dark
          
          
        >
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
        
        <v-icon          
          dark
        >
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
          <template v-slot:day="{ date }">
            <template v-for="event in eventsMap[date]">
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
                    <v-btn icon @click="editarEvento(event)">
                      <v-icon>edit</v-icon>
                    </v-btn>
                    <v-btn icon>
                      <v-icon @click="removerEvento(event)">delete</v-icon>
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
                      Cancel
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </template>
          </template>
        </v-calendar>
      </v-sheet>
    </v-flex>
    
  </v-layout>
</template>

<script>
  export default {
    data: () => ({
      today: undefined,      
      events: [
        {
          title: 'Vacation',
          details: 'Going to the beach!',
          date: '2018-12-30',
          open: false
        },
        {
          title: 'Vacation',
          details: 'Going to the beach!',
          date: '2018-12-31',
          open: false
        },
        {
          title: 'Vacation',
          details: 'Going to the beach!',
          date: '2019-01-01',
          open: false
        },
        {
          title: 'Meeting',
          details: 'Spending time on how we do not have enough time',
          date: '2019-01-07',
          open: false
        },
        {
          title: '30th Birthday',
          details: 'Celebrate responsibly',
          date: '2019-01-03',
          open: false
        },
        {
          title: 'New Year',
          details: 'Eat chocolate until you pass out',
          date: '2019-01-01',
          open: false
        },
        {
          title: 'Conference',
          details: 'Mute myself the whole time and wonder why I am on this call',
          date: '2019-01-21',
          open: false
        },
        {
          title: 'Hackathon',
          details: 'Code like there is no tommorrow',
          date: '2019-06-01',
          open: false
        }
      ]
    }),
    computed: {
      // convert the list of events into a map of lists keyed by date
      eventsMap () {
        
        const map = {}
        this.events.forEach(e => (map[e.date] = map[e.date] || []).push(e))
        return map
      }
    },
    created () {
      this.initialize()
    },
    methods: {
      initialize() { 
       /*  this.$http.get("http://localhost:8080/api/eventos/aluno/1",
          { headers: { "content-type": "application/json" } }).then(response => {
            this.events = response.body.data.content;
          }); */
          this.today = this.getCurrentDate();        
          //this.mes   = this.getcurrentDate[1];
          
        },
     /* open (event) {
        alert(event.title)
      },*/
      editarEvento(event) {
          // Edita o evento
          this.$http.put("http://localhost:8080/api/eventos/" + localStorage.getItem("user-ID"), 
            '{"alunoId": ' + localStorage.getItem("user-ID") + ', "data": "' + event.date + '", "descricao": "' + event.details + '", "titulo": "' + event.title + '"}', 
          { headers: { "content-type": "application/json" } }).then(result => {              
              this.response = result.data;
              alert(this.response);
          }, error => {
              alert(error);
          });
      },
      removerEvento(event) {
          // Remove o evento
          this.$http.delete("http://localhost:8080/api/eventos/" + localStorage.getItem("user-ID"), 
          { headers: { "content-type": "application/json" } }).then(result => {              
              this.response = result.data;
              alert(this.response);
          }, error => {
              alert(error);
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