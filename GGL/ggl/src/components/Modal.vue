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
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12>
                <v-text-field prepend-icon="person" name="login" label="Nome completo" type="text"></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field prepend-icon="email" name="email" label="Email" type="text"></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field prepend-icon="lock" name="password" label="Senha" type="password"></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field prepend-icon="lock" name="confirmpassword" label="Confirme sua senha" type="password"></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red" flat @click="dialog = false">Fechar</v-btn>
          <v-btn color="blue darken-1" flat @click="navigateTo('Login')" >Cadastrar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>


<script>
export default {
  data: () => ({
    dialog: false,
    debug: true,
    domain: '',
    ajaxRequest: false,
    postResults: []
  }),
  methods: {
      close() {
         
          this.dialog = false;
      },
      navigateTo(where) {
      this.$router.push(where)
    }
  }
};
</script>