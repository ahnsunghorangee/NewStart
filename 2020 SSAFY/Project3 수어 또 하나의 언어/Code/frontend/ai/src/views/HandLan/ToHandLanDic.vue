<template>
  <div style="width:100%; height:100%;">
    <div style="width:100%; height:20%;">
    <div style="width:100%; height:50%"></div>
    <div style="height:5%; margin-left:50px" align="center">
        <router-link to="/toHandLanChoice">
            <div
              style="float: left; color: rgb(0 0 0 / 60%); font-weight: bold; font-size: large;"
             >
              <v-icon size="35px">mdi-chevron-left</v-icon>뒤로가기
            </div>
            </router-link>
        </div>
      <div id="search" :style="{'margin-left':searchMargin+'px'}" style="width: 100%; 
height: 50px; 
max-width:550px; 
background-color:white; border-radius: 45px; 
padding:10px 10px 10px 30px;">
        <v-icon size="30" style="float:left;margin-right:20px;">mdi-magnify</v-icon>
        <input v-on:keyup.enter="transButton" v-model="attr" placeholder="수어로 번역할 한글을 입력해주세요." type="text" style="height:100%; overflow:hidden;float:left;" :style="{width:'50%'}">
        
        <v-btn
        style="float:right; text-align:center;width:30px; height:30px; background-color:white; border-radius:15px;"
          @click="transButton"
        >
          <v-icon>mdi-arrow-right</v-icon>
        </v-btn>
      </div>
  </div>
  <div style="width:100%;height:80%;">
    <v-row  style="width:100%; height:100%; margin:0px;" justify="center">
    <div  style="height:100%; width:100%; padding-top:0.5%;" v-if="isWord">
      <v-row :style="{display:isopened,width:frameSize.x*0.9+'px'}" style="margin-left:0px; margin-top:3%; height:100%; max-height:500px; min-width:300px; position: fixed; z-index:2;" justify="center">
        <div  style=" height:100%; width:100%;">
          <video :src="videoUrl" style="width:100%; height:40%; background-size: contain;" autoplay loop></video>
          <div style="width:100%; height:50%;" align="center">
            <div style="margin:10px 0px; font-weight:bold; font-size:larger; text-align:center;">{{result}}에 대한 수어</div>

            <v-card-subtitle style="overflow:auto; text-align:center; width: 40%;">
              {{descUrl}}
            </v-card-subtitle>
            <div style="width:100%; text-align: center;">
              <v-btn v-if="$store.state.userinfo.id!=''" @click="addVoca"  color="rgb(57 181 111)" style="color:white; font-size:45px; font-weight:bold; font-size:small;"><v-icon>mdi-plus</v-icon>내 노트에 추가하기</v-btn>
              <v-btn v-if="$store.state.userinfo.id==''" @click="nologin"  style="color:#6c757d91; font-size:45px; font-weight:bold; font-size:small;"><v-icon>mdi-plus</v-icon>내 노트에 추가하기</v-btn>
            </div>
          </div>
        </div>
      </v-row>
      <v-row style="height:100%;" justify="center">
        <img style="height:100%;z-index:0;" :src="require('../../assets/dictionary_'+this.search+'.png')">
      </v-row>
    </div>

    <div style="height:100%; width:100%; padding-top:1%;" v-if="!isWord">
      <v-row :style="{display:isopened,width:frameSize.x*0.9+'px'}" style="margin-left:0px; margin-top:65px;;height:5%; min-height:500px; min-width:300px; position: fixed; z-index:2;" justify="center">
      <div style="width:100%; height:50%;" align="center">
        <div style="margin:10px 0px; font-weight:bold; font-size:larger; text-align:center;">{{result}}</div>
      </div>
      </v-row>
      <v-row style="height:100%;" justify="center">
      <img style="height:100%;z-index:0;" :src="require('../../assets/dictionary_'+this.search+'.png')">
      </v-row>
    </div>

  </v-row>
  </div>
    <!-- <camera id="camera" style="height:80%; max-width:500px; max-height:800px; display:fixed; max-width:550px;" :style="{'margin-left':searchMargin+'px','margin-top':(frameSize.y*0.9-cameraHeight)/2+'px'}"></camera> -->
  </div>
</template>

<script>
import axios from 'axios'
import Swal from 'sweetalert2'
// import Camera from '../../components/Camera.vue'
export default {
  // components:{Camera},
  data() {
    return {
      translateSearch:'',
      frameSize:{
        x:0,
        y:0,
      },
      searchSize : 0,
      cameraWidth:500,
      cameraHeight:800,
      attr:'',
      showSearchResult:'none',
      searchMargin:0,
      search:'close',
      isopened : 'none',
      videoSrc : '',
      result: '',
      descUrl: '',
      imageUrl: '',
      videoUrl: '',
      isWord: false,
    }
  },
  methods: {
    nologin(){
      // alert("로그인이 필요한 서비스입니다.");
      Swal.fire({
            icon: 'error',
            title: '로그인이 필요한 서비스입니다.',
            text: ' ',
            footer: ' '
          })
    },
    onResize(){
        this.frameSize = {x:window.innerWidth, y:window.innerHeight};
    },
    transButton(){
      if(this.attr ==''){
        // alert('검색할 단어를 입력해주세요');
        Swal.fire({
            icon: 'error',
            title: '검색할 단어를 입력해주세요.',
            text: ' ',
            footer: ' '
          })
      }else{
        this.showSearchResult = 'block';
        this.search = 'open';
        this.isopened = 'block';

        axios
        .post(`https://serverName/api/crawling/word`, {
          word: this.attr // 검색할 단어
        })
        .then(response => {
          //console.log(response);
          // //console.log("단어 검색 완료");
            this.result = this.attr;
            this.descUrl = response.data.descUrl;
            this.imageUrl = response.data.imageUrl1;
            this.videoUrl = response.data.videoUrl;
            this.isWord = true;
            
        }).catch(() =>{
          //console.log(error);
          this.result = '검색된 단어가 없습니다.'
          this.isWord = false;
        });

        let timerInterval;
        Swal.fire({
          title: '검색중...',
          // html: '전송까지 <b></b> 초 남았습니다.',
          timer: 3800,
          timerProgressBar: true,
          onBeforeOpen: () => {
            Swal.showLoading()
            
            Swal.color= 'green';
            timerInterval = setInterval(() => {
              const content = Swal.getContent()
              if (content) {
                const b = content.querySelector('b')
                if (b) {
                  b.textContent = Swal.getTimerLeft()
                }
              }
            }, 100)
          },
          onClose: () => {
            clearInterval(timerInterval)
            Swal.fire(
              '검색완료!',
              '',
              'success'
            )
          }
        }).then((result) => {
          /* Read more about handling dismissals below */
          if (result.dismiss === Swal.DismissReason.timer) {
            // // //console.log('I was closed by the timer')
          }
        });

        

      }
    },
    closeSearchResult(){
      this.showSearchResult = 'none';
    },
    addVoca() {
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: res => {
          //console.log("token", user_id);
          axios
            .post(`https://serverName/api/users/voca`, {
              user_id: res.id,
              word: this.attr,
              video: "test"
            })
            .then(() => {
              // alert("내 단어장에 추가가 완료되었습니다. 내 단어장에서 확인해주세요.")
              Swal.fire({
                icon: 'success',
                title: '추가 완료',
                text: '내 단어장에서 확인해주세요.',
                footer: ' '
              })
            });
        }
      });
    },
  },
  beforeMount() {
    this.onResize();
     window.onresize=()=>{
      this.onResize();
      this.frameSize = {x:window.innerWidth, y:window.innerHeight};
      //console.log(document.getElementById('search'));
      this.cameraWidth = document.getElementById('camera').offsetWidth;
      this.cameraHeight = document.getElementById('camera').offsetHeight;
      this.searchMargin = document.getElementById('search').offsetWidth;
      //console.log(this.cameraWidth);
      //console.log(this.searchSize);
        }
  },
  
  mounted(){
      this.onResize();
      this.searchMargin = (this.frameSize.x*0.9-this.serachMargin)/2;
      if(this.frameSize.x>550){
        this.searchSize = 550;   

      }else{
        this.searchSize = this.frameSize.x*0.9;
      }
      this.searchMargin = (this.frameSize.x*0.9-this.searchSize)/2;
      //console.log(this.searchMargin+" "+this.searchSize);
    window.onresize=()=>{
      this.onResize();
      this.frameSize = {x:window.innerWidth, y:window.innerHeight};
      //console.log(document.getElementById('search'));
      if(this.frameSize.x>550){
        this.searchSize = 550;   
      }else{
        this.searchSize = this.frameSize.x*0.9;
      }     
      this.searchMargin = (this.frameSize.x*0.9-this.searchSize)/2; 
      this.cameraWidth = document.getElementById('camera').offsetWidth;
      this.cameraHeight = document.getElementById('camera').offsetHeight;
      //console.log(this.cameraWidth);
      //console.log(this.searchSserachMarginize);
      //console.log(this.frameSize.x+"곱하기"+this.serachMargin+" "+this.serachMargin);
        }
  },
}
</script>

<style>
#searchResult{
  width:100%;
}
.v-image__image--cover {
    background-size: contain;
}
</style>