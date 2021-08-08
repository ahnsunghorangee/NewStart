<template>
  <div style="width:100%; height:100%;" >
    <div>
    
    <div v-if="frameSize.x > 1000" :style="{'padding-top':(frameSize.y-64-(frameSize.x*0.3))/2+'px'}">
    <router-link to="/fromHandLan"><div class="serviceButton" :style="{'margin-left':frameSize.x*0.15+'px',height:frameSize.x*0.3+'px'}"><div style="height: 30%;
    padding: 5%;
    font-size: xx-large;
    font-weight: bold;"><div style="font-size:15px;">수어를 음성이나 글로 바꾸고싶으신가요?</div><div>수어를<br>번역하기</div></div><div style="height:70%; padding:5%"><img style="height:100%;width:auto;" src="../assets/btn/FromHandLan.png"></div></div>
    </router-link>
    
    <router-link to="/toHandLanChoice"><div class="serviceButton" :style="{'margin-left':frameSize.x*0.15+'px',height:frameSize.x*0.3+'px'}"><div style="    height: 30%;
    padding: 5%;
    font-size: xx-large;
    font-weight: bold;"><div style="font-size:15px;">수어로 어떻게 표현하는지 궁금하신가요?</div>수어로<br>번역하기</div><div style="height:70%; padding:5%"><img style="height:100%;width:auto;" src="../assets/btn/ToHandLan.png"></div></div>
    </router-link>
    </div>

    <div v-if="frameSize.x <= 1000">
    <router-link to="/fromHandLan"><div class="serviceButton" :style="{'margin-left':frameSize.x*0.15+'px',width:frameSize.x*0.7+'px',height:(frameSize.y-56)*0.45+'px','margin-bottom':(frameSize.y-56)*0.05+'px'}"><div style="height: 30%;
    padding: 5%;
    font-size: xx-large;
    font-weight: bold;"><div style="font-size:15px;">수어를 음성이나 글로 바꾸고싶으신가요?</div>수어를 번역하기</div><div style="height:70%; padding:5%"><img style="height:100%;width:auto;" src="../assets/btn/FromHandLan.png"></div></div>
    </router-link>
    
    <router-link to="/toHandLanChoice"><div class="serviceButton" :style="{'margin-left':frameSize.x*0.15+'px',width:frameSize.x*0.7+'px',height:(frameSize.y-56)*0.45+'px'}"><div style="    height: 30%;
    padding: 5%;
    font-size: xx-large;
    font-weight: bold;"><div style="font-size:15px;">수어로 어떻게 표현하는지 궁금하신가요?</div>수어로 번역하기</div><div style="height:70%; padding:5%"><img style="height:100%;width:auto;" src="../assets/btn/ToHandLan.png"></div></div>
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