<template>
  <div>
    <div class="container-start">
      <div class="game-name-container">
        <label for="gameName">Game Name?</label>
        <div class="game-name-input">
          <input autocomplete="off" id="gameName" type="text" v-model="gameName" placeholder="Enter unique game name" />
        </div>
        <div class="button2-container">
          <button @click="generateRandomName">Generate Game Name</button>
        </div>
      </div>
      <div class="date-fields">
        <div class="input-container">

          <label for="startDate">Start Date:</label>
          <DatePicker input-class="date-input" v-model="startDate" :id="startDate"
            v-on:update:modelValue="handleStartDateValid">
          </DatePicker>
        </div>
        <div class="input-container">
          <label for="endDate">End Date:</label>
          <DatePicker input-class="date-input" v-model="endDate" id="endDate" v-on:update:modelValue="handleEndDateValid">
          </DatePicker>
        </div>
      </div>
      <Counter numberType="dollars" label="Starting Bankroll?" :startingBankroll="startingBankroll"
        :defaultCounter="100000" />
      <Counter :startingBankroll="startingBankroll" numberType="dollars" label="Commission:" :defaultCounter="19.95" />
      <div>
        <h3>Game Difficulty?</h3>
        <div class="toggle-container">
          <div class="toggle-switch easy-toggle">
            <input type="checkbox" id="easyToggle" v-model="easyChecked" @change="handleDifficultyChange('1')"
              :disabled="mediumChecked || hardChecked" />
            <label for="easyToggle">Easy </label>
          </div>
          <div class="toggle-switch medium-toggle">
            <input type="checkbox" id="mediumToggle" v-model="mediumChecked" @change="handleDifficultyChange('2')"
              :disabled="easyChecked || hardChecked" />
            <label for="mediumToggle">Medium</label>
          </div>
          <div class="toggle-switch hard-toggle">
            <input type="checkbox" id="hardToggle" v-model="hardChecked" @change="handleDifficultyChange('3')"
              :disabled="easyChecked || mediumChecked" />
            <label for="hardToggle">Hard </label>
          </div>
        </div>
      </div>
      <button class="invite-friends" @click="notifyParent">
        + Invite Friends to Play
      </button>
      <!-- method should push the host creating the game to an array of players. -->
      <button class="create-game" @click="createNewGame">Create Game</button>
    </div>
  </div>
  <div>

  </div>
</template>
<script>
import Counter from './addSubtractCounter.vue'
import gameService from '../services/GameService';
import { useStore } from 'vuex';
import DatePicker from 'vue3-datepicker';
//import FriendsList from '../components/FriendsList.vue';

export default {

  components: {
    Counter,
    DatePicker
  },
  data() {
    const store = useStore();
    return {
      showFriendsList: false,
      gameName: '',
      startDate: null,
      endDate: null,
      difficulty: 1,
      easyChecked: true, // Default to Easy being checked for MVP
      mediumChecked: false,
      hardChecked: false,
      selectedPlayerCount: null,
      startingBankroll: 100000, // Default to 100k per MVP
      commissionFee: 19.95,
      playersInvited: store.state.selectedOptions,
      playerCounts: [
        { id: 'one', label: '1', position: 'left', value: '1' },
        { id: 'two', label: '2', position: 'left', value: '2' },
        { id: 'three', label: '3', position: 'left', value: '3' },
        { id: 'four', label: '4', position: 'right', value: '4' },
        { id: 'five', label: '5', position: 'right', value: '5' },
        { id: 'six', label: '6', position: 'right', value: '6' },
      ],
      game: {
        difficulty: 1,
        gameName: '',
        startDate: this.$store.state.startDate,
        endDate: this.$store.state.endDate,
        commissionFee: 19.95,
        playerCount: 1,
        cash: this.$store.state
      }
    };
  },
  computed: {
    getDifficulty() {
      if (this.easyChecked) return '1';
      else if (this.mediumChecked) return '2';
      else if (this.hardChecked) return '3';
      return '1'; // Default difficulty
    },
  },

  watch: {
    localStartingBankroll(newVal) {
      // Commits the starting bankroll to the store
      useStore().commit('SET_STARTING_BANKROLL', newVal);
    },
  },
  methods: {
    createNewGame() {

      const game = {
        gameName: this.gameName,
        gameStart: this.startDate.toISOString(), // Convert Date to string 
        gameEnd: this.endDate.toISOString(),   // Convert Date to string 
        difficultyId: 1,
        commissionFee: this.commissionFee,
        cash: this.startingBankroll, 
        playerCount: this.selectedPlayerCount,
        invitedFriends: this.$store.state.selectedOptions,
      };

      // Log the game object to the console for debugging
      console.log('Game object:', game);
      // Log the array for debugging
      for (let i = 0; i < game.invitedFriends.length; i++) {
        console.log(game.invitedFriends[i]);
      }

      gameService.createGame(game)
        .then(response => {
          if (response.status === 200) {
            const { gameDetailsId, gameName, gameStart, gameEnd, playerCount, difficultyId, gameStatusId, commissionFee, players } = response.data;

            this.$store.commit('SET_NEW_GAME_DETAILS', response.data);
            console.log('Current Vuex Store State:', this.$store.state.gameDetails);

            if (players && players.length > 0) {
              console.log("Players:");
              players.forEach(player => {
                const { gamePlayerId, userId, cash, host } = player;
                console.log("  - Player ID:", gamePlayerId);
                console.log("    User ID:", userId);
                console.log("    Cash:", cash);
                console.log("    Host:", host);
              });
            }
            // reloads to show the game in gamesList
            window.location.reload();
          }
        })
        .catch(error => {
          console.error('Error creating game:', error);
        });
    },

    generateRandomName() {
      const randomWords = [
        "Gamer", "Crazed", "Questing", "Fun", "Cashed", "Bull", "Zoomin", "Blitzed", "Stonks", "Charmed", "Risky",
        "Dart", "Bolt", "Fury", "Rush", "Zen", "Snap"
      ];
      const randomWord1 = randomWords[Math.floor(Math.random() * randomWords.length)];
      const randomWord2 = randomWords[Math.floor(Math.random() * randomWords.length)];
      const salt = Math.floor(100 + Math.random() * 900); // Generates a number between 100 and 999
      const randomName = `${randomWord1}${randomWord2}${salt}`;
      // Sets the generated name to the gameName
      this.gameName = randomName;
    },

    handleStartDateValid(startDate) {
      // If endDate is set and startDate is after endDate, reset endDate
      if (this.endDate && startDate > this.endDate) {
        this.endDate = null;
        window.alert('End date must be after the start date. Please try again.');
        return;
      }
    },

    handleEndDateValid(endDate) {
      // If startDate is set and endDate is before startDate, reset startDate
      if (this.startDate && endDate < this.startDate) {
        this.startDate = null;
        window.alert('End date must be after the start date. Please try again.');
        return; // Does not commit to the store if validation fails
      }
    },

    handleDifficultyChange(difficulty) {
      // Handle the logic when a difficulty checkbox is changed
      if (difficulty === '1') {
        this.mediumChecked = false;
        this.hardChecked = false;
      } else if (difficulty === '2') {
        this.easyChecked = false;
        this.hardChecked = false;
      } else if (difficulty === '3') {
        this.easyChecked = false;
        this.mediumChecked = false;
      }
    },

    notifyParent(){
      this.$emit('invite-click');
    }

    // getDifficulty() {
    //   if (this.easyChecked) return '1';
    //   else if (this.mediumChecked) return '2';
    //   else if (this.hardChecked) return '3';
    //   return 'easy'; // Default difficulty
    // },
  }
};
</script>
  
<style scoped>
.invite-friends {
  font-family: Poppins;
  margin-top: 10px;
  margin-bottom: 10px;
  font-size: large;
  display: flex;
  width: 20rem;
  height: 3.75rem;
  padding: 0.5rem 0.75rem;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
  border-radius: 1.25rem;
  border: 2px solid var(--text-subtitle, #000);
  background: #DCFD00;
  /* Inner Shadow/Pressed */
  box-shadow: -16px -16px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

.invite-friends:active {
  /* Temporary pressed styles */
  border: 1.5px solid var(--text-subtitle, #000);
  justify-content: center;
  align-items: center;
  /* Inner Shadow/Sharp */
  box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

.create-game {
  font-family: Poppins;
  font-size: large;
  display: flex;
  width: 20rem;
  height: 2.5rem;
  padding: 1rem 1.5rem;
  justify-content: center;
  align-items: center;
  border-radius: 1rem;
  border: 2px solid var(--text-subtitle, #000);
  background: #A5DCFF;
  /* Inner Shadow/Pressed */
  box-shadow: -16px -16px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

.create-game:active {
  /* Temporary pressed styles */
  gap: 0.5rem;
  border: 1.5px solid var(--text-subtitle, #000);
  /* Inner Shadow/Sharp */
  box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

.container-start {
  margin-right: 3rem;
  display: inline-flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 20.017px;
  border-right: 3px solid #000;
  background: #FEF9ED;
  box-shadow: 6px 6px 0px 4px #000;
  padding: 30px 85px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 20.017px;

}

/* .main-container {
  display: grid;
  padding: 41px 24.839px 42.88px 25.161px;
  flex-direction: column;
  place-items: center;
  font-style: Poppins;
} */

.game-name-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 8px;
}

.game-name-input {
  display: flex;
  width: 318px;
  height: 40px;
  padding: 8px 0px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 26px;
  background: #A5DCFF;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25) inset;
}

.game-name-input input {
  width: 90%;
  height: 100%;
  padding-top: 5px;
  padding-left: 10px;
  border: none;
  background-color: rgba(255, 255, 255, 0.1);
}

.button2-container {
  display: flex;
  width: 128px;
  height: 32px;
  padding: 6.4px 9.6px;
  justify-content: center;
  align-items: center;
  gap: 6.4px;
  border-radius: 20px;
  border: 1.25px solid #000;
  background: #FEF9ED;
  box-shadow: -6.4px -6.4px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

.button2-container button {
  border: none;
  background-color: rgba(0, 0, 0, 0);
}

.date-fields {
  display: flex;
  align-items: center;
}

.input-container {
  display: flex;
  height: 40px;
  padding: 8px 8px;
  /* margin: 8px 8px; */
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 25px;
  background: #DCFD00;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25) inset;

}

.input-container :deep(input) {
  border: none;
  background: transparent;
}

/* zomg attribution: https://stackoverflow.com/users/5213573/mitch-lillie
scoped will only work to apply styling to child components directly referenced in current Vue component. 
In this case, datepicker is creating its own child input which will not be affected by the style, 
unless you use :deep(<inner-selector>) as shown above line 374. */

.container p {
  color: #000;
  font-family: Poppins;
  font-size: 16px;
  font-style: normal;
  font-weight: 500;
  line-height: 16px;
}

.regular-radio {
  width: 24px;
  height: 24px;
  border-radius: 32px;
  border: 2px solid #000;
  background: var(--surface-01, #FEF9ED);
  box-shadow: -6.4px -6.4px 0px 0px rgba(0, 0, 0, 0.25) inset;
}

.radioButton-board {
  display: flex;
  justify-content: center;
  align-items: center;
}

.toggle-switch input {
  display: none;
}

/* Style toggle switch label */
.toggle-switch label {
  position: relative;
  cursor: pointer;
  padding: 5px 10px;
  border: 1px solid #ccc;
  border-radius: 20px;
  background-color: #f2f2f2;
  transition: background-color 0.3s ease;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 24px;
}


.toggle-switch input:checked+label {
  color: black;
}

.easy-toggle input:checked+label {
  background: #4CBB17;
}

.medium-toggle input:checked+label {
  background: #4CBB17;
}

.hard-toggle input:checked+label {
  background: #4CBB17;
}


.toggle-switch input+label::after {
  content: '';
  display: block;
  position: relative;
  width: 25px;
  height: 25px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #ccc;
  transition: transform 0.3s ease;
}


.toggle-switch input:checked+label::after {
  transform: translateX(26px);
}

.toggle-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 24px;
}
</style>

