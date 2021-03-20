<template>
  <div class="edit">

    <div>
      <img class="photo" :src="getUrl(selected)">
    </div>

    <div class="tools">
      <h1>TOOLS</h1>
      <div v-if="effect.name === 'brightness' || effect.name === 'coloration'">
        <input type="range" :min="effect.min" :max="effect.max" v-model="value" class="slider" v-on:change="effect.param = value; applyEffect(selected, effect)">
      </div>
      <div v-else>
        <b v-if="errorMessage" style="color:red">
          Selectionnez votre effet !
          <br><br>
        </b>
        <button class="Search__button" @click="applyEffect(selected, effect)">Apply effect</button>
      </div>
      
      <br><br>


    </div>

    <div>
      <input type="button" class="button" value="save">
      <input type="button" class="button" value="download">
      <input type="button" class="button" value="reset">

      <select name="selecteurImage" class="button" v-model="selected">
        <option v-bind:value="{name: item.name, id: item.id}" v-bind:key="item.id" v-for="item in response">
          {{ item.name }}
        </option>
      </select>

      <select name="selecteurEffet" class="button" v-model="effect">
        <option v-bind:value="{name: item.name, param: item.param, min: item.min, max: item.max}" v-bind:key="item" v-for="item in name">
          {{ item.name }}
        </option>
      </select>
    </div>


    <br>

    <!-- <img class = "imgDisplay"> -->
    <img class = "result">
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

      effect: {
        name: null,
        param: null,
        min: null,
        max: null,
      },

      name: [
        {name: "contrast"},
        {name: "brightness", param: 0, min: -255, max: 255},
        {name: "equalizer"},
        {name: "toGrey"},
        {name: "coloration", param: 0, min: 0, max: 360}
        ],

      errorMessage: false,
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

    applyEffect(selected, effect){
      if(effect.name == null){
        this.errorMessage = true;
        return;
      } else {
        this.errorMessage = false;
      }
      var imageUrl = "/images/" + selected.id + "?algorithm=" + effect.name;
      if(effect.param != null){
        imageUrl += "&p1=" + effect.param;
      }
      var imageEl = document.querySelector(".photo");

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

  border: 12px solid rgb(42, 42, 42);
  margin: 5%;
  float: left;
}

.tools {
  width: 30%; 
  height: 70%;
  border: 12px solid rgb(140, 0, 255);
  margin: 5%;
  float: right;
}

h1{
text-align: center;
}

.button{
  margin-left: 2.5%;
  margin-right: 2,5%;
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

