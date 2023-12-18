<template>
  <div>
    <input type="checkbox" :id="`checkbox_${friend.userId}`" :value="friend.username"
      v-model="$store.state.selectedOptions" />
    <label :for="`checkbox_${friend.userId}`">{{ friend.username }}</label>
  </div>
</template>
  
<script>
import { useStore } from 'vuex';
export default {
  props: {
    friend: {
      type: Object,
      required: true
    },
  },
  computed: {
    isChecked() {
      return this.isSelected(this.friend);
    },
  },
  methods: {
    getOptionId(friend) {
      return `${friend.userId}`;
    },
    toggleOption() {
      this.$store.commit("SET_SELECTED_OPTIONS", this.friend);
    },
    isSelected(option) {
      return this.$store.state.selectedOptions.includes(option.id);
    },
  },
};
</script>
  
<style scoped>
.label-text {
  font-weight: bold;
  color: #000;
  cursor: pointer;
}

input[type="checkbox"].toggle {
  opacity: 0;
}
</style>
