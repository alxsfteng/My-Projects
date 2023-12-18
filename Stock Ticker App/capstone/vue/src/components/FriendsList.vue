<template>
  <div class="container-friend">
    <!-- <div class="main-container"> -->
    <div class="friends-list">
      <h3>Invite Friends to a Game!</h3>
      <div class="friends-scroll">
      <MultiCheckbox class="multi-checkbox" v-for="friend in friends" :key="friend.id" :friend="friend"></MultiCheckbox>
      </div>
      <div class="selected-friends">Selected Friends: {{ $store.state.selectedOptions }}</div>
      
      <button class="invite-button" @click="notifyParent">Invite</button>
    </div>
  </div>
  <!-- </div> -->
</template>
  
<script>
import userService from '../services/UserService';
import MultiCheckbox from '../components/MultiCheckbox.vue';
import router from '../router';

export default {
  components: {
    MultiCheckbox,

  },
  data() {
    return {
      friends: [],
    };
  },
  created() {
    this.getUsers();
  },
  methods: {
    getUsers() {
      userService.list()
        .then(response => {
          this.friends = response.data;
          this.$store.commit("SET_FRIENDS", response.data);
        })
        .catch(error => {
          console.error('Error getting users:', error);
        });
    },
    inviteFriends() {
      router.push({ name: 'home' });

    },

    notifyParent(){
      this.$emit('invite-click');
    }
  },
};

</script>

<style scoped>
.friends-list {
  width: 28rem;
  height: 100%;
  /* flex-shrink: 0; */
  border-radius: 10px;
  border-right: 2px solid #000;
  
  background: #FFF;
  box-shadow: -4px -4px 0px 0px rgba(0, 0, 0, 0.19) inset;
  overflow-y: auto;
  border: 1px solid #ccc;
}
.friends-list h3{
  color: #000;
font-family: Poppins;
font-size: 30px;
font-style: normal;
font-weight: 500;
line-height: normal;
text-align: center;
}
.container-friend {
  display: inline-flex;
  height:522px;
  padding: 4rem 4rem 4rem;
  flex-direction: column;
  margin-right: 3rem;
  justify-content: center;
  align-items: center;
  border-radius: 20.017px;
  border-right: 3px solid #000;
  background: #FEF9ED;
  box-shadow: 6px 6px 0px 4px #000;
  
}

.invite-button {
  display: flex;
  width: 155px;
  height: 48px;
  padding: 8px 12px;
  justify-content: center;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
  border-radius: 16px;
  border: 4px solid #000;

  background: #A5DCFF;


  box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;


}
.friends-scroll {
  max-height: 330px; 
  overflow-y: auto; 
  scrollbar-width: thin;
  scrollbar-color:#A5DCFF;
  border-radius: 16px;
  position: relative;
  right: 5px;

}
.friends-scroll::-webkit-scrollbar {
  width: 22px;
}

.friends-scroll::-webkit-scrollbar-thumb {
  background-color: #DCFD00;
  border-radius: 16px;
}
.friends-scroll::-webkit-scrollbar-thumb:hover {
  background-color: #A5DCFF;
}
.selected-friends{
  
}

.multi-checkbox {

margin-left: 1rem;
text-indent: 10px;
display: flex;
height: 2.5rem;
padding: 0.75rem 1rem;
align-items: center;
border-radius: 0.5rem;
border: 1px solid var(--Sky-Lighter, #F2F4F5);
background: var(--Sky-Lighter, #F2F4F5);
}
/* .main-container{
    width: 900px;
height: 671px;
flex-shrink: 0;
border-radius: 20.017px;
border: 3px solid #000;

background: #FEF9ED;

box-shadow: 6px 6px 0px 4px #000;
} */
</style>