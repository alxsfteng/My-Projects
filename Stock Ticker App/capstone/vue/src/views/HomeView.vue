<template>
  <div class="home">
    <header>

      <!-- <Video></Video> -->
      <div class="heading-dashboard">
        <div class="research-button-flex">
          <button class="research-buttons learning-button" @click="openLearningPortal">Learning Portal</button>
          <button class="research-buttons stock-button" @click="openStockResearch">Research Stocks</button>
          <button class="research-buttons companies-button" @click="openCompaniesPage">Companies</button>


          <!-- <button class="research-buttons">Learning Portal</button>
          <button class="research-buttons">Research Stocks</button>
          <button class="research-buttons">Companies</button> -->
        </div>
        <div class="banner-bold">
          <h1>{{ getUsername }}</h1>
        </div>

        <img :src="profilePicture" alt="Profile Picture" class="profile-picture">
      </div>

    </header>
    <div class="dash-main-container">
      <div class="games-list">
        <GamesList></GamesList>
      </div>
      <div class="game-invite-content-container">
        <div id="nav-home">
          <button class="tabs" @click="showStartGame">Start Game</button>
          <button class="tabs" @click="showFriendsList">Friends List</button>
          
          <!-- <button class="tabs" @click="showMakeATrade">Make A Trade</button> -->
        </div>
        <div v-show="showStart">
          <StartGame v-if="showStart" @invite-click="handleInviteClick"></StartGame>
        </div>
        <div v-show="showFriends">
          <FriendsList v-if="showFriends" @invite-click="handleInviteClick"></FriendsList>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import StartGame from '../components/StartGame.vue';
import FriendsList from '../components/FriendsList.vue';
import Video from '../components/Video.vue';
// import Trade from '../components/Trade.vue';
import profilePicture from '@/assets/pepe.png';
import GamesList from '../components/GamesList.vue';

export default {
  components: {
    // Video,
    FriendsList,
    StartGame,
    // Trade,
    GamesList
  },

  computed: {
    getUsername() {
      return this.$store.getters.getCurrentUsername;
    },
    profilePicture() {
      return profilePicture;
    },
  },

  data() {
    return {
      showFriends: true,
      showStart: false,
      // showTrade: false,
      // ... other data properties from your Home component
    };
  },

  methods: {
    openLearningPortal() {
      const newTab = window.open('https://www.easypeasyfinance.com/stocks-for-kids-financial-literacy/', '_blank');
      if (newTab) {
        newTab.focus(); // If a new tab was successfully opened, focus on it
      } else {
        // If the browser blocked the new tab, you might want to display a message to the user
        alert('Please allow pop-ups for this site to open the learning portal.');
      }

    },
    openStockResearch() {
      const newTab = window.open('https://www.ssga.com/us/en/intermediary/etfs/funds/spdr-dow-jones-industrial-average-etf-trust-dia?WT.mc_id=ps_etf-eqg_liquidity-funds_us_google_text_psnb_mf2_dia_sep22&gad_source=1&gclid=CjwKCAiAg9urBhB_EiwAgw88mcnzz7TBevYxTdXKjIOWAFevaFY7YyaLLiETqevAKLz1d9YD98OQPxoCHlEQAvD_BwE&gclsrc=aw.ds', '_blank');
      if (newTab) {
        newTab.focus(); // If a new tab was successfully opened, focus on it
      } else {
        // If the browser blocked the new tab, you might want to display a message to the user
        alert('Please allow pop-ups for this site to open the learning portal.');
      }
    },
    openCompaniesPage() {
      const newTab = window.open('https://www.investopedia.com/ask/answers/who-or-what-is-dow-jones/#:~:text=Dow%20Jones%20%26%20Company%20is%20one,news%20companies%20in%20the%20world.&text=Dow%20Jones%20started%20the%20Dow,stock%20indexes%20in%20the%20world.', '_blank');
      if (newTab) {
        newTab.focus(); // If a new tab was successfully opened, focus on it
      } else {
        // If the browser blocked the new tab, you might want to display a message to the user
        alert('Please allow pop-ups for this site to open the learning portal.');
      }
    },
    // ... your existing methods for navigating between tabs


    showFriendsList() {
      this.showFriends = true;
      this.showStart = false;
      this.showTrade = false;
    },
    showStartGame() {
      this.showFriends = false;
      this.showStart = true;
      this.showTrade = false;
    },

    handleInviteClick() {
      if (this.showFriends == true) {
        this.showFriends = false;
        this.showStart = true;
      } else {
        this.showFriends = true;
        this.showStart = false;
      }
      //this.showTrade = false;
    }
    // showMakeATrade() {
    //   this.showFriends = false;
    //   this.showStart = false;
    //   this.showTrade = true;

    // }
  },

};
</script>
<style scoped>
header {
  margin: auto;
}

.banner-bold {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 70%;
  height: 6.5rem;
  border-radius: 1.25106rem;
  border: 3px dashed #000;
  background: #4CBB17;
  box-shadow: 4px 6px 0px 4px #000;
  box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;




}

#nav-home {
  margin-left: 4.5rem;
  display: flex;
}

.research-button-flex {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: .1rem;
  margin-left: 3%;
}

.dash-main-container {
  width: 100%;
  justify-content: space-between;
  display: flex;
}


.tabs:nth-of-type(1) {
  display: flex;
  width: 40%;
  height: 48px;
  padding: 16px 0px;
  justify-content: center;
  align-items: center;
  flex-shrink: 0;
  border-radius: 16px 16px 0px 0px;
  border: 2px solid var(--text-subtitle, #000);
  background: #DCFD00;
  box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

.tabs:nth-of-type(2) {
  display: flex;
  width: 40%;
  height: 48px;
  padding: 16px 0px;
  justify-content: center;
  align-items: center;
  flex-shrink: 0;
  border-radius: 16px 16px 0px 0px;
  border: 2px solid var(--text-subtitle, #000);
  background: #A5DCFF;
  box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

/* .tabs:nth-of-type(3) {
  display: flex;
  width: 120px;
  height: 48px;
  padding: 16px 0px;
  justify-content: center;
  align-items: center;
  flex-shrink: 0;
  border-radius: 16px 16px 0px 0px;
  border: 2px solid var(--text-subtitle, #000);
  background: #A5DCFF;
  box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;
} */

.research-buttons {
  width: 9.375rem;
  height: 2.5rem;
  padding: 0.5rem 0.75rem;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
  border-radius: 1rem;
  border: 2px solid var(--text-subtitle, #000);
  background: var(--primary-brand, #DCFD00);
  cursor: pointer;
  box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

.research-buttons:hover {
  background: var(--primary-brand, #dffb28);
  box-shadow: -2px -3px 4px 0px rgba(0, 0, 0, 0.29) inset;
}

.research-buttons:active {
  background: var(--primary-brand, #dffb28);
  box-shadow: -2px -3px 4px 0px rgba(0, 0, 0, 0.29) inset;
}


.heading-dashboard {
  display: flex;
  justify-content: space-around;
  margin-bottom: 25px;
  margin-top: 25px;
  width: 96.25%;
  height: 6.8125rem;
  border-radius: 0.625rem 0.625rem 0.3125rem 0.3125rem;
}

.profile-picture {
  width: 9rem;
  height: 9rem;
  transform: rotate(0.112deg);
  flex-shrink: 0;
  border-radius: 50%;
  border: 2px solid #000;
  background: #FEF9ED;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25) inset;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  object-fit: contain;
  top: -20px;
  position:relative;
}

nav {
  display: flex;
}

.home {
  width: 90vw;
  height: 928px;
  border-radius: 10px;
  border: 4px solid #0267A6;


  background: #A5DCFF;
  margin: auto;
}

.games-list {
  width: 50%;
}
</style>