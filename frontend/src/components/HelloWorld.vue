<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <button class="Search__button" @click="callRestService()">Call Spring Boot REST backend</button>

    <select v-model="selected">
      <option v-for="item in response" v-bind:value="{name: item.name, id: item.id}" v-bind:key="item.id">
        {{ item.name }}
      </option>
    </select>
    <button class="Search__button" @click="getImage(selected)">Get image</button>
    

    <a :href="getUrl(selected)" :download="selected.name">
      <button class="downloadButton">Download</button>
    </a>

    <button @formmethod="DELETE" @click="deleteImage(selected); callRestService();" >Delete</button>
    
    <div class="large-12 medium-12 small-12 cell">
      <label>
        <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
      </label>
        <button v-on:click="submitFile(); callRestService();">Submit</button>
    </div>
    <br><br>
    <img class = "imgDisplay">
    <br>

    <button v-on:click="show = !show;" class="Search__button" >Display Gallery</button><br><br>
    <div class="grid-container" v-if="show">
      <div v-for="item in response" v-bind:key="item.id">
        <div class="meta">
          <router-link :to="{path: '/edit', query: {id: item.id}}">
            <img class="div-img" v-bind:src="'/images/' + item.id" v-bind:alt="item.name">
          </router-link>
          <span class="tooltip" >{{ meta[item.id] }}</span>
        </div>
      </div>
    </div>
      
  </div>
</template>

<script src="../assets/http-api.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
a {
  color: #42b983;
}

.gallery {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-column-gap: 25px;
  grid-row-gap: 25px;
  width:100%;
}
.imgGallery{
  height: 200px;
  object-fit: contain;
  position: relative;
  top: 40px; left: 40px;
}

body {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  font-family: sans-serif;
}
.grid-container {
  columns: 5 200px;
  column-gap: 1.5rem;
  width: 90%;
  margin: 0 auto;
}
.meta{
  display: inline-block;
  
}

.div-img{
  width: 150px;
  width: 100%;
  margin: 10px 0 0 0;
  border: solid 4px black;
  padding: 5px;
  box-shadow: 5px 5px 5px rgba(0,0,0,0.5);
  border-radius: 5px;
  transition: all .25s ease-in-out;
  
}

.meta:hover .tooltip{
    visibility: visible;
}

.meta .tooltip{
  visibility: hidden;
  text-align: center;
}


</style>

