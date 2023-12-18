<!-- follow the logic from the lecture_final code from 13_Vue_Component_Communication > ReviewDisplay.vue file to set this up  -->
<!-- it is a child of the GamesList.vue file -->

<!-- GameDisplay.vue -->
<template>
  <div>
    
    <div class="container-game-display">
      <p>G a m e :</p>
      <div class="game-name-wrapper">
          <span :class="{ 'shadow2': true }" contenteditable>{{ gameDetails.game.gameName }}</span>
      </div>
      <p class="players-list"> players in this game:</p>
      <p class="players-list" v-for="player in gameDetails.game.players" :key="player.gamePlayerId"> {{ player.userName }}</p>
      <div class="flex-cash-trade">
        <!-- TODO: .GET the /portfolio for registered user for this game id at created() hook, write a method to .reduce it ADD it to users balance and dynamically display it here -->
      </div>
      <div class="portfolio-game-display">
        <!-- TODO: Modal for a trade completed -->
      </div>
    </div>
  </div>
</template>
<script>
import GameService from '../services/GameService';
// import gameService from '../services/GameService';
// import tradingService from '../services/TradingService';

export default {
  props: {
    //props from the gamesList
    gameData: [],
  },
  data() {
    return {
      magicText: 'Magic',
    };
  },
  computed: {
    gameDetails() {
      // TODO: use this.gameDetailsId to break down prop values for proper hydration
      return this.$store.state.currentGameDetails;
    },
    // gameNameHeading() {
    //   return this.gameName;
    // }
  },
  methods: {
  },
  // created() {
  //   this.fetchGameDetails();
  // },
};
</script>

<style>
.game-name-wrapper {
  margin-left: .8rem;
  position: relative;
  top: -20%;
  font-weight: 800;
  font-size: 16px;
  
}

.players-list {
  margin-left: 1%;
  text-transform: uppercase;
  letter-spacing: 2px;
  
}


.game-name-wrapper {
  font-family: 'Poppins', sans-serif;
  text-transform: uppercase;
  font-size: 3vw;
  text-align: center;
  font-weight: 600;
  position: relative;
  margin: 0;
  border-bottom: #3D405C solid 2px;


  color: #91d4ff;

  &.flourish {
    color: #0A8CF1;
  }

  &:before,
  &:after,
  span:before,
  span:after {
    content: attr(data-heading);
    position: absolute;
    overflow: hidden;
    left: 0;
    top: 0;
    width: 100%;
    z-index: 5;
    font-weight: normal;
    text-shadow: none;
  }

  &:before,
  &:after,
  span:before,
  span:after {
    font-family: 'Poppins', sans-serif;
  }
}

span {
  &.shadow2 {
    &::before {
      font-family: 'Poppins', sans-serif;
      color: #cbc7e3;
      z-index: -1;
    }
  }
}

.container-game-display p {
  margin-bottom: -10px;
  
}
</style>