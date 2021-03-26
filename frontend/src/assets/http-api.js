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
      meta: [],

      selected: {
        name: null,
        id: 0,
        },
    };
  },
  mounted() {
    this.callRestService();
    this.getMeta();
    },
  
  methods: {
    callRestService() {
      axios
        .get(`images/get`)
        .then((response) => {
          // JSON responses are automatically parsed.
          this.response = response.data;
          this.selected.name = response.data[0].name;
        })
        .catch((e) => {
          this.errors.push(e);
        });
        this.addImageMeta();
    },

    
    addImageMeta(){
      const id = this.response.length;
      var imageUrl = "/images/" + id + "/get";

      axios
        .get(imageUrl)
        .then((response) => {
          // JSON responses are automatically parsed.
          this.meta[id] = response.data;
        })
        .catch((e) => {
          this.errors.push(e);
        });
    },

    getMeta(){
      axios
        .get(`images`)
        .then((response) => {
          // JSON responses are automatically parsed.
          this.meta = response.data;
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

    getUrl(selected) {
      return "/images/" + selected.id;
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
    },

    deleteImage(selected){
      var imageUrl = "/images/" + selected.id;
      axios.delete(imageUrl);
    },

  }
};