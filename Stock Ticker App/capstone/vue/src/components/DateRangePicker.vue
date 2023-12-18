<!-- depricated difficulty passing props -->

<template>
  <div class="dates-form">

  </div>
</template>

<script>
import DatePicker from 'vue3-datepicker';
import { useStore } from 'vuex';

export default {
  components: {
    // DatePicker
  },
  data() {
    return {
      dateFormat: 'yyyy-MM-dd',
      startDate: this.gameStart,
      endDate: this.gameEnd,
    };
  },
  watch: {
    startDate(newValue) {
      this.$store.commit('SET_START_DATE', newValue);
    },
    endDate(newValue) {
      this.$store.commit('SET_END_DATE', newValue);
    },
  },
  methods: {

  },
};
</script>


<style scoped>
.date-fields {
  display: flex;
  align-items: center;
}

.date-input {
  width: 90%;
  height: 100%;
  padding-top: 5px;
  padding-left: 10px;
  border: none;
  background-color: rgba(255, 255, 255, 0.1);
}

.input-container {
  display: flex;
  height: 40px;
  padding: 8px 8px;
  margin: 8px 8px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 25px;
  background: #DCFD00;

  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25) inset;

}

/* Add styles from figma component 'start date? end date? here */
</style>

  