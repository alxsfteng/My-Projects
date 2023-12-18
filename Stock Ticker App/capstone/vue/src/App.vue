<template>
  <div id="capstone-app">
    <div id="nav">
      <router-link class="top-buttons" v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link class="top-buttons" v-bind:to="{ name: 'gameStart' }">Game Display</router-link>&nbsp;|&nbsp;
      <!-- <router-link v-bind:to="{ name: 'sandbox' }">Sandbox</router-link>&nbsp;|&nbsp; -->
      <router-link class="top-buttons" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
  </div>
</template>
<script>
import { useStore } from 'vuex';
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap');

body {
  background-color: #0a8cf1;
  font-family: 'Poppins', sans-serif;
  margin: 0;
  /* Reset default body margin */
  padding: 0;
  /* Reset default body padding */
}

#capstone-app {
  min-height: 100vh;
}
</style>