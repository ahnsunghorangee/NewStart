<template>
  <div style="height:100%; width:100%; padding-top: 5%;">
      <v-row style="height:20%; margin:0px;" justify="center">
      <div class="inputPhonediv" ><input v-on:keyup.enter="insertPhone" type="number" class="inputPhone" v-model="phone" placeholder="긴급 연락처 추가하기(01012345678 형식으로 적어주세요)"><div @click="insertPhone" style="height:40px;width:40px;border-radius:20px; box-shadow:1px 1px 3px rgb(0,0,0,53%);float:right; padding:3.5px; background-color:white; margin:5px 5px"><v-icon size="35px" color="green">mdi-plus</v-icon></div></div>
      </v-row>

      <v-card style="height:80%;">
        <!-- <v-btn @click="sendMessage">메세지보내기</v-btn> -->
          <div style="height:100%; width:100%;" v-if="phoneList.length==0">
              <v-row justify="center" style="margin:0px; width:100%; text-align:center;">
                  <h4>위급상황시 긴급 메세지를 보낼 번호가 없습니다.</h4>
                  <h4>위에서 최소 1개의 번호를 등록해주세요.</h4>
              </v-row>
          </div>
          <v-row justify="center">
          <v-card :style="{display:showModifyInput}" style=" margin-top:4%; text-align:center;position:absolute; width:50%; height:43%;">
              <div style="width:100%; height:20%;"><v-icon style="float:right" @click="closeModify">mdi-close</v-icon></div>
              <input class="modifyInput" v-model="modifyInput">
              <v-btn @click="updatePhone" color="#75d491" style="width: 50%; height: 50px; font-size: large; font-weight: bold; text-shadow: rgba(0, 0, 0, 0.54) 0px 0px 5px; color: white;">수정완료</v-btn>
          </v-card>
          </v-row>
        <div style="height:100%; width:100%; padding: 21px;" v-if="phoneList.length!=0">
            <v-row style="width:100%;height:80%; margin:0px; overflow-y:scroll;" justify="center">
            <h4 style="width:100%;font-weight:bold; text-align:center; display:fixed;">등록된 번호</h4>
            <h4 style="width:100%;font-weight:bold; font-size:15px; text-align:center;">위급상황을 위해 최소 1개의 번호를 등록해주세요.</h4>
            <v-row v-for="(phone,index) in phoneList" :key="index" style="width:100%; margin:10px 0px;" justify="center">
            <div class="numbers" >
                {{phone.number}}
                <div style="float:right;"><v-icon color="#72d69a" style="margin-right:8px;" @click="OpenupdatePhone(phone)">mdi-wrench</v-icon><v-icon size="30px" color="#ef746b" @click="deletePhone(phone.id)">mdi-delete</v-icon></div>
            </div>
            </v-row>
            </v-row>
        </div>
      </v-card>
  </div>
</template>

<script>
import axios from 'axios'

export default {
    data() {
    return {
      frameSize:{
        x:0,
        y:0,
      },
      userid:'',
      phoneList:[],
      phone:'',
      showModifyInput:'none',
      modifyInput:'',
      thismodifyData:'',
    }
  },
    methods:{
      isNumberKey(evt) {
          var charCode = (evt.which) ? evt.which : event.keyCode;
          if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
              return false;
          return true;
      },
      sendMessage() {
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: () => {
          axios
            .post(`https://serverName/api/users/send`,{
              user_id: this.$store.state.userinfo.id,
              nickname: this.$store.state.userinfo.nickname
              // word:
            })
            .then(() => {
              //console.log(response);
              //console.log("문자보내기");
            });
        }
      });
    },

        closeModify(){
            this.showModifyInput = 'none'
        },
     OpenupdatePhone(phone){
         this.showModifyInput='block';
         this.thismodifyData = phone;
         this.modifyInput = phone.number;
    },
     deletePhone(id) {
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: () => {
          axios
            .delete(`https://serverName/api/users/phone/${id}`)
            .then(() => {
              //console.log(response);
              //console.log("전화번호 삭제하기");
                this.selectPhone();
            });
        }
      });
    },
    updatePhone() {
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: res => {
          axios
            .put(`https://serverName/api/users/phone/${this.thismodifyData.id}`, {
              user_id: res.id,
              number: this.modifyInput
            })
            .then(() => {
              //console.log(response);
              //console.log("전화번호 수정하기");
                this.closeModify();
                this.selectPhone();
            });
        }
      });
    },
     selectPhone() {
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: res => {
          axios
            .get(`https://serverName/api/users/user/phone/${res.id}`)
            .then(response => {
              //console.log(response);
              //console.log("로그인 아이디가 등록한 모든번호 불러오기");
              this.phoneList = response.data;
            });
        }
      });
    },

    insertPhone() {
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: res => {
          axios
            .post(`https://serverName/api/users/phone`, {
              user_id: res.id,
              number: this.phone
            })
            .then(() => {
              //console.log(response);
              //console.log("전화번호 등록하기");
              this.phone = '';
                this.selectPhone();
            });
        }
      });

    },
    },
    mounted(){
        this.selectPhone();
    },
}
</script>

<style>
.numbers{
    border-radius: 10px;
    height: 50px;
    width: 80%;
    text-align: center;
    box-shadow: 0px 0px 16px #e0e0e0e8 inset;
    font-size: x-large;
    font-weight: 500;
    padding: 8px;
    max-width: 760px;
    min-width: 300px;
}
.inputPhonediv{
    width: 80%;
    height: 50px;
    border-radius: 50px;
    background-color: #e0e0e033;
    box-shadow: 0px 0px 7px #0000002b inset;
}
.inputPhone{
width: 90%;
    height: 50px;
    border-radius: 50px;
    padding: 0px 21px;
    font-size: 19px;
    text-align: center;
}
.modifyInput{
    height: 50px;
    margin: 21px 0px;
    width: 90%;
    text-align: center;
    box-shadow: 0px 0px 11px #6c757d26 inset;
    border-radius: 15px;
    background-color: #8080800a;
}
</style>