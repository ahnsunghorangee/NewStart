<template>
  <div style="width:100%; height:100%;">
    <v-row
      style="width: 100%; height: 100%; padding: 0px 2%; margin: 0px; "
      justify="center"
    >
      <v-col cols="30" sm="20" md="10" lg="20">
        <!-- <v-card ref="form" cols="12" sm="10" md="8" lg="6"> -->
        <!-- <v-card-text> -->
        <div style="height:5%;" align="center">
          <router-link to="/board"
            ><div
              style="float: left; color: rgb(0 0 0 / 60%); font-weight: bold; font-size: large;"
            >
              <v-icon size="35px">mdi-chevron-left</v-icon>뒤로가기
            </div></router-link
          >
        </div>

        <table style="width:100%; height:85%;">
          <tr>
            <v-text-field
              style="margin:12px;"
              v-model="subject"
              :counter="20"
              label="제목을 입력해주세요"
              required
              id="subject"
              ref="subject"
            ></v-text-field>
          </tr>
          <tr width="500" height="300">
            <v-textarea
              style="width:100%; height:100%;"
              no-resize
              v-model="content"
              solo
              :counter="500"
              label=""
              placeholder="요청사항을 작성해주세요."
              required
              type="textarea"
              id="content"
              ref="content"
            ></v-textarea>
          </tr>
          <tr>
            <input
              style="width:50%; margin-left:0%; margin-top:0%; "
              type="file"
              @change="onChange($event)"
            />
            <!-- <video style="width:50%; height:50%;" autoplay :src="image" /> -->
            <v-btn @click="uploadImage">Upload video</v-btn>
            <v-btn @click="removeImage">Remove video</v-btn>
          </tr>
          <!-- </v-card-text> -->
        </table>

        <div style="height:10%;" class="form-group" align="center">
          <v-btn
            align="left"
            type="button"
            color="rgb(54, 214, 123)"
            style=" color: white; width: 150px; height: 90%; font-size: large; font-weight: bold; text-shadow:#343a40d4 1px 1px 4px;"
            @click="updateHandler"
            >수정하기</v-btn
          >

          <!-- <v-btn @click="clear">초기화</v-btn> -->
        </div>
        <!-- </v-card> -->
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from "axios";
import Swal from 'sweetalert2';

export default {
  name: "boardupdate",
  data: () => ({
    board: [],
    id: "",
    subject: "",
    content: "",
    image: "",
    date: "",
    reply: [],
    thisReply: "",
    email: "",
    fileData: "",
  }),

  created() {
    var id = this.$route.params.id;
    axios
      .get(`https://serverName/api/notices/notice/${id}`)
      .then(({ data }) => {
        //console.log(data);
        this.id = data.id;
        this.email = data.email;
        this.subject = data.subject;
        this.content = data.content;
        var dateBefore = data.date;
        var date = dateBefore.split("T")[0];
        date = date + " ";
        date = date + dateBefore.split("T")[1].split(".")[0];
        this.date = date;
      });
  },

  methods: {
    checkHandler() {
      let err = true;
      let msg = "";
      err &&
        !this.subject &&
        ((msg = "제목 입력해주세요"),
        (err = false),
        this.$refs.subject.focus());
      err &&
        !this.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) {
        // alert(msg);
        Swal.fire({
            icon: 'error',
            title: msg,
            text: '',
            footer: ' '
        })
      }
      else this.updateHandler();
    },

    updateHandler() {
      var id = this.$route.params.id;
      //console.log("id", id);
      //console.log(this.subject + " " + this.content);
      var url = "null";
        //console.log(url);
        if (this.fileData) {
          var strArr = this.fileData.name.split(".");
          url =
            "https://serverName/media/" +
            this.$store.state.userinfo.id +
            "" +
            this.$route.params.number +
            "." + strArr[1];
        }
      axios
        .put(`https://serverName/api/notices/notice/${id}`, {
          subject: this.subject,
          content: this.content,
          email: this.email,
          url: url
        })
        .then(() => {
          // alert("수정이 완료되었습니다.");
          Swal.fire({
            icon: 'success',
            title: '수정 완료!',
            text: ' ',
            footer: ' '
          })
          this.moveList();
        });
    },
    moveList() {
      this.$router.push("/board");
    },
    clear() {
      this.$refs.form.reset();
    },
    removeImage(){
      this.fileData = "";
      this.isFile = false;
      const input = this.$refs.fileInput
      input.type = 'text'
      input.type = 'file'
    },
    uploadImage() {
      var reader = new FileReader();
      var formData = new FormData();
      // //console.log(this.fileData);
      var strArr = this.fileData.name.split("."); // 파일 확장자 가져오기 위해 자르기

      var fileName =
        this.$store.state.userinfo.id +
        "" +
        this.$route.params.number +
        "." +
        strArr[1];
      formData.append("file", this.fileData);
      formData.append("filename", fileName);
      axios
        .post(`https://serverName/api/notices/upload`, formData, {
          headers: { "Content-Type": "multipart/form-data" }
        })
        .then(() => {
          //console.log(response);
        })
        .catch(() => {
          //console.log(e);
          // this.task = true
        });

      reader.readAsDataURL(this.fileData);
      reader.onloadend = function() {
        //console.log(fileName);
        // axios.post(`http://localhost:8000/api/notices/upload`,{data : reader.result, filename : fileName}).then(response=>{
        //     //console.log(response);
        //   }).catch(e=>{
        //     //console.log(e)
        //     // this.task = true
        // });
      };

      let timerInterval;
      Swal.fire({
        title: '업로드중...',
        // html: '전송까지 <b></b> 초 남았습니다.',
        timer: 2000,
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
            '업로드완료!',
            '',
            'success'
          )
          this.isFile = true;
        }
      }).then((result) => {
        /* Read more about handling dismissals below */
        if (result.dismiss === Swal.DismissReason.timer) {
          // // //console.log('I was closed by the timer')
        }
      });
    },
    onChange(e) {
      const file = e.target.files[0];
      //   this.item.imageUrl = URL.createObjectURL(file);
      this.image = URL.createObjectURL(file);
      this.fileData = file;
      // this.$set(this.items[index], "file", file);
    }
  }
};
</script>

<style>
</style>