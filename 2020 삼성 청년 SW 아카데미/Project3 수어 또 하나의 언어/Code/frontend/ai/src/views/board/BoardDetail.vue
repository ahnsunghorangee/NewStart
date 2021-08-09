<template>
  <div style="width:100%; height:100%;overflow:hidden;">
    <v-row
      style="width:100%; height:100%;overflow-y:scroll; overflow-x:hidden;"
      justify="center"
    >
      <v-col cols="30" sm="20" md="10" lg="10">
        <div style="height:10%; min-height:35px;" align="center">
          <router-link to="/board"
            ><div
              style="float: left; color: rgb(0 0 0 / 60%); font-weight: bold; font-size: large;"
            >
              <v-icon size="35px">mdi-chevron-left</v-icon>뒤로가기
            </div></router-link
          >
        </div>
        <div
          style="height:10%;border-bottom:1px solid rgba(0, 0, 0, 0.12);"
          align="center"
        >
          <h3>{{ subject }}</h3>
          <div
            v-if="$store.state.userinfo.id == user_id"
            style=" margin-top:-25px; float:right;"
          >
            <div class="modify btn"><v-icon color="#72d69a" style="margin-right:8px;" @click="moveUpdate">mdi-wrench</v-icon></div>
            <div class="delete btn" ><v-icon size="30px" color="#ef746b" @click="Delete">mdi-delete</v-icon></div>
          </div>
        </div>
        <div
          style="height:8%; width:100%; border-bottom:1px solid rgba(0, 0, 0, 0.12);     padding: 8px 0px;"
        >
          <div style=" width:50%; float:left;">글쓴이 : {{ email }}</div>
          <div style="width:50%; float:right;text-align:right;">
            작성일 : {{ date }}
          </div>
        </div>
        <div style="width:100%; height:68%; padding:15px;">
          <div>{{ content }}</div>
          <video
            style="width:50%; height:50%;"
            id="preview"
            controls
            autoplay
            :src="url"
            v-if="isVideo"
          ></video>
        </div>

        <div></div>

        <div class="replyinput">
          <input
            v-model="thisReply"
            style="border:1px solid rgba(0, 0, 0, 0.12); height:45px; width:90%; background-color:white; float:left;"
            v-on:keyup.enter="addReply"
            placeholder="댓글을 입력해주세요."
          />
          <v-btn
            v-if="$store.state.userinfo.id!=''"
            @click="addReply"
            style="height:45px; width:9%;border-color: transparent; float: left; color: white; font-weight: bold; font-size: small; text-shadow: 1px 1px 5px #0000006b;"
            type="button"
            class="btn btn-sm"
            color="rgb(98 149 232)"
            >등록</v-btn
          >
          <v-btn
            v-if="$store.state.userinfo.id==''"
            @click="alertlogin"
            style="height:45px; width:9%;border-color: transparent; float: left; color: gray; font-weight: bold; font-size: small; text-shadow: 1px 1px 5px #0000006b;"
            type="button"
            class="btn btn-sm"
            >등록</v-btn>
        </div>
        <div class="reply" v-for="(re, index) in reply" :key="index">
          <div style="width:100%; height:40%;">
            <div style="float: left; width: 50%; padding: 2px 2px; font-weight: 500; color: #000000ad; font-size: 22px;">
              <b-badge class="badge-lg">{{ re.nickname }}</b-badge>
                <span style="white-space: pre; margin-left: 10px; font-size: 12px; color: #00000082;"> {{ re.date }} </span>
            </div>
          </div>
          <div>
            <div style="width:100%; height:60%; margin-left: 5px; float: left;">{{ re.content }} <button type="button" style="float: right; color: red;" @click="deleteReply(re.id)" v-if="$store.state.userinfo.id == re.user_id"><v-icon medium color="#ef746b">mdi-delete</v-icon></button></div>
          </div>
        </div>

      </v-col>
    </v-row>
  </div>
</template>

<script>
// import store from '../../store'
import axios from "axios";
import Swal from 'sweetalert2';
export default {
  name: "boarddetail",
  data: () => ({
    board: [],
    id: "",
    subject: "",
    content: "",
    email: "",
    date: "",
    image: "",
    reply: [],
    thisReply: "",
    url: "",
    isVideo: false,
  }),

  created() {
    var id = this.$route.params.id;
    this.selectNoticeReply();
    axios
      .get(`https://serverName/api/notices/notice/${id}`)
      .then(({ data }) => {
        this.id = data.id;
        this.subject = data.subject;
        this.content = data.content;
        this.email = data.email;
        this.user_id = data.user_id;
        this.url = data.url;
        this.date = data.date;
        var date = data.date.split("T")[0];
        date = date + " ";
        date = date + data.date.split("T")[1].split(".")[0];
        this.date = date;
        if(this.url != 'null'){
          this.isVideo = true;
        }
      });
  },

  methods: {
    moveList() {
      this.$router.push("/board");
    },
    moveUpdate() {
      this.$router.push("/board/update/" + this.id);
    },
    alertlogin(){
      // alert('로그인이 필요한 서비스입니다.');
      Swal.fire({
            icon: 'error',
            title: '로그인이 필요한 서비스입니다.',
            text: '',
            footer: ' '
        })
    },
    Delete() {
      // this.$router.push("/board/delete/" + this.id);
      Swal.fire({
        title: '삭제하시겠습니까?',
        text: " ",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '네!'
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire(
            '삭제 완료!',
            ' ',
            'success'
          )
          var id = this.$route.params.id;
          axios
            .delete(`https://serverName/api/notices/notice/${id}`)
            .then(() => {       
              this.$router.push("/board");
          });
        }
      })
      
    },
    addReply() {
      // var notice_num = 8;
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: res => {
          axios
            .post(`https://serverName/api/notices/reply`, {
              user_id: res.id,
              notice_id: this.id,
              content: this.thisReply,
              nickname: this.$store.state.userinfo.nickname
            })
            .then(() => {
              this.thisReply = "";
              this.selectNoticeReply();
            });
        },
        fail: () => {
          // alert('로그인 해주세요.');
          Swal.fire({
            icon: 'error',
            title: '로그인이 필요한 서비스입니다.',
            text: ' ',
            footer: ' '
          })
        }
      });
    },
    selectNoticeReply() {
      axios
      .get(
        `https://serverName/api/notices/notice/reply/${this.$route.params.id}`
      )
      .then(response => {
        this.reply = response.data;
        
        this.reply.forEach(re => {
          var date = re.date.split("T")[0];
          date = date + " ";
          date = date + re.date.split("T")[1].split(".")[0];
          re.date = date;
        });
        
      });
    },
    deleteReply(reply_id){
      Swal.fire({
        title: '삭제하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '네!'
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire(
            '삭제완료!',
            ' ',
            'success'
          );
          axios
            .delete(`https://serverName/api/notices/reply/${reply_id}`)
            .then(() => {
              this.selectNoticeReply();
            });
        }
      });
    }
  }
};
</script>

<style scoped>
.btn {
  display: inline-block;
  margin: 0px 4px;
  height: 100%;
}
.delete {
  color: #b72626;
}
.modify {
  color: #148843;
}
.replyinput {
  width: 100%;
  height: 65px;
  background-color: #80808021;
  border-top: 1px solid rgba(0, 0, 0, 0.12);
  border-bottom: 1px solid rgba(0, 0, 0, 0.12);
  padding: 10px;
}
.reply {
  height: 80px;
  width: 100%;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}
</style>