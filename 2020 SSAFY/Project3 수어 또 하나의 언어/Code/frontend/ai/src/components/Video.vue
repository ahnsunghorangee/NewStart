<template>
  <div style="width:100%; height:100%;" id ="video">
    <video style="width:100%; height:100%;" id="my-preview" autoplay class="video"><div>잘못된접근입니다.</div></video>
    <video style="width:100%; height:100%;" id="preview" controls autoplay v-if="ok"></video>
<!-- <video style="width:50%; height:90%;" autoplay ref="video" id="video" class="video"></video> -->
<!-- <video style="width:100%; height:100%;" controls autoplay ref="video" id="my-preview" class="video" v-if="ok"></video> -->
 
    <div v-if="$route.name =='FromHandLan'">
        <v-btn :style="{'margin-left':(frameSize.x*0.9-100)/2+'px'}" type="button" @click="startRecording()" v-bind:disabled="isStartRecording" id="btnStart">
          <div id="btnStartInner"></div>
        </v-btn>
        <!-- <router-link to="/fromHandLanResult"><v-btn type="button" class="btn btn-success" @click.prevent="submitVideo()" v-bind:disabled="isSaveDisabled" id="btnSave">{{ submitText }}</v-btn></router-link> -->
        <!-- <v-btn type="button" class="btn btn-primary" @click.prevent="retakeVideo()" v-bind:disabled="isRetakeDisabled" id="btnRetake">Retake</v-btn> -->
    </div>

    <!-- <div id="send" style="margin-top:-80px" :style="{'margin-left':(frameSize.x*0.9-305)/2+'px'}" v-if="$route.name =='FromHandLanSend'">
      <router-link to="/FromHandLan"><v-btn class="sendBtn" color="rgb(232, 107, 94)" style="width:150px;color:white; margin-right:5px; height:50px; font-size:45px; font-weight:bold; font-size:large"><v-icon>mdi-backup-restore</v-icon>다시 하기</v-btn></router-link>
      <router-link to="/FromHandLanResult"><v-btn class="sendBtn" color="rgb(54, 214, 123)" style="width:150px;color:white; height:50px; font-size:45px; font-weight:bold; font-size:large"><v-icon>mdi-check</v-icon>번역 하기</v-btn></router-link>
    </div> -->

  </div>
</template>

<script src="https://cdn.webrtc-experiment.com/RecordRTC.js"></script>
<script src="https://webrtc.github.io/adapter/adapter-latest.js"></script>

<script>
import axios from 'axios'
import $ from 'jquery'
import router from '../router.js'
export default {
    name: "video",

    data(){
        return{
          video : '',
          recorder : '',
          videoPlayer:'',
          recordedBlobs:[],
        }
    },
    methods: {

          onResize(){
        this.frameSize = {x:window.innerWidth, y:window.innerHeight};
    },
    startRecording(){
      if($('#btnStartInner').css('border-radius')=='25px'){
        $('#btnStartInner').css('border-radius','0px');
        this.record();
      }else{
        this.stop();
      }
    },
      record(){
        navigator.mediaDevices.getUserMedia({
            // audio: true, 
            video: true,
        }).then((stream) => {
            // Display a live preview on the video element of the page
            // setSrcObject(stream, video);
            // this.video = document.querySelector("video");

            // this.video = document.getElementById("my-preview");
            // // //console.log(this.videoPlayer)
            // this.video.srcObject = stream;
            // this.video.play();
            // video.muted = true;

            // Initialize the recorder
            this.recorder = new MediaRecorder(stream, {
              videoBitsPerSecond : 250000000,
                mimeType: 'video/webm',
            });

            var recordArr = [];
            var recordedVideo = document.getElementById("preview");
            //console.log(recordedVideo);
            this.recorder.onstop = function(e) {
              //console.log("Recorder stopped: ");
              var superBuffer = new Blob(recordArr, { type: "video/webm" });
              //console.log(superBuffer)
              var reader = new FileReader();
              var test;

              reader.readAsDataURL(superBuffer); 
              reader.onloadend = function() {
                  var base64data = reader.result;                
                  router.push({name : 'FromHandLanSend', params : {videoSrc : window.URL.createObjectURL(superBuffer), data : base64data}});
                  return;
              }
       
              recordedVideo.addEventListener("loadedmetadata", function () {
                if (recordedVideo.duration === Infinity) {
                  recordedVideo.currentTime = 1e101;
                  recordedVideo.ontimeupdate = function () {
                    recordedVideo.currentTime = 0;
                    recordedVideo.ontimeupdate = function () {
                      delete recordedVideo.ontimeupdate;
                      recordedVideo.play();
                    };
                  };
                }
              });
            };
            this.recorder.ondataavailable = function(e) {
              if (e.data && e.data.size > 0) {
                recordArr.push(e.data);
              }
            };
            this.recorder.start(10); // collect 10ms of data
            //console.log("MediaRecorder started", this.recorder)
        });
      },
      stop(){
          this.recorder.stop();
          //console.log('멈춰잇')
         }
      },
      // init(){
      //   navigator.mediaDevices.getUserMedia({
      //       // audio: true, 
      //       video: true,
      //   }).then((stream) => {
      //     this.videoPlayer = document.getElementById("my-preview");
      //     this.videoPlayer.srcObject = stream;
      //     this.videoPlayer.play();
      //   });
      // },
     beforeMount() {
    this.onResize();
  },
  beforeUpdated(){
    this.sendWidth = document.getElementById('send').offsetWidth*2+5;
    this.buttonMargin = (document.getElementById('camera').offsetWidth -100)/2;
    //console.log(this.buttonMargin);
    //console.log(document.getElementById('camera').offsetWidth);
  },

  destroy(){
    this.videoPlayer.stop();
  }, 
    mounted() {
      // //console.log(router);
      navigator.mediaDevices.getUserMedia({
            // audio: true, 
            video: true,
        }).then((stream) => {
          this.videoPlayer = document.getElementById("my-preview");
          this.videoPlayer.srcObject = stream;
          this.videoPlayer.play();
        });

          window.onresize=()=>{
      this.onResize();
    }
      navigator.mediaDevices.getUserMedia({
            // audio: true, 
            video: true,
        }).then((stream) => {
          this.videoPlayer = document.getElementById("my-preview");
          this.videoPlayer.srcObject = stream;
          this.videoPlayer.play();
        });
        // this.videoPlayer = document.querySelector('video');
    },
};
</script>

<style lang="scss">
.video {
  width: 100%;
  height: 100%;
  padding: 0px;
  box-sizing: border-box;


  
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

video{
  width: 100%;
    height: 100%;
}
</style>
