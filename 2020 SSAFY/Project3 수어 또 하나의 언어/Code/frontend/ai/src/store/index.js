import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userinfo: {
      age_range: "",
      birthday: "",
      email: "",
      gender: "",
      id: "",
      nickname: "",
    },
  },
  mutations: {
    login(state, logininfo) {
      state.userinfo.age_range = logininfo.age_range;
      state.userinfo.birthday = logininfo.birthday;
      state.userinfo.email = logininfo.email;
      state.userinfo.gender = logininfo.gender;
      state.userinfo.id = logininfo.id;
      state.userinfo.nickname = logininfo.nickname;
      //console.log("스토어저장", state.userinfo);
    },
    logout(state) {
      state.userinfo = {
        age_range: "",
        birthday: "",
        email: "",
        gender: "",
        id: "",
        nickname: "",
      };
      //console.log("스토어저장", state.userinfo);
    },
  },
  actions: {},
  modules: {},
  plugins: [createPersistedState()],
});