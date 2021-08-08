<template>
  <div style="width:100%;height:100%;">
      <v-card style="width:50%;height:100%; display:inline-block; float:left;">
        <router-link to="/toHandLan"><v-btn style="position: absolute;
    margin: 5px; height:60px; width:60px; text-align:center;"><v-icon size="35px">mdi-replay</v-icon></v-btn></router-link>
        <img :src="objectImage" style="width:100%; height:100%;" v-if="change">
        <img :src="textImage" style="width:100%; height:100%;" v-if="!change">
      </v-card>
      <v-card style="width:50%; height:100%; display:inline-block;">
      <v-tabs
          v-model="tabs"
          fixed-tabs
        >
          <v-tabs-slider></v-tabs-slider>
          <v-tab
            href="#mobile-tabs-5-1"
            class="primary--text"
            @click="tabChange(1)"
          >
            <v-icon>mdi-vector-rectangle</v-icon>물체
          </v-tab>

          <v-tab
            href="#mobile-tabs-5-2"
            class="primary--text"
            @click="tabChange(2)"
          >
            <v-icon>mdi-pencil</v-icon> 글씨
          </v-tab>
        </v-tabs>
      <v-tabs-items v-model="tabs">
      <v-tab-item
        v-for="i in 2"
        :key="i"
        :value="'mobile-tabs-5-' + i"
      >
        <v-card flat>
          <div v-if="i==1">
            <!-- <div v-if="objects[0].transResultLetter!='물체를 찾을 수 없습니다.'"> -->
            <v-card v-for="(result,index) in objects" style="margin:10px; display:block;" :key="index">
              <img style="width:80px; height:80px; margin:5px; display:inline-block;" :src="result.src">
              <div style="display:inline-block;">{{result.transResultLetter}}</div>
              <v-dialog v-model="dialog" scrollable max-width="300px">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn style="float:right; width:20%;height:80px; margin:5px;border-color: transparent; color: white; font-weight: bold; font-size: small; text-shadow: 1px 1px 5px #0000006b;"
                    color="rgb(136 182 226)"
                    v-bind="attrs"
                    v-on="on"
                    @click="transButton(result)"
                  >
                    <v-icon style="transform: rotate(-28deg);">mdi-hand-pointing-right</v-icon>수화 보기<v-icon style="transform: rotate(-46deg);">mdi-hand-pointing-left</v-icon>
                  </v-btn>
                </template>
                
                <v-card
                  :style="{width:frameSize.x*0.8+'px'}"
                  style="overflow:hidden;"
                >
                  <video v-if="imageUrl != ''" :src="videoUrl" style="width:100%; height:50%; background-size: contain;" autoplay loop></video>  
           
                  <v-card-title>
                    <div v-if="imageUrl != ''" style="margin:10px 0px; font-weight:bold; font-size:larger;">{{nowTransLate.transResultLetter}}에 대한 수어</div>   
                    <div v-if="imageUrl == ''" style="margin:10px 0px; font-weight:bold; font-size:medium;">{{nowTransLate.transResultLetter}}에 대한 결과가 없습니다.<br>게시판에 문의해주세요</div>  
                  </v-card-title>

                  <v-card-subtitle style="overflow-y:scroll;">
                    {{descUrl}}
                    <img v-if="imageUrl != ''" src="../../assets/btn/kakaobtn.png" v-on:click="kakaolink(nowTransLate.transResultLetter)" />
                    <v-btn v-if="$store.state.userinfo.id!=''&&imageUrl != ''" @click="addVoca"  color="rgb(57 181 111)" style="width:100%;color:white; font-size:45px; font-weight:bold; font-size:small;"><v-icon>mdi-plus</v-icon>내 노트에 추가하기</v-btn>
                    <v-btn v-if="$store.state.userinfo.id==''&&imageUrl != ''" @click="nologin"  style="width:100%;color:#6c757d91; font-size:45px; font-weight:bold; font-size:small;"><v-icon>mdi-plus</v-icon>내 노트에 추가하기</v-btn>
                    <router-link v-if="imageUrl == ''" to="/board">
                        <v-btn class="btnText" style="width:100%;color:white; font-size:45px; font-weight:bold; font-size:small;" color="rgb(232, 107, 94)"><v-icon color="white">mdi-lead-pencil</v-icon>요청 글쓰러 가기</v-btn>
                    </router-link>
                  </v-card-subtitle>
               
                 </v-card>
              </v-dialog>
            </v-card>
          </div>
          
          <div v-if="i==2" >
            <v-card v-for="(result,index) in letters" :key="index" style="width:100%; height: 50px;
              margin: 5px 0px;
              padding:6px 8px;
              display : inline-block;
              font-weight: bold;
              font-size: large;">
              <div style="float:left;width:70%;text-align:center;">{{result.transResultLetter}}</div>   
              <v-dialog scrollable max-width="300px">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    style="float:right; width:20%;height:30px; margin:5px;border-color: transparent; color: white; font-weight: bold; font-size: small; text-shadow: 1px 1px 5px #0000006b;"
                    color="rgb(136 182 226)"
                    v-bind="attrs"
                    v-on="on"
                    @click="transButton(result)"
                  >
                    <v-icon style="transform: rotate(-28deg);">mdi-hand-pointing-right</v-icon>수어 보기<v-icon style="transform: rotate(-46deg);">mdi-hand-pointing-left</v-icon>
                  </v-btn>
                </template>
                
                <v-card
                  :style="{width:frameSize.x*0.8+'px'}"
                  style="overflow:hidden;"
                >
                  <video v-if="imageUrl != ''" :src="videoUrl" style="width:100%; height:50%; background-size: contain;" autoplay loop></video>  

                  <v-card-title>
                    <div v-if="imageUrl != ''" style="margin:10px 0px; font-weight:bold; font-size:larger;">{{nowTransLate.transResultLetter}}에 대한 수어</div>   
                  <div v-if="imageUrl == ''" style="margin:10px 0px; font-weight:bold; font-size:medium;">{{nowTransLate.transResultLetter}}에 대한 결과가 없습니다.<br>게시판에 문의해주세요</div> 
                  </v-card-title>

                <v-card-subtitle style="overflow-y:scroll;">
                  {{descUrl}}
                  <img v-if="imageUrl != ''" src="../../assets/btn/kakaobtn.png" v-on:click="kakaolink(nowTransLate.transResultLetter)" />
                  <v-btn v-if="$store.state.userinfo.id!=''&&imageUrl != ''" @click="addVoca"  color="rgb(57 181 111)" style="width:100%;color:white; font-size:45px; font-weight:bold; font-size:small;"><v-icon>mdi-plus</v-icon>내 노트에 추가하기</v-btn>
                  <v-btn v-if="$store.state.userinfo.id==''&&imageUrl != ''" @click="nologin"  style="width:100%;color:#6c757d91; font-size:45px; font-weight:bold; font-size:small;"><v-icon>mdi-plus</v-icon>내 노트에 추가하기</v-btn>
                  <router-link v-if="imageUrl == ''" to="/board">
                      <v-btn class="btnText" style="width:100%;color:white; font-size:45px; font-weight:bold; font-size:small;" color="rgb(232, 107, 94)"><v-icon color="white">mdi-lead-pencil</v-icon>요청 글쓰러 가기</v-btn>
                  </router-link>
                </v-card-subtitle>
                      
              </v-card>
              </v-dialog>

            </v-card>
          </div>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </v-card>
      
      <v-row justify="center">
    
  </v-row>
  </div>
</template>

<script>
import axios from 'axios'
import Swal from 'sweetalert2'

export default {
  data() {
    return {
        transResultIMG:'../assets/photoresult.jpg',
        responsiveVoice:'',
          dialogm1: '',
          frameSize:{
          x:0,
          y:0,
        },
        nowTransLate:'',
        dialog: false,
        tabs: ['objects','letters'],
        objects:[
        ],
        letters:[
        ],
        objectImage : '',
        textImage : '',
        change : true,
        descUrl:'',
        imageUrl:'',
        videoUrl:'',
    }
        
    },
    methods: {
      kakaolink(data){
            window.Kakao.Link.sendDefault({
            objectType: 'feed',
            content: {
              title: '수어 번역기',
              description: '친구가 보낸 메세지 : '+data+'\n 수어번역기에서 확인해보세요!',
              imageUrl:
                'https://serverName/img/title.6d9ffb95.png',
              link: {
                webUrl: 'https://serverName',
                androidExecParams: 'test',
              },
            },
            buttons: [
              {
                title: '웹으로 이동',
                link: {
                  webUrl: 'https://serverName',
                },
              },
            ]
          });

      },
       nologin(){
        // alert("로그인이 필요한 서비스입니다.");
        Swal.fire({
              icon: 'error',
              title: '로그인이 필요한 서비스입니다.',
              text: ' ',
              footer: ' '
            })
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
      onResize(){
          this.frameSize = {x:window.innerWidth, y:window.innerHeight};
      },
      transButton(nowTrans){
        this.nowTransLate = nowTrans;
        this.descUrl = '';
        this.imageUrl = '';
        this.videoUrl = '';
        // //console.log(nowTrans);
        let timerInterval;
        Swal.fire({
          title: '로딩중...',
          // html: '전송까지 <b></b> 초 남았습니다.',
          timer: 3000,
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
              '로딩완료!',
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

        axios
        .post(`https://serverName/api/crawling/word`, {
          word: nowTrans.transResultLetter // 검색할 단어
        })
        .then(response => {
          //console.log(response);
          //console.log("단어 검색 완료");
          if(response.data.imageUrl==''){
            this.imageUrl = '';
          }else{
            this.descUrl = response.data.descUrl;
          this.imageUrl = response.data.imageUrl1;
          this.videoUrl = response.data.videoUrl;
          }
          
        }).catch(()=>{
          console.log('결과없음');
          this.descUrl = '';
        });
      },
      tabChange(type){
        if(type == 1){
          this.change = true;
        } else {
          this.change = false;
        }
        
      }
    },
    mounted(){
    window.onresize=()=>{
      this.onResize();
    }
    // //console.log(this.$route.params.oList);
    // //console.log(this.$route.params.tList);
    this.$route.params.oList.data.forEach(object => {
      // //console.log(object);
      if(object.label == '포장 된 상품')
        object.label = '상품'
      this.objects.push({src : 'data:image/jpeg;base64,'+object.src,transResultLetter: object.label, videoSrc : object.videoname})

    });
    // this.$route.params.roiList.forEach(roi => {
    //   // //console.log(object);
    //   this.objects.push({src : 'data:image/jpeg;base64,'+ ,transResultLetter: object})
    // });
    if(this.$route.params.tList.data){
      this.$route.params.tList.data.forEach(letter => {
        // //console.log(letter);

        this.letters.push({transResultLetter: letter.label, videoSrc: letter.videoname});
      });
    } else {
        this.letters.push({transResultLetter: '단어를 찾을 수 없습니다.'})
    }
    this.objectImage = this.$route.params.objectImage;
    this.textImage = this.$route.params.textImage;
  },
}
</script>

<style>
.v-toolbar__content{
  height:0px;
}
</style>