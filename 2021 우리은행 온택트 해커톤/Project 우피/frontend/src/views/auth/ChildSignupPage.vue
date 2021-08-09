<template>
  <section>
    <h1 style="margin: 20px">자녀 회원 가입</h1>
    <p style="margin: 20px">
      부모님에게 전달받은 코드를 입력해주세요. <br />
      QR코드 스캔으로 회원가입을 진행할 수도 있습니다.
    </p>
    <div class="inputField">
      <div class="input_title"><span>가입 코드 인증</span></div>
      <div
        style="
          display: flex;
          flex-direction: row;
          height: 50px;
          margin-bottom: 30px;
        "
      >
        <input placeholder="가입 코드 입력" v-model="certiNum" />
      </div>
    </div>

    <button @click="$router.push({ name: 'ChildSignupDetail' })" class="ok-btn">
      인증
    </button>
  </section>
</template>

<script>
export default {
  data() {
    return {
      certiNum: "MG-",
      viewCerti: false,
    };
  },
  methods: {},
};
</script>

<style lang="scss" scoped>
* {
  font-weight: bold;
}
section {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.inputField {
  width: 100%;
  padding: 0 20px;
  text-align: center;
  margin: 40px 0;
  .input_title {
    text-align: left;
  }
  input {
    border: 1px solid #eee;
    background: #eee;
    height: 50px;
    width: 100%;
    margin: 0 auto;
    padding: 10px;
  }
  button {
    height: 100% !important;
    background: #8ac4ff;
    color: rgb(0, 17, 255);
  }
}
.v-btn {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.ok-btn {
  background-color: #3c3eca;
  width: 90%;
  height: 50px;
  border-radius: 8px;
  color: white;
  font-weight: bold;
  border: none;
}
</style>