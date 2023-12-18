<template>
  <div class="counter-frame">
    <div class="counter-label">{{ label }}</div>
    <div class="center">
      <div class="counter-container">
        <div class="counter-container-item minus button" @click="decreaseCounter">-</div>
        <div class="counter-container-input number">{{ formattedCounter }}</div>
        <div class="counter-container-item plus button" @click="increaseCounter">+</div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';

export default {
  props: {
    numberType: {
      type: String,
      default: 'integer',
      validator: (value) => ['integer', 'percentage', 'dollars'].includes(value),
    },
    label: {
      type: String,
      default: 'Amount',
    },
    defaultCounter: {
      type: Number,
      default: 0,
    },
  },
  
  data() {
    return {
      counter: this.defaultCounter,
    };
  },
  computed: {
    formattedCounter() {
      if (this.numberType === 'percentage') {
        return `${this.counter}%`;
      } else if (this.numberType === 'dollars') {
        return `$${this.counter.toFixed(2)}`;
      } else {
        return this.counter.toString();
      }
    },

  },
  methods: {
    increaseCounter() {
      this.counter += 5000;
    },
    decreaseCounter() {
      this.counter = Math.max(0, this.counter - 5000);
    },
  },
};
</script>

<style scoped>
/* Add styles from bankroll_counter on figma here*/
.counter-container-item {
  display: flex;
  width: 1.875rem;
  height: 1.875rem;
  padding: 0.46875rem;
  justify-content: center;
  align-items: center;
  flex-shrink: 0;
  border-radius: 39.0625rem;
  border: 1.25px solid var(--text-subtitle, #000);
  background: #FEF9ED;
  /*TODO: reset these in a focus pseudo element for hover change */
  box-shadow: -5px -5px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

.counter-container-input {
  display: flex;
  height: 2.5rem;
  padding: 0.5rem 0rem;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  flex-grow: 2;
  border-radius: 1.625rem;
  background: #A5DCFF;
  /*TODO: reset these in a focus pseudo element for hover change */
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25) inset;

}

.counter-container {
  width: 75%;
  display: flex;
  width: 17.25rem;
  justify-content: center;
  align-items: center;
  gap: 0.625rem;
}
</style>