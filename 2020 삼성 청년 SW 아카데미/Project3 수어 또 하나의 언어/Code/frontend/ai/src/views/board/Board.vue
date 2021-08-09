<template>
  <div class="container" align="center">
    <div>
      <h3 style="font-weight: bold;font-size: 24px;
    color: #495057;">
        문의 게시판
      </h3>
      <div style="height:50px;">
        <v-btn
          v-if="$store.state.userinfo.id != ''"
          style="border-color: transparent; float: left; color: white; font-weight: bold; font-size: small; text-shadow: 1px 1px 5px #0000006b;"
          type="button"
          id="mvWriteBtn"
          class="btn btn-sm"
          data-backdrop="static"
          color="rgb(98 149 232)"
          @click="movePage"
          ><v-icon size="19px">mdi-pencil</v-icon>새글쓰기</v-btn
        >

        <v-btn
          v-if="$store.state.userinfo.id == ''"
          style=" border-color: transparent; float: left; color: #0000007a; font-weight: bold;font-size: small;"
          type="button"
          id="mvWriteBtn"
          class="btn btn-sm"
          data-backdrop="static"
          @click="nologin"
          ><v-icon size="19px">mdi-pencil</v-icon>새글쓰기</v-btn
        >

        <v-spacer></v-spacer>
        <input
          style="border:0px solid transparent;float: right; width: 301px; height: 39px; background-color: #80808017; font-size: small; padding: 0px 10px; border-radius: 20px;"
          v-model="search"
          placeholder="제목, 내용을 검색해주세요"
        />
      </div>

      <div style="height:400px;">
        <v-data-table
          v-model="selected"
          :headers="headers"
          :items="boards"
          :search="search"
          item-key="id"
          :items-per-page="5"
          :hide-default-footer="false"
          @click:row="moveRead"
        ></v-data-table>
        <!-- <div>
          <div class="v-data-footer__icons-before">
            <button aria-label="Previous page" class="v-btn v-btn--disabled v-btn--flat v-btn--icon v-btn--round v-btn--text theme--light v-size--default" @click="formerPage()"><v-icon>mdi-chevron-left</v-icon></button>
          </div>
          <div class="v-data-footer__icons-after">
            <button aria-label="Next page" class="v-btn v-btn--flat v-btn--icon v-btn--round v-btn--text theme--light v-size--default" @click="nextPage()"><v-icon>mdi-chevron-right</v-icon></button>
          </div>
         </div> -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from 'sweetalert2';

export default {
  name: "boardlist",
  data() {
    return {
      search: "",
      headers: [
        { text: "NO.", value: "id" },
        { text: "제 목", value: "subject" },
        { text: "작 성 자", value: "email" },
        { text: "작 성 일", value: "date" }
      ],
      boards: [],
      selected: []
    };
  },
  created() {
    axios
      .get(`https://serverName/api/notices/notice`)
      .then(({ data }) => {
        this.boards = data;
        for (let index = 0; index < data.length; index++) {
          var dateBefore = data[index].date;
          var date = dateBefore.split("T")[0];
          date = date + " ";
          date = date + dateBefore.split("T")[1].split(".")[0];
          data[index].date = date;
        }
      });
  },
  computed: {
    numberOfPages() {
      return Math.ceil(this.boards.length / 5);
    }
  },
  methods: {
    movePage() {
      // this.$router.push("/board/create");
      var boardNum = 1;
      if (this.board) {
        boardNum = this.boards[0].id;
      }
      this.$router.push({
        name: "BoardCreate",
        params: { number: boardNum }
      });
    },
    moveRead(value) {
      // console.log(value.id);
      this.$router.push("/board/detail/" + value.id);
    },
    nextPage() {
      // if (this.page + 1 <= this.numberOfPages) this.page += 1;
      if (this.page + 1 <= 5) this.page += 1;
    },
    formerPage() {
      if (this.page - 1 >= 1) this.page -= 1;
    },
    updateItemsPerPage(number) {
      this.itemsPerPage = number;
    },
    nologin() {
      // alert("로그인이 필요한 서비스입니다.");
      Swal.fire({
          icon: 'error',
          title: '로그인이 필요한 서비스입니다.',
          text: '',
          footer: ' '
      })
    }
  }
};
</script>

<style>
table {
  width: 100%;
}
table th {
  font-size: 1.2rem;
  text-align: center;
}
thead {
  box-shadow: 0px 1px 9px #6c757d6b;
}
table td:first-of-type {
  border-top: 0px solid white;
}
table tr {
  height: 2rem;
  text-align: center;
  border-bottom: 0px solid white;
}
table tr:first-of-type {
  border-top: 0px solid white;
}
table tr td {
  padding: 1rem 0;
  font-size: 1.1rem;
}
container {
  border-bottom: 0px solid white;
}
.btn-cover {
  margin-top: 1.5rem;
  text-align: center;
}
.btn-cover .page-btn {
  width: 5rem;
  height: 2rem;
  letter-spacing: 0.5px;
}
.btn-cover .page-count {
  padding: 0 1rem;
}

.btn btn-sm btn-primary {
  margin-left: 250px;
}
.v-data-table__wrapper {
  height: 290px;
}
.v-data-footer__select {
  display: none;
  color: white;
}
input:focus {
  outline: none !important;
  outline-width: 0 !important;
  box-shadow: none;
  -moz-box-shadow: none;
  -webkit-box-shadow: none;
}
.v-menu__content {
  display: none;
}
.v-data-footer {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  font-size: 0.75rem;
  padding: 0 8px;
}
</style>