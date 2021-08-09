<template>
  <div style="width:100%; height:100%;" >
    <div>
    
    <div v-if="frameSize.x > 768" :style="{'padding-top':(frameSize.y-64-(frameSize.x*0.3))/2+'px'}">
    <router-link to="/toHandLanDic"><div class="serviceButton" :style="{'margin-left':frameSize.x*0.15+'px',height:frameSize.x*0.3+'px'}"><div style="height: 30%;
    padding: 5%;
    font-size: xx-large;
    font-weight: bold;">수어 사전<br><div style="font-size:20px;">글로 검색해서 번역하기</div></div><div style="height:70%; padding:5%"><img style="height:100%;width:auto;" src="../../assets/btn/dictionary.png"></div></div>
    </router-link>
    
    <router-link to="/toHandLan"><div class="serviceButton" :style="{'margin-left':frameSize.x*0.15+'px',height:frameSize.x*0.3+'px'}"><div style="    height: 30%;
    padding: 5%;
    font-size: xx-large;
    font-weight: bold;">수어 찾기<br><div style="font-size:20px;">카메라로 찍어서 번역하기</div></div><div style="height:70%; padding:5%"><img style="height:100%;width:auto;" src="../../assets/btn/photo-camera.png"></div></div>
    </router-link>
    </div>

    <div v-if="frameSize.x <= 768">
    <router-link to="/toHandLanDic"><div class="serviceButton" :style="{'margin-left':frameSize.x*0.15+'px',width:frameSize.x*0.7+'px',height:(frameSize.y-56)*0.45+'px','margin-bottom':(frameSize.y-56)*0.05+'px'}"><div style="height: 30%;
    padding: 5%;
    font-size: xx-large;
    font-weight: bold;">수어 사전<br><div style="font-size:20px;">글로 검색해서 번역하기</div></div><div style="height:70%; padding:5%"><img style="height:100%;width:auto;" src="../../assets/btn/dictionary.png"></div></div>
    </router-link>
    
    <router-link to="/toHandLan"><div class="serviceButton" :style="{'margin-left':frameSize.x*0.15+'px',width:frameSize.x*0.7+'px',height:(frameSize.y-56)*0.45+'px'}"><div style="    height: 30%;
    padding: 5%;
    font-size: xx-large;
    font-weight: bold;">수어 찾기<br><div style="font-size:20px;">카메라로 찍어서 번역하기</div></div><div style="height:70%; padding:5%"><img style="height:100%;width:auto;" src="../../assets/btn/photo-camera.png"></div></div> 
    </router-link>
    </div>

    </div>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  data() {
    return {
      frameSize:{
        x:0,
        y:0,
      }
    }
  },
  methods: {
    onResize(){
        this.frameSize = {x:window.innerWidth, y:window.innerHeight};
    },
  },
  mounted(){
    this.onResize();
  }
}
</script>

<style scoped>
  .serviceButton{
  width:30%;
  background-color:white;
  display: block;
  border-radius: 18px;
  box-shadow: 0px 6px 17px 0px #00000012;
  text-align: center;
  float: left;
}

.v-application a {
  color:black;
}
</style>