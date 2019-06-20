<template>
  <v-app id="inspire">
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary">
                <v-toolbar-title>Minha conta</v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field prepend-icon="person" name="nome" label="Nome" type="nome" v-model="input.nome" :rules="[rules.required]"></v-text-field>
                  <v-text-field prepend-icon="email" name="email" label="E-mail" type="email" v-model="input.email" :rules="[rules.required]"></v-text-field>
                  <v-text-field id="password" prepend-icon="lock" name="password" label="Nova senha" type="password"  v-model="input.senha" :rules="[rules.required]" ></v-text-field>
                  
                  <v-checkbox v-model="input.receber_notificacoes" :label="'Receber notificações por e-mail'"></v-checkbox>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn @click="postAluno()" color="primary">Salvar</v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
  export default {
    data: () => ({
      drawer: null,
      alerta_vazio: false,
      alerta_senha: false,
      input: {
        nome: '',
        email: '',
        senha: '',    
        receber_notificacoes: false,  
        //enviarEmail: true,        
      },
      rules: {
        required: value => !!value || 'Campo obrigatório.',
        min: v => v.length >= 6 || 'Email deve ter no mínimo 6 caracteres.',
        max: v => v.length <= 200 || 'Email deve ter no máximo 200 caracteres.'
      }
    }),
    props: {
      source: String
    },    
    beforeMount() {
      this.getAluno();
    },
    methods:{
      getAluno(){
        this.$http.get("http://localhost:8080/api/alunos/" + localStorage.getItem('user-email'), { headers: { "content-type": "application/json" } }).then(result => {              
            this.response = result.data;
            this.input.nome   = result.data.data.nome;
            this.input.email  = result.data.data.email;                                
        }, error => {
            //alert(error);
        });
      },
      postAluno(){
        this.alerta_vazio = false;
        this.alerta_senha = false;

        if (this.input.email == "" || this.input.senha == "" || this.input.nome == "") {
            this.alerta_vazio = true;
            return;
        }  

        // Cadastra o novo aluno
        this.$http.put("http://localhost:8080/api/alunos/" + localStorage.getItem('user-ID'), this.input, { headers: { "content-type": "application/json"} }).then(result => { 
                        
            this.response = result.data;
            //alert(result.data);
        }, error => {
            //alert(error);
        });
      }      
    }
  }
</script>