<!-- follow the logic from the lecture_final code from 13_Vue_Component_Communication > ReviewList.vue file to set this up  -->
<!-- this is the parent of the CompetitorDisplay.vue file -->
<template>
    <div class="competitorList-container">
        <h1 class="leaderboard">Leaderboard</h1>
        <div class="flex-comp-list">
            <!-- <div class="competitor-display"> -->
            <CompetitorDisplay v-for="playerInfo in sortedPlayerDetails" v-bind:key="playerInfo.playerId"
                v-bind:playerInfo="playerInfo" class="comp-list"></CompetitorDisplay>
            <!-- </div> -->
        </div>
    </div>
</template>
<script>
import CompetitorDisplay from './CompetitorDisplay.vue';
import tradingService from '../services/TradingService';
export default {
    components: {
        CompetitorDisplay,
    },
    data() {
        return {
            playerDetails: [],
        };
    },
    computed: {
        sortedPlayerDetails() {
            return [...this.playerDetails].sort((a, b) => b.percentageChange - a.percentageChange);
        },
    },
    created() {
        console.log('Before dispatch');
        this.$store.dispatch('fetchUserGameList').then(response => {
            console.log('After dispatch. Response:', response);
            if (response && response.data) {
                this.userGameList = response.data;
                this.fetchPlayerDetailsForEachGame();
            } else {
                console.error('Error fetching user game list. Response or response.data is undefined. Tough luck, buttercup');
            }
        });
    },
    methods: {
        calculatePercentageGainOrLoss(startingCash, currentCash) {
            const percentageChange = ((currentCash - startingCash) / startingCash) * 100;
            return percentageChange;
        },
        fetchPlayerDetailsForEachGame() {
            // Helper method to grab the playerList from the getUserGameList and iterate this object
            this.userGameList.forEach(game => {
                const players = game.players;
                const gameName = game.gameName;

                // Loop through each player to fetch and set their details
                players.forEach(player => {
                    const playerId = JSON.stringify(player.gamePlayerId);
                    const username = player.userName;
                    const startingCash = 100000;
                    const currentCash = player.cash;

                    // Fetch portfolio worth for the current player using playerId
                    tradingService.getPortfolioWorth(playerId)
                        .then(responsePortfolioWorth => {
                            const totalValue = currentCash + responsePortfolioWorth.data;
                            const percentageChange = this.calculatePercentageGainOrLoss(startingCash, totalValue);

                            const playerInfo = {
                                playerId: playerId,
                                username: username,
                                totalValue: totalValue,
                                currentCash: currentCash,
                                percentageChange: percentageChange,
                                gameName: gameName,
                            };

                            console.log('Player Username:', username);
                            console.log('Fetched Portfolio Worth:', responsePortfolioWorth.data);
                            console.log('Current Cash:', currentCash);
                            console.log('Total Value (Portfolio + Cash):', totalValue);
                            console.log('Percentage Change:', percentageChange);

                            this.playerDetails.push(playerInfo);
                        })
                        .catch(error => {
                            console.error('Error fetching player data:', error);
                        });
                });
            });
        },
        formatPercentage(number) {
            if (typeof number !== 'number') {
                alert('You have tried to format something that is not a number');
            }
            const percentage = number * .01;
            return percentage.toLocaleString(undefined, { style: 'percent', minimumFractionDigits: 2 });
        }
    },
}
</script>
<style>
.competitorList-container {
    display: flex;
    width: 100%;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    flex-shrink: 0;
    border-radius: 10px;
    border: 1px solid #000;
    background: #378d0f;

    box-shadow: -4px -4px 0px 0px rgba(0, 0, 0, 0.19) inset;
}

.competitorList-container h1 {
    width: 200px;
    height: 30px;
    flex-shrink: 0;
    color: #000;

    text-align: center;
    font-family: Poppins;
    font-size: 15px;
    font-style: normal;
    font-weight: 500;
    line-height: normal;
    margin-top: 0;
}

.competitor-display {
    display: flex;
    width: 200px;
    margin: 8px;
}

.comp-list{
    width: 100%;
    position: relative;
    left: -40%;;
}
</style>