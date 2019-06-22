<template>
  <v-layout row>
    <v-flex offset>
      <v-card>
        <div class="large-12 medium-12 small-12 cell">
          <label href="" class="file-select">
            <!-- We can't use a normal button element here, as it would become the target of the label. -->
            <div class="select-button">
              <!-- Display the filename if a file has been selected. -->
              <span v-if="arqSelecionados > 0">Arquivos Selecionados: {{arqSelecionados}}</span>
              <span v-else>Clique para fazer upload de um arquivo</span>
            </div>            
            <!-- Now, the file input that we hide. -->
            <input type="file" id="arquivos" ref="arquivos" multiple v-on:change="handleFileUpload()"/>
          </label>
          <label v-if="arqSelecionados > 0" href="" class="file-select">
            <!-- We can't use a normal button element here, as it would become the target of the label. -->
            <div class="upload-button">
              <v-btn fab flat icon ripple @click="submitFiles()">
                  <v-icon>cloud_upload</v-icon>
                </v-btn>
            </div>            
          </label>
          
        </div>
        <v-list one-line subheader>
            <v-subheader inset>Arquivos</v-subheader>
  
            <v-list-tile
              v-for="item in items"
              :key="item.id"
              avatar
            >
              <v-list-tile-avatar>
                <v-icon>assignment</v-icon>
              </v-list-tile-avatar>
  
              <v-list-tile-content>
                <v-list-tile-title>{{ item.fileName }}</v-list-tile-title>
                <v-list-tile-sub-title>{{ item.fileType }}</v-list-tile-sub-title>
              </v-list-tile-content>
  
              <v-list-tile-action>
                <v-btn fab flat icon ripple @click="downloadArquivo(item)">
                  <v-icon>cloud_download</v-icon>
                </v-btn>
                </v-list-tile-action>
                <v-list-tile-action>
                <v-btn fab flat icon ripple @click="removerArquivo(item)">
                  <v-icon>delete</v-icon>
                </v-btn>
              </v-list-tile-action>
            </v-list-tile>
          </v-list>        
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
  export default {
    props: {
      idDisciplina: "",
      arqSelecionados: 0
    },
    name: 'files',
    data () {
      return {
        arquivos: '',
        items: [],
        //[
        //  { icon: 'folder', iconClass: 'grey lighten-1 white--text', title: 'Sistemas de informação' },
        //  { icon: 'folder', iconClass: 'grey lighten-1 white--text', title: 'Inglês' },
        //  { icon: 'folder', iconClass: 'grey lighten-1 white--text', title: 'Espanhol' }
        //]
      }
    },
    methods:{
      handleFileUpload() {
        this.arquivos = this.$refs.arquivos.files;
        this.arqSelecionados = this.arquivos.length; 
      },
      submitFiles() {
        // Initialize the form data
        let formData = new FormData();

        // Add the form data we need to submit    
        for( var i = 0; i < this.arquivos.length; i++ ){
          let file = this.arquivos[i];

          formData.append('files', file);
        }

        // Adicionar novo curso
        this.$http.post("http://localhost:8080/api/uploadMultipleFiles/" + this.idDisciplina, 
          formData, 
        { headers: { "content-type": "multipart/form-data" } }).then(result => {              
            this.response = result.data;
            console.log(this.response);
            this.listarArquivos();
        }, error => {
            console.error(error);
        });

        this.arqSelecionados = 0;
      },
      listarArquivos() {
      // busca a lista de arquivos cadastrados
      this.$http
        .get(
          "http://localhost:8080/api/files/" + this.idDisciplina,
          { headers: { "content-type": "application/json" } }
        )
        .then(
          result => {
            this.response = result.data;

            this.items = [];
            result.data.data.content.forEach(e => {
                this.items.push(e);                       
            });

            console.log(this.response);
          },
          error => {
            console.error(error);
          }
        );
      },
      removerArquivo(file) {
        // Remove o arquivo
        this.$http
          .delete("http://localhost:8080/api/files/" + file.id, {
            headers: { "content-type": "application/json" }
          })
          .then(
            result => {
              this.response = result.data;
              console.log(this.response);
              this.listarArquivos();
            },
            error => {
              console.error(error);
            }
          );
      },
      downloadArquivo(file) {
        // download do arquivo
        this.$http({
            method: 'get',
            url: "http://localhost:8080/api/downloadFile/" + file.id,
            responseType: 'arraybuffer'
          })
          .then(
            result => {
              this.response = result.data;
              //console.log(this.response);
              let blob = new File([this.response], file.fileName, {type: file.fileType}); 

              const url = window.URL.createObjectURL(blob);
              const link = document.createElement('a');
              link.href = url;
              link.setAttribute('download', file.fileName); //or any other extension
              document.body.appendChild(link);
              link.click();
            },
            error => {
              console.error(error);
            }
          );
      }
    },
    beforeMount(){
       this.listarArquivos();
    }, 
  }
</script>

<style scoped>
.file-select > .select-button {
  padding: 0.5rem;

  color: white;
  background-color: #024a74;

  text-align: center;
  font-weight: bold;
}

.file-select > .upload-button {
  color: white;
  background-color: #024a74;

  text-align: center;
  font-weight: bold;
}

/* Don't forget to hide the original file input! */
.file-select > input[type="file"] {
  display: none;
}
</style>