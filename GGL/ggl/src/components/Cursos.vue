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
                :key="item.id"
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
                    <v-dialog :key="item.id" v-model="item.cadastrarDisciplina" max-width="500px">
                        <template v-slot:activator="{ on }">
                            <div v-ripple v-on="on">
                                <v-btn fab flat icon ripple small>
                                    <v-icon>add</v-icon>
                                </v-btn>
                            </div>
                        </template>
                        <v-card>
                        <v-card-text>
                        <v-text-field label="Disciplina" v-model="nomeNovaDisciplina"></v-text-field>
                        </v-card-text>
                        <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn flat color="primary" @click="item.cadastrarDisciplina = false">Cancelar</v-btn>
                        <v-btn flat color="secundary" @click="adicionarDisciplina(item)">Criar Disciplina</v-btn>
                        </v-card-actions>
                        </v-card>
                    </v-dialog>
                    <v-dialog :key="item.id" v-model="item.open" max-width="600px">
                    <template v-slot:activator="{ on }">
                        <div v-ripple v-on="on">
                        <v-btn fab flat icon ripple small>
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
                    <v-btn fab flat icon ripple small @click="removerCurso(item)">
                    <v-icon>delete</v-icon>
                    </v-btn>
                </v-list-tile>
                </template>
                <v-list-tile
                v-for="subItem in item.disciplinas"
                :key="subItem.id"
              >
                <v-list-tile-content>
                  <v-list-tile-title>
                    <v-icon>assignment</v-icon>
                    <button @click="goToDetalhesDisciplina(subItem)">{{ subItem.nome }}</button> 
                  </v-list-tile-title>                  
                </v-list-tile-content>
                <v-dialog :key="subItem.id" v-model="subItem.open" max-width="600px">
                    <template v-slot:activator="{ on }">
                        <div v-ripple v-on="on">
                        <v-btn fab flat icon ripple small>
                            <v-icon>edit</v-icon>
                        </v-btn>
                        </div>
                    </template> 
                    <v-card min-width="350px" flat>
                        <v-card-title primary-title>
                        <v-text-field :label="subItem.nome" v-model="nomeAlterarDisciplina"></v-text-field>
                        </v-card-title>
                        <v-card-actions>                  
                        <v-spacer></v-spacer>
                        <v-btn flat color="secundary" @click="editarDisciplina(item, subItem)">Salvar</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
                <v-btn fab flat icon ripple small @click="removerDisciplina(item, subItem)">
                    <v-icon>delete</v-icon>
                </v-btn> 
              </v-list-tile>
            </v-list-group>
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
      nomeNovaDisciplina: "",     
      nomeAlterarDisciplina: "",
      items: []
    };
  },
  methods: {
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
                e["cadastrarDisciplina"] = false;

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
      // busca a lista de disciplinas cadastradas   
      curso["disciplinas"] = [];

      this.$http.get(
          "http://localhost:8080/api/disciplina/curso/" + curso.id,
          { headers: { "content-type": "application/json" } }
        )
        .then(
          result => {
            this.response = result.data;            
            console.log(this.response);

            curso["disciplinas"] = result.data.data.content;
             
            curso["disciplinas"].forEach(e => {                
                e["open"] = false;                
            });
            return;
          },
          error => {
            console.error(error);            
          }
        );       
    },
    goToDetalhesDisciplina(disciplina) {     
      var where = "";
        
      if (this.$router.currentRoute.path.includes("/Dashboard/")) 
        where = "Disciplinas/" + disciplina.id;
      else 
        where = "Dashboard/Disciplinas/" + disciplina.id;      

      this.$router.push(where)
    },
    adicionarDisciplina(curso) {
      // Adicionar nova disciplina
      this.$http
        .post(
          "http://localhost:8080/api/disciplina",
          '{"cursoId": ' + 
            curso.id +
            ', "nome": "' +
            this.nomeNovaDisciplina +
            '"}',
          { headers: { "content-type": "application/json" } }
        )
        .then(
          result => {
            this.response = result.data;
            console.log(this.response);
            this.listarDisciplinas(curso);
          },
          error => {
            console.error(error);
          }
        );

      curso.cadastrarDisciplina = false;
    },
    editarDisciplina(curso, disciplina) {
      // Edita o evento
      this.$http
        .put(
          "http://localhost:8080/api/disciplina/" + disciplina.id,
          '{"cursoId": ' +
            curso.id +
            ', "nome": "' +
            this.nomeAlterarDisciplina +
            '"}',
          { headers: { "content-type": "application/json" } }
        )
        .then(
          result => {
            this.response = result.data;
            console.log(this.response);
            this.listarDisciplinas(curso);
          },
          error => {
            console.error(error);
          }
        );

      curso.open = false;
    },
    removerDisciplina(curso, disciplina) {
      // Remove o evento
      this.$http
        .delete("http://localhost:8080/api/disciplina/" + disciplina.id, {
          headers: { "content-type": "application/json" }
        })
        .then(
          result => {
            this.response = result.data;
            console.log(this.response);
            this.listarDisciplinas(curso);
          },
          error => {
            console.error(error);
          }
        );
    },
    teste(subItem) {
      debugger
      subItem.open = false;
    }
  },
  beforeMount() {
    this.listarCursos();
  }
};
</script>