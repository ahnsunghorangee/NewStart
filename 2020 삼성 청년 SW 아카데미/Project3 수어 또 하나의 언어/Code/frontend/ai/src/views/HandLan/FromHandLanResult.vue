
<template>
  <div style="width:100%; height:100%;">
        <div v-if="inputText!='' && inputText!='null'" id="textbox" style="text-align:center;" :style="{'padding-top':(frameSize.y*0.9-textHeight-80)/2+'px'}">
            <div style="font-size:57px; font-weight: bold;     margin-bottom: 50px"><button @click="clickHandler()"><v-icon size="57px" id="speaker" v-text="speaker" :color="spaekerColor"></v-icon></button>{{inputText}}</div>
            <div style="width:100%;">
                <v-btn v-if="$store.state.userinfo.id == ''" @click="nologin" class="btnText" style="width:50%; height: 46px; margin:10px 0px; max-width:500px; font-size: large; min-width: 250px; color:white;" color="rgb(232, 107, 94)"><v-icon color="white">mdi-alert-circle</v-icon>구급 메세지 보내기<v-icon color="white">mdi-email</v-icon></v-btn>
                <v-btn v-if="$store.state.userinfo.id != ''" @click="sendMessage" class="btnText" style="width:50%; height: 46px; margin:10px 0px; max-width:500px; font-size: large; min-width: 250px; color:white;" color="rgb(232, 107, 94)"><v-icon color="white">mdi-alert-circle</v-icon>구급 메세지 보내기<v-icon color="white">mdi-email</v-icon></v-btn>
            </div>
            <div style="width:100%;">
                <router-link to="/fromHandLan"><v-btn class="btnText" style="width:24.5%;height: 46px; font-weight: bold; font-size: large; color:white; margin-right:1%; max-width:250px; min-width: 124px;" color="rgb(54, 214, 123)"><v-icon color="white">mdi-refresh</v-icon>다시하기</v-btn></router-link>
                <router-link to="/"><v-btn class="btnText" style="width:24.5%; font-weight: bold; height: 46px; font-size: large; max-width:250px; color:white; min-width: 124px;" color="rgb(228, 184, 244)"><v-icon color="white">mdi-home</v-icon>홈으로</v-btn></router-link>
            </div>
        </div> 
        <div id="textbox" v-if="inputText=='' || inputText=='null'" style="text-align:center;" :style="{'padding-top':(frameSize.y*0.9-textHeight-80)/2+'px'}">
            <div style="font-size:57px; font-weight: bold; margin-bottom: 50px">결과가 없어요.</div>
            <div style="font-size:25px; font-weight: bold; margin-bottom: 50px">죄송합니다. 이 데이터를 추가해달라고 요청해주세요.</div>
            <div style="width:100%;">
                <router-link to="/board">
                    <v-btn class="btnText" style="width:50%; height: 46px; margin:10px 0px; max-width:500px; font-size: large; min-width: 250px; color:white;" color="rgb(232, 107, 94)"><v-icon color="white">mdi-lead-pencil</v-icon>요청 글쓰러 가기</v-btn>
                </router-link>
            </div>
            <div style="width:100%;">
                <router-link to="/fromHandLan"><v-btn class="btnText" style="width:24.5%;height: 46px; font-weight: bold; font-size: large; color:white; margin-right:1%; max-width:250px; min-width: 124px;" color="rgb(54, 214, 123)"><v-icon color="white">mdi-refresh</v-icon>다시하기</v-btn></router-link>
                <router-link to="/"><v-btn class="btnText" style="width:24.5%; font-weight: bold; height: 46px; font-size: large; max-width:250px; color:white; min-width: 124px;" color="rgb(228, 184, 244)"><v-icon color="white">mdi-home</v-icon>홈으로</v-btn></router-link>
            </div>
        </div> 
  </div>
</template>

<script>
// import Vue2Tts from 'vue2-tts'
import axios from 'axios';
import Swal from 'sweetalert2';

export default {
    components: {
        // 'vue2-tts': Vue2Tts
    },
    data() {
        return {
            // inputText:'화상을 입었어요.',
            inputText:this.$route.params.answer,
            responsiveVoice:'',
            speaker:'mdi-volume-medium',
            spaekerColor:'gray',
            textWidth:0,
            textHeight:0,
            frameSize:{
                    x:0,
                    y:0,
            }
        }
    },
    beforeCreate(){
        const textToSpeech = require('@google-cloud/text-to-speech');

        // Import other required libraries
        const fs = require('fs');
        const util = require('util');
        // Creates a client
        const client = new textToSpeech.TextToSpeechClient();
        this.quickStart(fs,util,client); 

    },
    beforeUpdate(){
        this.textWidth = document.getElementById('textbox').offsetWidth;
        this.textHeight = document.getElementById('textbox').offsetHeight;
        //console.log(this.textHeight);
    },
    mounted(){
    this.onResize();
      window.onresize=()=>{
          this.onResize();
        }
      },
    methods:{
        nologin() {
            // alert("로그인이 필요한 서비스입니다.");
            Swal.fire({
                icon: 'error',
                title: '로그인이 필요한 서비스입니다.',
                text: '',
                footer: ' '
            })
        },
        noPhone(){
            Swal.fire({
                icon: 'error',
                title: '등록되어있는 번호가 없습니다.',
                text: '',
                footer: ' '
            })
        },
        okSend(){
            Swal.fire({
                icon: 'success',
                title: '등록된 번호로 문자를 발송했습니다.',
                text: '',
                footer: ' '
            })
        },
        sendMessage() {
            //console.log('들어옴')
            window.Kakao.API.request({
                url: "/v1/user/access_token_info",
                success: () => {
                    axios
                        .get(`https://serverName/api/users/user/phone/${this.$store.state.userinfo.id}`)
                        .then(response => {
                            if(response.data.length != 0){
                                axios
                                .post(`https://serverName/api/users/send`,{
                                user_id: this.$store.state.userinfo.id,
                                nickname: this.$store.state.userinfo.nickname,
                                word:this.inputText
                                })
                                .then(() => {
                                    this.okSend();
                                });
                            }
                            else{
                                this.noPhone();
                            }
                    });
                
                }
            });
        },
        textToSound(){
            this.speaker = 'mdi-volume-high';
            this.spaekerColor = 'blue';
            this.quickStart()
        },
        speak(opt_prop) {
      //console.log("test", this.inputText);
      if (
        typeof SpeechSynthesisUtterance === "undefined" ||
        typeof window.speechSynthesis === "undefined"
      ) {
        // alert("이 브라우저는 음성 합성을 지원하지 않습니다.");
        Swal.fire({
            icon: 'error',
            title: '이 브라우저는 음성 합성을 지원하지 않습니다.',
            text: ' ',
            footer: ' '
          })
        return;
      }

      window.speechSynthesis.cancel(); // 현재 읽고있다면 초기화

      const prop = opt_prop || {};

      const speechMsg = new SpeechSynthesisUtterance();
      speechMsg.rate = prop.rate || 1; // 속도: 0.1 ~ 10
      speechMsg.pitch = prop.pitch || 1; // 음높이: 0 ~ 2
      speechMsg.lang = prop.lang || "ko-KR";
      speechMsg.text =  this.inputText;

      // SpeechSynthesisUtterance에 저장된 내용을 바탕으로 음성합성 실행
      window.speechSynthesis.speak(speechMsg);
    },
    clickHandler(inputText) {
      // alert(inputText);
      this.spaekerColor = 'blue';
      this.speak(inputText, {
        rate: 1,
        pitch: 1.2,
        lang: "ko-KR"
      });
    },
        onResize(){
            this.frameSize = {x:window.innerWidth, y:window.innerHeight};
        },
        
    }
}
</script>

<style>
.btnText{
    color: white;
    font-weight: bold;
    font-size: large;
    text-shadow: 1px 1px 2px #00000063;
}
</style>