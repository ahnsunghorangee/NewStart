<template>
  <div style="width:100%; height:100%; padding-top:50px;">
    <camera id="camera" style="height:80%; max-width:500px; max-height:800px; display:fixed; max-width:550px;" :style="{'margin-left':searchMargin+'px'}"></camera>
  </div>
</template>

<script>
import Camera from '../../components/Camera.vue';
import Swal from 'sweetalert2';

export default {
  components:{Camera},
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
    }
  },
  methods: {
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
      }
    },
    closeSearchResult(){
      this.showSearchResult = 'none';
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