<template>
  <v-layout row>
    <v-flex offset>
      <v-card>
        <v-toolbar color="light-blue" light>
          <v-toolbar-title class="white--text">Cursos</v-toolbar-title>
          <v-btn fab small color="cyan accent-2" bottom right absolute @click="dialog = !dialog">
            <v-icon>add</v-icon>
          </v-btn>
        </v-toolbar>
        <v-list one-line subheader>     
            <v-subheader inset style="height: 20px"></v-subheader>       
            <v-list-group
                v-for="item in items"
                :key="item.nome"
                v-model="item.mostrarDisciplinas"
                no-action
            >
                <template v-slot:activator>
                <v-list-tile>
                    <v-list-tile-avatar>
                    <v-icon :class="'grey lighten-1 white--text'">{{ 'folder' }}</v-icon>
                    </v-list-tile-avatar>
                    <v-list-tile-content>
                    <v-list-tile-title>{{ item.nome }}</v-list-tile-title>
                    </v-list-tile-content>
                    <v-btn fab flat small @click="">
                    <v-icon>add_alarm</v-icon>
                    </v-btn>
                    <v-dialog :key="item.id" v-model="item.open" max-width="600px">
                    <template v-slot:activator="{ on }">
                        <div v-if="!item.time" v-ripple v-on="on">
                        <v-btn fab flat small @click="editCursos(item)">
                            <v-icon>edit</v-icon>
                        </v-btn>
                        </div>
                    </template>
                    <v-card min-width="350px" flat>
                        <v-card-title primary-title>
                        <v-text-field :label="item.nome" v-model="nomeAlterarCurso"></v-text-field>
                        </v-card-title>
                        <v-card-actions>                  
                        <v-spacer></v-spacer>
                        <v-btn flat color="primary" @click="item.open = false">Cancelar</v-btn>
                        <v-btn flat color="secundary" @click="editarCurso(item)">Salvar</v-btn>
                        </v-card-actions>
                    </v-card>
                    </v-dialog>
                    <v-btn fab flat small @click="removerCurso(item)">
                    <v-icon>delete</v-icon>
                    </v-btn>
                </v-list-tile>
                </template>
                <v-list-tile
                v-for="subItem in item.disciplinas"
                :key="subItem.nome"
              >
                <v-list-tile-content>
                  <v-list-tile-title @click="goToPage('Disciplinas')">{{ subItem.nome }}</v-list-tile-title>
                </v-list-tile-content>
              </v-list-tile>
            </v-list-group>

          <!--<v-subheader inset style="height: 20px"></v-subheader>
          <v-list-tile v-for="item in items" :key="item.nome" avatar>
            <v-list-tile-avatar>
              <v-icon :class="'grey lighten-1 white--text'">{{ 'folder' }}</v-icon>
            </v-list-tile-avatar>
            <v-list-tile-content>
              <v-list-tile-title>{{ item.nome }}</v-list-tile-title>
            </v-list-tile-content>
            <v-btn fab flat small @click="">
              <v-icon>add_alarm</v-icon>
            </v-btn>
            <v-dialog :key="item.id" v-model="item.open" max-width="600px">
              <template v-slot:activator="{ on }">
                <div v-if="!item.time" v-ripple v-on="on">
                  <v-btn fab flat small @click="editCursos(item)">
                    <v-icon>edit</v-icon>
                  </v-btn>
                </div>
              </template>
              <v-card min-width="350px" flat>
                <v-card-title primary-title>
                  <v-text-field :label="item.nome" v-model="nomeAlterarCurso"></v-text-field>
                </v-card-title>
                <v-card-actions>                  
                  <v-spacer></v-spacer>
                  <v-btn flat color="primary" @click="item.open = false">Cancelar</v-btn>
                  <v-btn flat color="secundary" @click="editarCurso(item)">Salvar</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-btn fab flat small @click="removerCurso(item)">
              <v-icon>delete</v-icon>
            </v-btn>
            
          </v-list-tile>-->
        </v-list>
        <v-dialog v-model="dialog" max-width="500px">
          <v-card>
            <v-card-text>
              <v-text-field label="Curso" v-model="nomeNovoCurso"></v-text-field>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn flat color="primary" @click="dialog = false">Cancelar</v-btn>
              <v-btn flat color="secundary" @click="adicionarCurso">Criar curso</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  name: "cursos",
  data() {
    return {
      dialog: false,
      nomeNovoCurso: "",
      nomeAlterarCurso: "",
      dialogAlterar: false,
      items: []
      //[
      //  { icon: 'folder', iconClass: 'grey lighten-1 white--text', title: 'Sistemas de informação' },
      //  { icon: 'folder', iconClass: 'grey lighten-1 white--text', title: 'Inglês' },
      //  { icon: 'folder', iconClass: 'grey lighten-1 white--text', title: 'Espanhol' }
      //]
    };
  },
  methods: {
    editCursos() {
      this.dialogAlterar = true;
    },
    adicionarCurso() {
      // Adicionar novo curso
      this.$http
        .post(
          "http://localhost:8080/api/cursos",
          '{"alunoId": ' +
            localStorage.getItem("user-ID") +
            ', "nome": "' +
            this.nomeNovoCurso +
            '"}',
          { headers: { "content-type": "application/json" } }
        )
        .then(
          result => {
            this.response = result.data;
            console.log(this.response);
            this.listarCursos();
          },
          error => {
            console.error(error);
          }
        );

      this.dialog = false;
    },
    listarCursos() {
      // busca a lista de cursos cadastrados
      this.$http
        .get(
          "http://localhost:8080/api/cursos/aluno/" +
            localStorage.getItem("user-ID"),
          { headers: { "content-type": "application/json" } }
        )
        .then(
          result => {
            this.response = result.data;

            this.items = [];

            result.data.data.content.forEach(e => {
                
                e["open"] = false;
                e["mostrarDisciplinas"] = false;      

                this.listarDisciplinas(e);
    
                this.items.push(e);                       
            });

            console.log(this.response);
          },
          error => {
            console.error(error);
          }
        );
    },
    editarCurso(curso) {
      // Edita o evento
      this.$http
        .put(
          "http://localhost:8080/api/cursos/" + curso.id,
          '{"alunoId": ' +
            localStorage.getItem("user-ID") +
            ', "nome": "' +
            this.nomeAlterarCurso +
            '"}',
          { headers: { "content-type": "application/json" } }
        )
        .then(
          result => {
            this.response = result.data;
            console.log(this.response);
            this.listarCursos();
          },
          error => {
            console.error(error);
          }
        );

      curso.open = false;
    },
    removerCurso(curso) {
      // Remove o evento
      this.$http
        .delete("http://localhost:8080/api/cursos/" + curso.id, {
          headers: { "content-type": "application/json" }
        })
        .then(
          result => {
            this.response = result.data;
            console.log(this.response);
            this.listarCursos();
          },
          error => {
            console.error(error);
          }
        );
    },
    listarDisciplinas(curso){
        // busca a lista de cursos cadastrados
      this.$http.get(
          "http://localhost:8080/api/disciplina/aluno/" + localStorage.getItem("user-ID") + "/" + curso.id,
          { headers: { "content-type": "application/json" } }
        )
        .then(
          result => {
            this.response = result.data;            
            console.log(this.response);
            curso["disciplinas"] = result.data.data.content;
          },
          error => {
            console.error(error);            
          }
        );

        curso["disciplinas"] = [];
    },
    goToPage(where) {       
      if (this.$router.currentRoute.path.includes("/Dashboard/")) 
        where = where.replace(/\s/g, '');         
      else 
        where = "Dashboard/" + where.replace(/\s/g, '');  
      

      this.$router.push(where)
    }
  },
  beforeMount() {
    this.listarCursos();
  }
};
</script>