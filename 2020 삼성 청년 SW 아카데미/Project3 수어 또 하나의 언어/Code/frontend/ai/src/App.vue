<template>  
  <v-app id="app" style="overflow-y:hidden">
    <Nav></Nav>
  </v-app>
</template>
<script>
import Nav from "@/components/Nav.vue"

export default {
  components:{Nav},
  name: "app",
  data() {
    return {
      frameSize:{
        x:0,
        y:0,
      }
    };
  },
  methods: {
    onResize(){
      if(window.innerHeight*0.5625 <=window.innerWidth){
        this.frameSize = {x:window.innerHeight*0.5625, y:window.innerHeight};
      }else{
        this.frameSize = {x:window.innerWidth, y:window.innerWidth *1.77};
      }
    },
  },
  mounted(){
    this.onResize();
  },
}
</script>
<style>

</style>