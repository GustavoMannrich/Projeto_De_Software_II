<template>
<div id="app">
  <v-app id="inspire">
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary">
                <v-toolbar-title>Minha conta</v-toolbar-title>
                <v-spacer></v-spacer>                
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field prepend-icon="person" name="login" label="Nome completo" type="text"></v-text-field>
                  <v-text-field prepend-icon="email" name="email" label="Email" type="text"></v-text-field>
                  <v-text-field prepend-icon="lock" name="password" label="Senha" type="password"></v-text-field>
                  <v-text-field prepend-icon="lock" name="confirmpassword" label="Confirme sua senha" type="password"></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <modal/>
                <v-btn color="secundary">Voltar</v-btn>
                <v-btn color="primary"  @click="navigateTo('Login')">Cadastrar</v-btn>
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
  name: 'SignUp',
  methods: {
    navigateTo(where) {
      this.$router.push(where)
    }
  }
}
</script>