<template>
  <v-card class="overflow-hidden">
    <div v-if="frameSize.x>1000&&$route.name=='Home'&&!isCookie" style="width:100%; height:100%; position:fixed; z-index:200;" :style="{display:openTutorial}" @click="closeTutorial"><img style="width:100%;height:100%; " src="../assets/tutorial.png"><v-btn color="#c55858" style="position:absolute; font-weight: bold; color: white;right: 182px; bottom: 0; margin: 16px;" @click="noshow1d">오늘 하루 보지 않기</v-btn><v-btn color="#c55858" style="position:absolute; font-weight: bold; color: white;right: 0; bottom: 0; margin: 16px;" @click="noshow7d">일주일간 보지 않기</v-btn></div>
    <div v-if="frameSize.x<=1000&&$route.name=='Home'&&!isCookie" style="width:100%; height:100%; position:fixed; z-index:200;" :style="{display:openTutorial}" @click="closeTutorial"><v-btn color="#c55858" style="position:absolute; font-weight: bold; color: white;right: 182px; bottom: 0; margin: 16px;" @click="noshow1d">오늘 하루 보지 않기</v-btn> <img style="width:100%;height:100%;" src="../assets/tutorial_mobile.png"><v-btn color="#c55858" style="position:absolute; font-weight: bold; color: white; right: 0; bottom: 0; margin: 16px;" @click="noshow7d">일주일간 보지 않기</v-btn></div>
    <div v-if="frameSize.x>1000" style="width:100%; height:100%; position:fixed; z-index:200;" :style="{display:openNavTutorial}" @click="closeTutorial"><img style="width:100%;height:100%; " src="../assets/tutorial.png"></div>
    <div v-if="frameSize.x<=1000" style="width:100%; height:100%; position:fixed; z-index:200;" :style="{display:openNavTutorial}" @click="closeTutorial"></div>
    
    <v-app-bar
      absolute
      color="transparent"
      elevate-on-scroll
      scroll-target="#scrolling-techniques-7"
      style="z-index:100;"
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>

      <v-spacer style="text-align:center;"><router-link to="/"><img src="../assets/title.png" height="50px"></router-link></v-spacer>

    </v-app-bar>
    <v-sheet
      id="scrolling-techniques-7"
      class="overflow-y-auto"
      :min-width="frameSize.x"
      :max-height="frameSize.y"
      style="z-index:0; "
    >

      <div class="background" v-if="$route.name=='Home' || $route.name=='ToHandLanChoice'" style="padding-top:64px;" :style="{width:frameSize.x+'px', height:frameSize.y+'px'}">
        <router-view></router-view>
        <!-- <v-card style="position: fixed;
    width: 800px;
    height: 200px;
    display: block;
    margin-top: -800px; z-index:3;
    margin-left: 586px;" :style="{display:openLogin}">
    <div style="width:65%; height:100%; margin-top:30px; padding-left:30px; float:left;">
      <input v-model="id" class="loginInput" placeholder="id">
      <input type="password" v-model="password" class="loginInput" placeholder="password">
    </div>  
    <div style="width:35%; height:100%; float:left; padding: 30px;">
      <v-btn style="float:left; width:100%; height:100%;">login</v-btn>
    </div>
    </v-card> -->
      </div>
      <div class="background" v-if="$route.name !='Home'&& $route.name!='ToHandLanChoice'" style=" padding-top:64px;" :style="{width:frameSize.x+'px', height:frameSize.y+'px'}">
        <div style="width:90%; height:90%; background-color:#ffffff9e; margin-left:5%;     border-radius: 30px;
    box-shadow: 0px 0px 27px -4openLoginpx #00000026;" :style="{'margin-top':(frameSize.y-46-(frameSize.y*0.9))/2+'px'}">
        <router-view></router-view>
        </div>
      </div>
    </v-sheet>

    <v-navigation-drawer
      v-model="drawer"
      absolute
      bottom
      temporary
      style="z-index:101;"
    >
      <v-list
        nav
        dense
      >
        <v-list-item-group
          v-model="group"
          active-class="deep-purple--text text--accent-4"
        >
        <div v-if="$store.state.userinfo.id==''" style="height:150px;width:100%; text-align:center;">
          <v-btn @click="kakaoLogin" color="#ffe812" style="display:table-col;vertical-align:middle;width:90%;max-width:250px; height:100px;">
             <div>
               <div style="display:table-col;vertical-align:middle; width:100%; height:50%;"><img style="width:50%; height:auto;" src="../assets/btn/kakao.png"></div><div class="loginBtn">로그인하기</div>
               </div></v-btn>
        </div>
        <v-card v-if="$store.state.userinfo.id!=''" style="height:150px;width:100%; text-align:center;">
          <!-- <div style="width:40%; height:100%; display:inline-block;">
            <div style="width:70px; height;70px;border-radius:35px;">
              <img :src="$store.state.userinfo.">
            </div>
          </div> -->
          <div v-if="userEmerPhonebook.length == 0" style="position:absolute; padding: 12px 8px;width:100%;height:100%;background-color:rgb(0 0 0 / 72%); font-size: 15px;
    font-weight: bold;"><h5 style="font-size:15px; font-weight: bold; color:#fbb8b8;">긴급연락처 등록이 안되어있어요!</h5><h5 style="font-size:15px; font-weight: bold; color:#fbb8b8;">아래 긴급연락처에서<br>긴급연락처 등록을 해 주세요.</h5></div>
          <div style="width:100%; height:70%; padding: 33px; display:inline-block; background: linear-gradient(0, rgb(240 245 253), rgb(197 220 255));padding: 33px;">
            <h4 style="font-weight: bold; color: #000000b3;">{{$store.state.userinfo.nickname}}님</h4>
            <h5 style="    font-size: medium; font-weight: bold; color: #0000009e;">안녕하세요!</h5>
          </div>
          <div style="width:100%; height:30%">
            <v-btn  @click="kakaoLogout" color="#acdc95" style="width: 100%; height: 100%; color: white; font-size: 18px; font-weight: bold; text-shadow: 1px 1px 7px #00000063;">
              로그아웃하기
            </v-btn>
          </div>
        </v-card>

        <router-link to="/toHandLanChoice">
          <v-list-item>
            <v-icon medium color="gray darken-2" style="margin-right : 8px;">mdi-camera</v-icon>
            <v-list-item-title>수어로 번역하기</v-list-item-title>
          </v-list-item>
        </router-link>

        <router-link to="/fromHandLan">
          <v-list-item>
            <v-icon medium color="gray darken-2" style="margin-right : 8px;">mdi-video</v-icon>
            <v-list-item-title>수어를 번역하기</v-list-item-title>
          </v-list-item>
          </router-link>

        <router-link to="/board">
          <v-list-item>
            <v-icon medium color="gray darken-2" style="margin-right : 8px;">mdi-comment-question-outline</v-icon>
            <v-list-item-title>게시판</v-list-item-title>
          </v-list-item>
        </router-link>

          <router-link to="/myNote">
          <v-list-item v-if="$store.state.userinfo.id!=''">
            <v-icon medium color="gray darken-2" style="margin-right : 8px;">mdi-tag-multiple</v-icon>
            <v-list-item-title>내 단어장</v-list-item-title>
          </v-list-item>
          </router-link>

          <router-link to="/myPhoneBook">
          <v-list-item v-if="$store.state.userinfo.id!=''">
            <v-icon medium color="red darken-2" style="margin-right : 8px;">mdi-alarm-light</v-icon>
            <v-list-item-title v-if="userEmerPhonebook.length == 0" style="color:red;">긴급연락처 관리</v-list-item-title>
            <v-list-item-title v-if="userEmerPhonebook.length != 0" >긴급연락처 관리</v-list-item-title>
          </v-list-item>
          </router-link>

        <router-link to="/">
          <v-list-item @click="showTutorial">
            <v-icon medium color="gray darken-2" style="margin-right : 8px;">mdi-information-outline</v-icon>
            <v-list-item-title>서비스 설명(홈으로 이동)</v-list-item-title>
          </v-list-item>
        </router-link>

        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </v-card>
</template>

<script>
import axios from 'axios'
import store from '../store'
import VueCookies from "vue-cookies"
import Swal from 'sweetalert2'

export default {
  data() {
    return {
      isCookie:null,
      frameSize:{
        x:0,
        y:0,
      },
      drawer: false,
      group: null,
      openTutorial:'block',
      id:'',
      password:'',
      userEmerPhonebook:[],
      openNavTutorial:'none'
    }
  },
  methods: {
     selectPhone() {
      window.Kakao.API.request({
        url: "/v1/user/access_token_info",
        success: res => {
          axios
            .get(`https://serverName/api/users/user/phone/${res.id}`)
            .then(response => {
              //console.log(response);
              //console.log("로그인 아이디가 등록한 모든번호 불러오기");
              this.userEmerPhonebook = response.data;
            });
        }
      });
    },

    noshow1d(){
      VueCookies.set("noshow","true",'1d');
      //console.log(VueCookies.keys());
    },
    noshow7d(){
      VueCookies.set("noshow","true",'7d');
      //console.log(VueCookies.keys());
    },
    onResize(){
        this.frameSize = {x:window.innerWidth, y:window.innerHeight};      
    },
    showTutorial(){
      this.openNavTutorial = 'block';
    },
    closeTutorial(){
      this.openTutorial = 'none';
      this.openNavTutorial = 'none';
    },
    kakaoLogout() {
      window.Kakao.API.request({
        url: "/v1/user/unlink",
        success: () => {
          //console.log("logout", res);
          store.commit('logout');
        }
      });
    },
    kakaoLogin() {
      // //console.log(window.Kakao);
      window.Kakao.Auth.login({
        success: this.GetMe
      });
    },
    GetMe() {
      //console.log("authObj", authObj);
      // 토큰 확인: 사용자의 id값 뽑아낼 때 쓰면 될듯
      window.Kakao.API.request({
        url: "/v2/user/me",
        success: res => {
          //console.log("body", res);
          const kakao_account = res.kakao_account;
          //console.log("카", kakao_account);
          const userInfo = {
            id: res.id,
            nickname: kakao_account.profile.nickname,
            email: kakao_account.email,
            gender: kakao_account.gender,
            birthday: kakao_account.birthday,
            age_range: kakao_account.age_range
          };

          axios
            .post(`https://serverName/api/users/user`, {
              id: userInfo.id,
              nickname: userInfo.nickname,
              email: userInfo.email,
              gender: userInfo.gender,
              birthday: userInfo.birthday,
              age_range: userInfo.age_range
            })
            .then(res => {
              //console.log(res);
              //console.log("로그인 성공");
              // alert("로그인 성공!");
              Swal.fire({
                icon: 'success',
                title: '로그인 성공',
                text: '',
                // footer: ' '
              });
              //console.log(res.data);
              this.selectPhone();
              store.commit('login',res.data);
            })
            .catch(() => {
              //console.log(err);
              //console.log("로그인 실패");
            });

          //console.log(userInfo);
          this.$bvModal.hide("bv-modal-example");
        },
        fail: () => {
          this.$router.push("/errorPage");
          //console.log(error);
        }
      });
    }
  },
  mounted(){
    //로그인 돼있을 때
    if (!window.Kakao.Auth.getAccessToken()) {
      store.commit('logout');
    }
    else {
      this.selectPhone();
    }
    this.isCookie = VueCookies.isKey('noshow');
    this.onResize();
      window.onresize=()=>{
          this.onResize();
        }
      },
  watch:{
     group () {
        this.drawer = false
      },
  }
}
</script>

<style>
.background{
  background:url('../assets/background.png') no-repeat;
  background-size: cover;
}
.loginInput{
      height: 59px;
    width: 100%;
    border-radius: 40px;
    padding: 0px 30px;
    box-shadow: 1px 1px 10px #e0e0e0 inset;
    margin-bottom:10px;
}
.loginInput:focus{
    height: 59px;
    width: 100%;
    border-radius: 40px;
    padding: 0px 30px;
    box-shadow: 1px 1px 10px #e0e0e0 inset;
    margin-bottom:10px;
}
.loginBtn{
  vertical-align: middle; width: 100%;
    height: 55%;
    font-size: 20px;
    font-weight: bold;
    color: #3b1e1e;
    margin-bottom: 9px;
    margin-top: -9px;
}
</style>