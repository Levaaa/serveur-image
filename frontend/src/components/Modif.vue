<template>
  <div class="edit">
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

