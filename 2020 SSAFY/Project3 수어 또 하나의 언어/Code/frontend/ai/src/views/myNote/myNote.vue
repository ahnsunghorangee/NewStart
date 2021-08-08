<template>
  <div style="width:100%; height:100%;">
    <v-row justify="center" >
      <v-card :style="{display:showHandLanModal}"  style="margin-top:10%; z-index:3; position:absolute; width:50%; height:50%; overflow:hidden;">
        <div style="width:100%; height:10%;"><v-icon style="float:right" @click="closeModal">mdi-close</v-icon></div>
        <div style="width:100%; height:90%">
          <div style="margin:10px 0px; font-weight:bold; font-size:larger; text-align:center;">{{results.result}}에 대한 수어</div>
          <video :src="results.videoUrl" style="width:100%; height:50%; background-size: contain;" autoplay loop></video>
    <div style="width:100%; height:50%; overflow:hidden;" align="center">

    <v-card-subtitle style="overflow:auto; text-align:center; width: 80%; height: 117px;">     
      {{results.descUrl}}
    </v-card-subtitle>
        </div>
        </div>
      </v-card>
    </v-row>
    <v-row justify="center" style="width:100%; height:10%;  margin:0px;">
    <div style = "font-weight: bold;font-size: 24px;
    color: #495057;">내 단어장</div>
    </v-row>
    <v-row justify="center" style="width:100%; height:90%; margin:0px;">
      <div style="height:100%; width:100%; position:relative;"><my-word-list @show-handLan="showHandLanModalmethod" style="height:100%;width:100%;" :list-array="mywords"></my-word-list></div>
    </v-row>
      
  </div>
</template>

<script>
import axios from 'axios'
import MyWordList from '../../components/myWordList.vue'

export default {
  components:{MyWordList},
data() {
    return {
      frameSize:{
        x:0,
        y:0,
      },
      mywords:[],
      showHandLanModal:'none',
      results:{}
    }
  },
  methods: {
    closeModal(){
this.showHandLanModal = 'none';
    },
    showHandLanModalmethod(results){
      this.showHandLanModal = 'block';
      //console.log(results);
      this.results = results;
    },
    onResize(){
        this.frameSize = {x:window.innerWidth, y:window.innerHeight};
    },
selectVocaList() {
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: res => {
          axios
            .get(`https://serverName/api/users/user/voca/${res.id}`)
            .then(response => {
              //console.log(response);
              this.mywords = response.data;
              //console.log("유저의 단어 전부 가져오기 완료");
            });
        }
      });
    },
    deleteVoca(voca_id) {
      //console.log('보카',voca_id)
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: () => {
          axios
            .delete(`https://serverName/api/users/voca/${voca_id}`)
            .then(() => {
              //console.log(res);
              //console.log("단어 삭제 완료");
              this.selectVocaList();
            });
        }
      });
    },
  },
  mounted(){
    this.onResize();
  },
  created(){
    this.selectVocaList();
  }
}
</script>

<style>

</style>