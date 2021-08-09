<template>
  <div class="note_container" style="width:100%; padding-top:8%;">
        <div style="width:100%;height:470px; position:relative;">
            <div v-for="(word,index) in paginatedData" :key="index" style="position:relative;height:50px; width:100%; margin-bottom:10px;">
        <v-row justify="center" style="width:100%; margin:0px;">
            <div style="width:90%; max-width:300px; padding-left:20px; border-bottom: 1px solid #8080803b;">
                <div style="float:left; overflow:hidden;height:100%; padding:10px 0px;">{{word.word}}</div>
                <div style="float:right"><v-btn color="#e06363" style="margin:0px 10px; font-weight: bold; color: white; text-shadow: 1px 1px 10px #00000059;" @click="deleteVoca(word.id)">삭제</v-btn></div>
                <div style="float:right"><v-btn color="#5ece5a" style="font-weight: bold; color: white; text-shadow: rgb(0 0 0 / 60%) 1px 1px 6px;" @click="showHandLan(word.word)">수화보기</v-btn></div>
            </div>
        </v-row>
              </div>
        </div>
    <div style="margin-top:-20px;">
      <v-row justify="center">
      <button :disabled="pageNum === 0" @click="prevPage" class="page-btn" style="cursor:pointer;">
        <v-icon size="40px">mdi-arrow-left-drop-circle</v-icon>
      </button>
          <span style=" font-weight: bold;" class="page-count">{{ pageNum + 1 }} / {{ pageCount }}</span>
      <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn" style="cursor:pointer;">
        <v-icon size="40px">mdi-arrow-right-drop-circle</v-icon>
      </button>
      </v-row>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import Swal from 'sweetalert2'

export default {
data () {
    return {
       frameSize : {x:window.innerHeight*0.5625, y:window.innerHeight,per:1},
      pageNum: 0,
    //    NowClassNum:1,
        // intoFood:'egg',
     
    }
  },
  props: {
    listArray: {
      type: Array,
      required: true
    },
    pageSize: {
      type: Number,
      required: false,
      default: 7
    },
    
  },
  mounted(){
 this.onResize();

  },
  methods: {
    selectVocaList() {
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: res => {
          axios
            .get(`https://serverName/api/users/user/voca/${res.id}`)
            .then(response => {
              //console.log(response);
              this.listArray = response.data;
              //console.log("유저의 단어 전부 가져오기 완료");
            });
        }
      });
    },
    deleteVoca(voca_id) {
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: () => {
          axios
            .delete(`https://serverName/api/users/voca/${voca_id}`)
            .then(() => {
              //console.log(res);
              //console.log("단어 삭제 완료?");
              this.selectVocaList();
            });
        }
      });
    },
    showHandLan(data){
      if(data ==''){
        // alert('검색할 단어를 입력해주세요');
        Swal.fire({
          icon: 'error',
          title: '검색할 단어를 입력해주세요',
          text: '',
          footer: ' '
        })
      }else{

        // axios.get(`https://serverName/api/ai/word`,{ params : {text : this.attr}}).then(res=>{
        //   //console.log(res)
        //   this.videoSrc = res.data;
        //   this.result = this.attr;
        // });
        let timerInterval;
        Swal.fire({
          title: '검색중...',
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

        axios
        .post(`https://serverName/api/crawling/word`, {
          word: data // 검색할 단어
        })
        .then(response => {
          //console.log(response);
          //console.log("단어 검색 완료");
          var results = { result:data,descUrl:response.data.descUrl,imageUrl : response.data.imageUrl1,videoUrl : response.data.videoUrl};
          // this.results.result = this.data;
          // this.results.descUrl = response.data.descUrl;
          // this.results.imageUrl = response.data.imageUrl1;
          // this.results.videoUrl = response.data.videoUrl;
          this.$emit('show-handLan',results);
        });

      }

    },
      onResize(){
      if(window.innerHeight*0.5625 <=window.innerWidth){
        this.frameSize = {x:window.innerHeight*0.5625, y:window.innerHeight,per:innerHeight/640};
      }else{
        this.frameSize = {x:window.innerWidth, y:window.innerWidth*1.77,per:innerWidth/360};
        }
    },
    nextPage () {
      this.pageNum += 1;
    },
    prevPage () {
      this.pageNum -= 1;
    },
  },
  computed: {
    pageCount () {
      let listLeng = this.listArray.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;
      
      /*
      아니면 page = Math.floor((listLeng - 1) / listSize) + 1;
      이런식으로 if 문 없이 고칠 수도 있다!
      */
      return page;
    },
    paginatedData () {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;
      return this.listArray.slice(start, end);
    }
  }
}
</script>

<style>
.note_container{
    position:relative;
    background-image: url("../assets/note.png");
    background-position: center;
    background-size: contain;
}
</style>