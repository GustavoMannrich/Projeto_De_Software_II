<template>
  <v-layout row justify-end>
    <v-dialog v-model="dialog" persistent max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn color="primary" dark v-on="on">Cadastre-se</v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">Cadastre-se</span>
        </v-card-title>
        <v-card-text>
          <v-alert :value="alerta_vazio" type="error" transition="scale-transition">
            Por favor preencha os campos obrigatórios.
          </v-alert>    
          <v-alert :value="alerta_senha" type="error" transition="scale-transition">
            As duas senhas devem ser iguais.
          </v-alert>    
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12>
                <v-text-field prepend-icon="person" name="nome" id="nome" label="* Nome completo" type="text" v-model="input.nome" :rules="[rules.required]"></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field prepend-icon="email" name="email" id="email" label="* Email" type="text" v-model="input.email" :rules="[rules.required, rules.min, rules.max]"></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field prepend-icon="lock" name="senha" id="senha" label="* Senha" type="password" v-model="input.senha" :rules="[rules.required]"></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field prepend-icon="lock" name="confirmarSenha" id="confirmarSenha" label="* Confirme sua senha" type="password" v-model="confirmarSenha" :rules="[rules.required]"></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red" flat @click="dialog = false;">Fechar</v-btn>
          <v-btn color="blue darken-1" flat @click="sendData()" >Cadastrar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
export default {
  name: 'app',
  data () {
      return {
          alerta_vazio: false,
          alerta_senha: false,
          dialog: false,
          input: {
            email: "",
            nome: "",
            senha: ""
          }, 
          confirmarSenha:"",
          response: "",
          rules: {
            required: value => !!value || 'Campo obrigatório.',
            min: v => v.length >= 6 || 'Email deve ter no mínimo 6 caracteres.',
            max: v => v.length <= 200 || 'Email deve ter no máximo 200 caracteres.'
          }
      }
  },
  methods: {
    close() {        
      this.dialog = false;
    },    
    sendData() {
      this.alerta_vazio = false;
      this.alerta_senha = false;

      if (this.input.email == "" || this.input.senha == "" || this.input.nome == "" || this.confirmarSenha == "") {
          this.alerta_vazio = true;
          return;
      }  

      if (this.confirmarSenha != this.input.senha) {
          this.alerta_senha = true;
          return;
      }

      // Cadastra o novo aluno
      this.$http.post("http://localhost:8080/api/cadastrar-aluno", this.input, { headers: { "content-type": "application/json"} }).then(result => {              
          this.response = result.data;
          console.log(result.data);              
      }, error => {         
          console.error(error);
      });

      this.dialog = false;
    }
  }  
};
</script>