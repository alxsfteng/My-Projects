<!-- follow the logic from the lecture_final code from 13_Vue_Component_Communication > ReviewList.vue file to set this up  -->
<!-- it is a parent of the Game Display.vue file -->

<!-- GamesList.vue -->
<template>
    <div class="game-display-container">
        <div v-if="userGameList.length === 0">
            <p>No games Available. Start a new game! Stock Ticker is an educational stock trading game designed to provide a
                realistic simulation of the stock market. It offers an immersive experience for beginners to learn the
                basics of investing, trading, and portfolio management without the actual financial risk.</P>

            <p>Stock Ticker: An educational stock market simulation.</p>

            <p>1. Duration: Trade within specified start and end dates.</p>
            <p>2. Participation: Friends invited; each starts with $100,000.</p>
            <p>3. Commissions: $19.95 fee per trade; impacts profits.</p>
            <p>4. Stocks: Trade from 25 companies of the DOW Jones 30.</p>
            <p>5. Trading: Buy/sell anytime; limits for diversified trades.</p>
            <p>6. Portfolio Management: Actively manage for max returns; diversify for reduced risk.</p>
            <p>7. Real-Time Data: Daily updates reflecting market changes.</p>
            <p>8. Educational Support Above: Tutorials and tips provided for learning.</p>
            <p>9. Winning: Highest portfolio value at end wins; includes cash after fee deductions.</p>







        </div>
        <div v-else>
            <!-- Games List For Loop -->
            <div v-for="(game, index) in userGameList" :key="index" class="game-list-item">
                <div class="game-item-top-flex">
                    <div class="portfolio-section">
                        <p>{{ formatDollars(getPortfolioWorthForGame(game.gameDetailsId)) }}</p>
                        <h3>Portfolio Value</h3>

                    </div>
                    <div class="players-commission-game-item-flex">
                        <p class="game-property">Players: {{ game.players.length }}</p>
                        <p class="game-property">Commission Fee: ${{ (game.commissionFee) }}</p>
                    </div>
                    <div class="search-bar">
                        <!-- TODO: This needs to get wired up with a get request for Users Balance. -->
                        {{ formatDollars(getCashForSpecificGame(game.gameDetailsId)) }}
                    </div>
                </div>
                <div class="header-section">
                    <div class="game-name-item">
                        <router-link :to="{ name: 'gameStart' }">
                            <button class="open-game-btn" @click="selectGame(game)"><span>Game: </span>
                                <h3> {{ game.gameName }}</h3>
                            </button>
                        </router-link>
                    </div>
                    <div class="dates-games-list-item">
                        <p class="game-property">Began: {{ formatDate(game.gameStart) }}</p>
                        <p class="game-property">End Date: {{ formatDate(game.gameEnd) }}</p>
                    </div>

                </div>
                <!-- <GameDisplay :game-details-id="game.gameDetailsId" :players="game.players" /> -->

            </div>
            <!-- Games List Loop Ends -->
        </div>
    </div>
</template>

<script>
// import GameDisplay from './GameDisplay.vue';
import gameService from '../services/GameService';
import tradingService from '../services/TradingService';

export default {
    components: {
        // GameDisplay,
    },
    data() {

        return {
            //TODO: a conditional render if there are no games in the store that prompts the user to start a game
            userGameList: [],
            selectedGame: null,
            selectedGameId: null,
            playerIdOfUser: null,
            portfolioWorthArray: [],

        };
    },
    computed: {
        //TODO: make sure that this feeds from the gameList
        fakePlayerCash() {
            const currentUsername = this.$store.getters.getCurrentUsername;
            const cashForGames = [];

            this.userGameList.forEach(game => {
                const currentUser = game.players.find(player => player.userName === currentUsername);
                if (currentUser) {
                    cashForGames.push({ gameId: game.gameDetailsId, cash: currentUser.cash })
                }
            });
            return cashForGames;
        },
    },
    created() {
        //this works! check store actions for ex.
        console.log('Before dispatch');
        this.$store.dispatch('fetchUserGameList').then(response => {
            console.log('After dispatch. Response:', response);
            if (response && response.data) {
                this.userGameList = response.data;
                this.fetchPlayerPortfolio();
            } else {
                console.error('Error fetching user game list. Response or response.data is undefined. Tough luck, buttercup');
            }
        });
    },
    methods: {
        getPortfolioWorthForGame(gameDetailsId) {
            const portfolioWorth = this.portfolioWorthArray.find(portfolio => portfolio.gameId === gameDetailsId);
            return portfolioWorth ? portfolioWorth.portfolioWorth : 'Not available';
        },
        fetchPlayerPortfolio() {
            const currentUsername = this.$store.getters.getCurrentUsername;
            // Iterate through each game in the userGameList
            this.userGameList.forEach(game => {
                const currentPlayer = game.players.find(player => player.userName === currentUsername);
                if (currentPlayer) {
                    const playerId = JSON.stringify(currentPlayer.gamePlayerId);
                    // Fetch portfolio worth for the current player using playerId
                    tradingService.getPortfolioWorth(playerId)
                        .then(responsePortfolioWorth => {
                            const portfolioWorth = responsePortfolioWorth.data;
                            console.log(`Portfolio Worth for Game ${game.gameDetailsId}: ${portfolioWorth}`);
                            // Assign portfolio worth to the respective game ID
                            this.portfolioWorthArray.push({
                                gameId: game.gameDetailsId,
                                portfolioWorth: portfolioWorth
                            });
                        }).catch(error => {
                            console.error('Error fetching portfolio worth:', error);
                        });
                }
            });
        },
        getCashForSpecificGame(gameDetailsId) {
            const currentUsername = this.$store.getters.getCurrentUsername;
            const game = this.userGameList.find(game => game.gameDetailsId === gameDetailsId);

            if (game) {
                const currentPlayer = game.players.find(player => player.userName === currentUsername);
                if (currentPlayer) {
                    return currentPlayer.cash;
                }
            }
        },

        selectGame(game) {

            // Existing values in the store b/c bugs
            console.log('Game before clearing store:', JSON.stringify(this.$store.state.currentGameDetails, null, 2));
            console.log('PLayerId before clearing store:', JSON.stringify(this.$store.state.playerId, null, 2));

            // Clears existing values in the store when user selects new game!
            this.$store.commit('SET_CURRENT_PLAYER_ID', null);
            this.$store.commit('SET_CURRENT_GAME_DETAILS', null);
            this.$store.commit('SET_CURRENT_GAME_DETAILS_ID', null);
            this.$store.commit('SET_PORTFOLIO', null);
            this.$store.commit('SET_PORTFOLIO_WORTH', null);
            this.$store.commit('SET_CASH', null);

            console.log('Game after clearing store:', JSON.stringify(this.$store.state.currentGameDetails, null, 2));
            console.log('PlayerId after clearing store:', JSON.stringify(this.$store.state.playerId, null, 2));

            // Set Vuex store with values from the selected game
            console.log('Before setting game details in store:', JSON.stringify(this.$store.state.currentGameDetails, null, 2));
            this.$store.commit('SET_CURRENT_GAME_DETAILS', {
                game
            });

            this.$store.commit('SET_CURRENT_GAME_DETAILS_ID', game.gameDetailsId);
            console.log('After setting game details Id in store:', JSON.stringify(this.$store.state.currentGameDetailsId, null, 2));

            // Fetches the playerId from the backend using the selected game to get the subsequent values from BE API
            gameService.getPlayer(this.$store.state.user.id, game.gameDetailsId)
                .then(responsePlayer => {
                    console.log('API Response:', JSON.stringify(responsePlayer.data.gamePlayerId));
                    const playerId = JSON.stringify(responsePlayer.data.gamePlayerId);
                    console.log(playerId)
                    this.$store.commit('SET_CURRENT_PLAYER_ID', playerId);
                    console.log('Value of playerId in Stored State:', this.$store.state.playerId);

                    return tradingService.getPortfolio(this.$store.state.playerId);
                })
                .then(responsePortfolio => {
                    this.$store.commit('SET_PORTFOLIO', responsePortfolio.data);
                    console.log('Value of portfolio in Stored State:', this.$store.state.portfolio);

                    return tradingService.getPortfolioWorth(this.$store.state.playerId);
                })
                .then(responsePortfolioWorth => {
                    this.$store.commit('SET_PORTFOLIO_WORTH', responsePortfolioWorth.data);
                    console.log('Value of portfolioWorth in Stored State:', this.$store.state.portfolioWorth);

                    return tradingService.getCashByPlayerId(this.$store.state.playerId);
                })
                .then(responseCash => {
                    this.$store.commit('SET_CASH', responseCash.data);
                    console.log('Value of portfolioWorth in Stored State:', this.$store.state.portfolioWorth);
                })
                .catch(error => {
                    console.error('Error fetching game data:', error);
                });
        },

        formatDate(dateString) {

            // Attribution: MattDeahy on StackOverflow
            // Makes our dates look nice 
            const date = new Date(dateString);
            const options = { year: 'numeric', month: 'long', day: 'numeric' };
            return date.toLocaleDateString('en-US', options);
        },

        formatPercentage(number) {
            if (typeof number !== 'number') {
                alert('You have tried to format something that is not a number');
            }
            const percentage = number * .01;
            return percentage.toLocaleString(undefined, { style: 'percent', minimumFractionDigits: 2 });
        },
        formatDollars(amount) {
            // Format a number as dollars
            const options = {
                style: 'currency',
                currency: 'USD',
            };
            return amount.toLocaleString('en-US', options);
        },

        // findGamePlayerIdForCurrentUser() {
        //     const userId = this.$store.state.user.id;
        //     const game = this.$store.state.currentGameDetails;
        //     const players = game.players || [];

        //     for (const player of players) {
        //         if (player.userId === userId) {
        //             this.gamePlayerId = player.gamePlayerId;
        //             return this.gamePlayerId;
        //         }
        //     }
        //     alert('No playerId was found.')
        //     return null;

        // },
    },

};
</script>

<style>
.game-display-container {
    margin-left: 3rem;
    margin-top: 3rem;
    width: 105%;
    max-height: 585px;
    min-height: 585px;
    overflow-y: auto;
    flex-shrink: 0;
    border-radius: 20px;
    border-left: 4px solid #000;
    border-bottom: 9px solid #000;
    padding: 1rem;
    background: #FEF9ED;
    padding-top: 2rem;
    padding-bottom: 2rem;
    box-shadow: -4px -4px 0px 0px rgba(0, 0, 0, 0.19) inset;
}

.game-item-top-flex {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 2px;
}

.portfolio-section {
    display: flex;
    flex-direction: column;
    font-family: Poppins;
    align-items: center;
    justify-content: flex-end;
    margin: 10px;
    width: 150px;
    height: 110.139px;
    flex-shrink: 0;
    border-radius: 6px;
    border: 2px solid #000;

    background: #DCFD00;

    box-shadow: 3px 3px 1px 1px rgba(0, 0, 0, 0.50);
}

.open-game-btn {
    display: flex;
    width: 180%;
    height: 95%;
    margin-top: 5px;
    padding: 2px 2px;
    justify-content: center;
    align-items: center;
    gap: 8px;
    flex-shrink: 0;
    border-radius: 15px;
    background: #A5DCFF;

    /* Inner Shadow */
    box-shadow: -6px -6px 0px 0px rgba(0, 0, 0, 0.25) inset;
    color: var(--text-subtitle, #000);
    font-family: Poppins;
    font-size: 15px;
    font-style: normal;
    font-weight: 500;
    line-height: 24px;
    transition: transform 0.3s ease, box-shadow 0.3s ease;

}

.open-game-btn:hover {
    background-color: #2caeff;
    transform: scale(1.03);
    border-radius: 15px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;
    color: whitesmoke;

}

.header-section {
    display: flex;
    width: 95%;
    height: 50px;
    align-items: space-between;
    flex-shrink: 0;
    border-bottom: 2px solid #000;
    border-radius: 5px;
    background: #DCFD00;
}

.game-name-item {
    display: flex;
    flex-grow: 2;
    margin-left: 8px;
    margin-bottom: 8px;
}

.dates-games-list-item {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    font-size: 10px;
    justify-content: center;
}

.dates-games-list-item p {
    margin: 4px 4px;

}

.search-bar {
    display: flex;
    margin-right: 5%;
    font-size: 24px;
    width: 180px;
    height: 3rem;
    padding: 8px 0px;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 25px;
    background: #A5DCFF;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25) inset;

}



.portfolio-section h3 {
    color: #2F3039;

    font-family: Poppins;
    font-size: 15px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
}

.portfolio-section h4 {
    color: #000;

    font-family: Poppins;
    font-size: 7.2px;
    font-style: normal;
    font-weight: 600;
    line-height: normal;
}

.game-display-container::-webkit-scrollbar {
    width: 16px;
}

.game-display-container::-webkit-scrollbar-track {
    background-color: #fff;
}

.game-display-container::-webkit-scrollbar-thumb {
    background-color: #DCFD00;
    border-radius: 16px;
}

.game-display-container::-webkit-scrollbar-thumb:hover {
    background-color: #A5DCFF;
}

.game-display-container p {
    color: #000;
    font-family: Poppins;
    font-size: 15px;
    font-style: normal;
    font-weight: 500;
    line-height: normal;
    text-align: center;
    margin: 1px;
}

.players-commission-game-item-flex {}
</style>