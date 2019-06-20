<template>
  <v-app id="inspire" dark>
    <v-navigation-drawer v-model="drawer" fixed clipped app>
      <v-list dense>
        <v-list-tile v-for="item in items" :key="item.text" @click="navigateTo(item.text)">
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>{{ item.text }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar color="blue" dense fixed clipped-left app>
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      <v-toolbar-title class="mr-5 align-center">
        <v-btn
          color="secundary"
          left
          flat
          @click="goToPageByText('Dashboard')"
        >GGL - Gerenciamento de conteúdo</v-btn>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-layout row align-center style="max-width: 650px"></v-layout>
      <v-btn color="primary" right>
        <span v-html="usename"></span>
      </v-btn>
      <v-btn color="secundary" right flat @click="logout()">
        <v-icon>power_settings_new</v-icon>
      </v-btn>
    </v-toolbar>
    <v-content>
      <router-view></router-view>  
      <!--<Calendar v-if="verifyScreen('Dashboard')"/>
      <Cursos v-else-if="verifyScreen('Cursos')"/>
      <MinhaConta v-else-if="verifyScreen('Minha conta')"/>
      <Disciplinas idDisciplina="1"/>-->
    </v-content>
  </v-app>
</template>

<script>
import Calendar from "@/components/Calendar";
import Cursos from "@/components/Cursos";
import MinhaConta from "@/components/MinhaConta";
import Disciplinas from "@/components/Disciplinas";

export default {
  components: {
    Calendar,
    Cursos,
    MinhaConta,
    Disciplinas
  },
  beforeMount() {
    this.verificaLogin();
    localStorage.setItem("screen", "Dashboard");
    this.$router.push(Calendar)
  },
  create() {
    
  },
  data: () => ({
    drawer: null,
    items: [
      { icon: "event", text: "Dashboard" },
      { icon: "subscriptions", text: "Cursos" },
      { icon: "account_circle", text: "Minha conta" }
    ],
    janela: "Dashboard",
    usename: localStorage.getItem("user-name"),
    userID: localStorage.getItem("user-ID")    
  }),
  props: {
    source: String
  },
  methods: {
    goToPage(item) {
      this.janela = item.text;
      localStorage.setItem('screen',item.text);
    },
    goToPageByText(item) {
      this.janela = item;
      localStorage.setItem('screen',item);
    },
    verifyScreen(item) {
      //return localStorage.getItem('screen') === item
      //alert(this.janela === item)
      //teste
      this.janela = localStorage.getItem('screen');

      return this.janela === item
      //return localStorage.getItem('screen') === item;
    },
    navigateTo(where) {
      if (this.$router.currentRoute.path.includes("/Dashboard/")) {
        if (where === "Dashboard") 
            where = "Calendar";
        else    
            where = where.replace(/\s/g, '');         
      } else {
        if (where === "Dashboard") 
            where = "Dashboard/Calendar";
        else
            where = "Dashboard/" + where.replace(/\s/g, '');  
      }
      
      this.$router.push('/Dashboard');
      this.$router.push(where);
    },
    logout() {
      localStorage.removeItem("user-token"), this.navigateTo("/");
    },
    verificaLogin() {
      if (
        localStorage.getItem("user-token") == null ||
        localStorage.getItem("user-token") == ""
      )
        this.logout();
    }
  }
};
</script>