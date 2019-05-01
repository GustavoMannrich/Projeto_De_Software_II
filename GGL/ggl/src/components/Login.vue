<template>
<div id="app">
  <v-app id="inspire">
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary">
                <v-toolbar-title>Login GGL</v-toolbar-title>
                <v-spacer></v-spacer>                
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field prepend-icon="person" name="login" label="Login" type="text"></v-text-field>
                  <v-text-field prepend-icon="lock" name="password" label="Password" id="password" type="password"></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <modal></modal>
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
import modal from '@/components/Modal'
export default {
  components: {
    modal
  },
  name: 'Login',
  
  // Fazendo requisição pro beckend

  // JSON para fazer login
  data () {
      return {
          ip: "",
          input: {
              nome: "admin",
              senha: "admin"
          },
          response: ""
      }
  },
  mounted() {
      this.$http.get("https://httpbin.org/ip").then(result => {
          this.ip = result.body.origin;
      }, error => {
          console.error(error);
      });
  },
  methods: {
      sendData() {
          // Pega o token
          this.$http.post("http://localhost:8080/auth", this.input, { headers: { "content-type": "application/json" } }).then(result => {              
              this.response = result.data; 
              localStorage.setItem('user-token', result.data.data.token) 
              console.log(result.data.data.token);
          }, error => {
              localStorage.removeItem('user-token');            
              console.error(error);              
              return;
          });
      }
  }
}
</script>