<template>
  <div id="camera" class="camera" >
    <div style="height:5%; margin-left:5px" align="center">
        <router-link to="/toHandLanChoice">
            <div
              style="float: left; color: rgb(0 0 0 / 60%); font-weight: bold; font-size: large;"
             >
              <v-icon size="35px">mdi-chevron-left</v-icon>뒤로가기
            </div>
            </router-link>
        </div>
    <video style="width:100%; height:100%;" autoplay ref="video" id="video" class="video" v-if="ok"></video>
    <!-- <button class="snap" v-on:click="capture()">SNAP</button> -->
    <canvas ref="canvas" class="canvas" id="canvas" width="1000" height="800"></canvas>
    <!-- <button class="send" @click="objectDetection()">물체</button>
    <button class="redo" @click="textDetection()">글자</button> -->

    <div v-if="$route.name =='ToHandLan'">
      <v-btn style="margin: -100px 225px 0px 225px;" id="btnCapture" @click="capture()"><v-icon size="45px">mdi-camera</v-icon></v-btn>
    </div>

    <!-- <div id="send" style="margin-top:-80px" :style="{'margin-left':(frameSize.x*0.9-305)/2+'px'}" v-if="$route.name =='FromHandLanSend'">
      <router-link to="/FromHandLan"><v-btn class="sendBtn" color="rgb(232, 107, 94)" style="width:150px;color:white; margin-right:5px; height:50px; font-size:45px; font-weight:bold; font-size:large"><v-icon>mdi-backup-restore</v-icon>다시 하기</v-btn></router-link>
      <router-link to="/FromHandLanResult"><v-btn class="sendBtn" color="rgb(54, 214, 123)" style="width:150px;color:white; height:50px; font-size:45px; font-weight:bold; font-size:large"><v-icon>mdi-check</v-icon>번역 하기</v-btn></router-link>
    </div> -->
  
  </div>

</template>

<script>
import $ from 'jquery'
import axios from 'axios'
import Swal from "sweetalert2";
export default {
  name: "camera",
  data() {
    return {
      frameSize:{
        x:0,
        y:0,
      },
      sendWidth:0,
      buttonMargin:0,
      videoPlayer:'',
      canvas:'',
      video:'',
      ok : true,
      oList : "",
      tList : "",
      textImage: 'data:image/jpeg;base64,',
      objectImage: 'data:image/jpeg;base64,',
      roiList: '',
    }
  },
  methods: {
    onResize(){
        this.frameSize = {x:window.innerWidth, y:window.innerHeight};
    },
    startRecording(){
      if($('#btnStartInner').css('border-radius')=='25px'){
        $('#btnStartInner').css('border-radius','0px');
      }else{
       this.$router.push('/FromHandLanSend');
      }
    },
    init() {
      if (
        "mediaDevices" in navigator &&
        "getUserMedia" in navigator.mediaDevices
      ) {
        let constraints = {
          video: {
            width: {
              min: 640,
              ideal: 1280,
              max: 1920,
            },
            height: {
              min: 360,
              ideal: 720,
              max: 1080,
            },
          },
        };
        navigator.mediaDevices.getUserMedia(constraints).then((stream) => {
          this.videoPlayer = document.querySelector("video");
          this.videoPlayer.srcObject = stream;
          this.videoPlayer.play();
        });
      } else {
        alert("Cannot get Media Devices");
      }
    },
    capture(){
      this.video = this.$refs.video;
      this.canvas = this.$refs.canvas;
      this.canvas.getContext("2d").drawImage(this.video, 0, 0, this.canvas.width, this.canvas.height);

      this.objectDetection();
      this.textDetection();

      let timerInterval;
        Swal.fire({
          title: '검색중...',
          // html: '전송까지 <b></b> 초 남았습니다.',
          timer: 5000,
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

            var objectList = this.oList;
            var textList = this.tList;
            this.$router.push({name:"ToHandLanResult", params:
            {
              oList : objectList, 
              tList : textList,
              objectImage : this.objectImage,
              textImage : this.textImage,
              roiList : this.roiList
            }
            
            });
          }
        }).then((result) => {
          /* Read more about handling dismissals below */
          if (result.dismiss === Swal.DismissReason.timer) {
            // // //console.log('I was closed by the timer')
          }
        })
      
    },
     objectDetection(){
      // this.canvas;
      var image = new Image();
      image.src = this.canvas.toDataURL();

      axios.post(`https://serverName/api/ai/objectDetection`,{image : image.src}).then(response=>{
        // //console.log(response);
        this.oList = response.data;
        this.objectImage += response.data.image;
        this.roiList = response.data.roi;
      })
    },
    textDetection(){
      var image = new Image();
      image.src = this.canvas.toDataURL();

      axios.post(`https://serverName/api/ai/textDetection`,{image : image.src}).then(response=>{
        this.tList = response.data;
        this.textImage += response.data.image;
      })
    },
  },
  beforeMount() {
    this.init();
    this.onResize();
  },
  beforeUpdated(){
    this.sendWidth = document.getElementById('send').offsetWidth*2+5;
    this.buttonMargin = (document.getElementById('camera').offsetWidth -100)/2;
    //console.log(this.buttonMargin);
    //console.log(document.getElementById('camera').offsetWidth);
  },
  mounted(){
    window.onresize=()=>{
      this.onResize();
    }
  },
  destroy(){
    this.videoPlayer.stop();
  },
};
</script>

<style lang="scss" scoped>
.camera {
  width: 100%;
  height: 100%;
  padding: 0px;
  box-sizing: border-box;

  .video{
    display: block;
  }

  .canvas{
    display: none;
    // position:fixed;
    // width: 1050px;
    // height: 840px;
    // margin-top: 10px;
    // margin-left: 700px;
  }
  
  .send{
    display: block;
    // position:fixed;
    width: 75px;
    height: 75px;
    border-radius: 50%;
    background-color: transparentize($color: #2493dd, $amount: 0.75);
    border: 1px solid color #171717;
    border-radius: 50%;
    outline: none;
    cursor: pointer;
    // margin-top: 530px;
    // margin-left: 900px;

    &:hover {
      background-color: #2493dd;
    }
    &:active {
      background-color: darken($color: #2493dd, $amount: 10);
    }
  }

  .redo{
    display: block;
    // position:fixed;
    width: 75px;
    height: 75px;
    border-radius: 50%;
    background-color: transparentize($color: #fd3015, $amount: 0.75);
    border: 1px solid color #171717;
    border-radius: 50%;
    outline: none;
    cursor: pointer;
    // margin-top: 530px;
    // margin-left: 1000px;

    &:hover {
      background-color: #fd3015;
    }
    &:active {
      background-color: darken($color: #fd3015, $amount: 10);
    }
  }

  .snap {
    display: block;
    // position:fixed;
    width: 75px;
    height: 75px;
    border-radius: 50%;
    background-color: transparentize($color: #ffce00, $amount: 0.75);
    border: 1px solid color #171717;
    border-radius: 50%;
    outline: none;
    cursor: pointer;
    // margin-top: 530px;
    // margin-left: 250px;

    &:hover {
      background-color: #ffce00;
    }
    &:active {
      background-color: darken($color: #ffce00, $amount: 10);
    }
  }
}
#btnStart{
  width: 100px;
  height:100px;
  border:0px solid transparent;
  border-radius: 50px;
  min-width:unset;
  padding:25px;
  position:fixed;
  margin-top:-130px;
}
#btnStartInner{
  background-color: #b72e2e;
    width: 50px;
    height: 50px;
    box-shadow: 0px 0px 10px #00000070;
    border-radius: 25px;
}
.sendBtn{
  height:100px;
  color:white;
}
#btnCapture{
  width: 100px;
  height:100px;
  border:0px solid transparent;
  border-radius: 50px;
  min-width:unset;
  padding:25px;
  position:fixed;
  margin-top:-130px;
}
</style>