<template>
  <div class="edit">
    <button class="Search__button" @click="callRestService()">Print</button>
  </div>
</template>

<script>

import axios from "axios";
export default {
  name: "Edit",
  props: {
  },
  data() {
    return {
      response: [],
    };
  },
  mounted() {
    this.callRestService();
    },
  
  methods: {
    callRestService() {
      axios
        .get(`images/0?algorithm=contrast`)
        .then((response) => {
          // JSON responses are automatically parsed.
          console.log(response);
        })
        .catch((e) => {
          this.errors.push(e);
        });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

