<template>
  <div class="edit">
    <img alt="Vue logo" src="../assets/logo.png">
    <Modif/>
  </div>
</template>

<script>
// @ is an alias to /src
import Modif from '@/components/Modif.vue'

export default {
  name: 'Edit',
  components: {
      Modif
  }
}
</script>
