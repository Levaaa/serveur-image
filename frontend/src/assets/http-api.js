import axios from "axios";
export default {
  name: "HelloWorld",
  props: {
    msg: String,
  },
  data() {
    return {
      show: false,
      response: [],
      errors: [],
      file: '',
      images: [],

      selected: {
        name: null,
        id: 0,
        },
    };
  },
  mounted() {
    this.callRestService();
    },
  
  methods: {
    callRestService() {
      axios
        .get(`images`)
        .then((response) => {
          // JSON responses are automatically parsed.
          this.response = response.data;
          console.log(this.response.length);
        })
        .catch((e) => {
          this.errors.push(e);
        });
    },

    getImage(selected) {
      var imageUrl = "/images/" + selected.id;
      var imageEl = document.querySelector(".imgDisplay");

      axios.get(imageUrl, { responseType:"blob" })
      .then(function (response) {
        var reader = new window.FileReader();
        reader.readAsDataURL(response.data);
        reader.onload = function() {
          var imageDataUrl = reader.result;
          imageEl.setAttribute("src", imageDataUrl);
        }
      });
    },

    handleFileUpload(){
      this.file = this.$refs.file.files[0];
    },

    submitFile(){
      let formData = new FormData();
      formData.append('file', this.file);
      axios.post( '/images', formData,
      {
        headers: {
            'Content-Type': 'multipart/file'
        }
      }).then(function(){
        console.log('SUCCESS!!');
      }).catch(function(error){
        console.log(error);
        console.log('FAILURE!!');
      });
    }
  }
};