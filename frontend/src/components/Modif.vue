<template>
  <div class="edit">

    <div>
      <img class="photo" :src="getUrl(selected)">
    </div>

    <div class="tools">
        <h1>TOOLS</h1>
        <input type="range" min="0" max="9" v-model="value" class="slider" v-on:input="changeImage(value)">
        <br><br>
        <input type="range" min="0" max="255" v-model="value" class="slider" v-on:input="getValue(value)">
        <br><br>
        <input type="range" min="0" max="255" v-model="value" class="slider" v-on:input="getValue(value)">
        <br><br>

    </div>

    <input type="button" class="button" value="save">
    <input type="button" class="button" value="download">
    <input type="button" class="button" value="save image">
    <input type="button" class="button" value="reset">
    <select name="selecteurImage" id="sldksdl" v-model="selected">
      <option v-bind:value="{name: item.name, id: item.id}" v-bind:key="item.id" v-for="item in response">
        {{ item.name }}
      </option>
    </select>

    <img class = "imgDisplay">
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Edit",
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
      axios.get(`images`)
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

    getUrl(selected) {
      return "/images/" + selected.id;
    },

    handleFileUpload(){
      this.file = this.$refs.file.files[0];
    },

    getValue(value){
      console.log(value);
    },

    changeImage(value){
      var imageUrl = "/images/" + value;
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
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.photo {
  /* to change to auto; */
  /* same as height */
  display: block;
  max-width: 45%; 
  max-height: 50%;

  border: 12px solid red;
  margin: 5%;
  float: left;
}

.tools {
  width: 30%; 
  height: 50%;
  border: 12px solid rgb(140, 0, 255);
  margin: 5%;
  float: right;
}

h1{
text-align: center;
}

.button{
  margin-left: 2.5%;
}

.slidecontainer {
  width: 90%;
}

.slider{
    outline: 0;
    border: 0;
    border-radius: 500px;
    width: 400px;
    max-width: 100%;
    margin: 24px 0 16px;
    transition: box-shadow 0.2s ease-in-out;
    overflow: hidden;
    height: 40px;
    -webkit-appearance: none;
    background-color: #ddd;
}

.slider::-webkit-slider-runnable-track {
	height: 40px;
	-webkit-appearance: none;
	color: #444;
	transition: box-shadow 0.2s ease-in-out;
}
.slider::-webkit-slider-thumb {
	width: 40px;
	-webkit-appearance: none;
	height: 40px;
	cursor: ew-resize;
	background: rgb(255, 255, 255);
	box-shadow: -340px 0 0 320px #1597ff, inset 0 0 0 40px #1597ff;
	border-radius: 50%;
	transition: box-shadow 0.2s ease-in-out;
	position: relative;
}
.slider:active::-webkit-slider-thumb {
	background: rgb(255, 255, 255);
	box-shadow: -340px 0 0 320px #1597ff, inset 0 0 0 3px #1597ff;
}

</style>

