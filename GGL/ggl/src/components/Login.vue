<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script src="https://unpkg.com/vue-router/dist/vue-router.js"></script>

<template>
<div id="app">
  <v-app id="inspire">
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary">
                <v-toolbar-title>GGL</v-toolbar-title>
                <v-spacer></v-spacer>                
              </v-toolbar>
              <v-alert :value="alerta_invalido" type="error" transition="scale-transition">
                Email ou senha inválido.
              </v-alert>
              <v-alert :value="alerta_vazio" type="error" transition="scale-transition">
                Por favor preencha os campos de email e senha.
              </v-alert>              
              <v-card-text>                
                <v-form>                  
                  <v-text-field prepend-icon="person" name="email" label="Email" type="text" v-model="input.email" :rules="[rules.required, rules.min, rules.max]"></v-text-field>
                  <v-text-field prepend-icon="lock" name="senha" label="Senha" id="senha" type="password" v-model="input.senha" :rules="[rules.required]"></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <signUp/>
                <v-btn color="primary" @click="sendData()">Login</v-btn>                
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</div>

</template>

<script>
import signUp from '@/components/SignUp'
import Dashboard from '@/components/Dashboard'

export default {
  components: {
    signUp
  },
  name: 'Login',
  
  // Fazendo requisição pro beckend

  // JSON para fazer login
  data () {
      return {
          alerta_invalido: false,
          alerta_vazio: false,
          ip: "",
          input: {
            email: "admin@gmail.com",
            senha: "admin"
          },
          response: "",
          rules: {
            required: value => !!value || 'Campo obrigatório.',
            min: v => v.length >= 6 || 'Email deve ter no mínimo 6 caracteres.',
            max: v => v.length <= 200 || 'Email deve ter no máximo 200 caracteres.'
          }
      }
  },
  methods: {
      navigateTo(where) {
        this.$router.push(where)
      },
      sendData() {
          this.alerta_invalido = false;
          this.alerta_vazio = false;

          if (this.input.email == "" || this.input.senha == "") {
              this.alerta_vazio = true;
              return;
          } 

          // Pega o token
          this.$http.post("http://localhost:8080/auth", this.input, { headers: { "content-type": "application/json" } }).then(result => {              
                this.response = result.data; 
                localStorage.setItem('user-token', result.data.data.token) 
                console.log(localStorage.getItem('user-token'));

                this.$http.get("http://localhost:8080/api/alunos/" + this.input.email, { headers: { "content-type": "application/json" } }).then(result => {              
                    this.response = result.data;
                    localStorage.setItem('user-name', result.data.data.nome);
                    localStorage.setItem('user-ID', result.data.data.id);
                    this.navigateTo("/Dashboard");
                }, error => {            
                    this.alerta_invalido = true;
                    localStorage.removeItem('user-token');            
                    console.error(error);
                });  
          }, error => {            
                this.alerta_invalido = true;
                localStorage.removeItem('user-token');            
                console.error(error);
          });
      },
      goToSomewhere(link){
        
        this.$router.push(link)
      }

  }
}
</script>