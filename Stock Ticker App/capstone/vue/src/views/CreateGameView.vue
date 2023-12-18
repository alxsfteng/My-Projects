<template>
    <div>
        <div class="container-game-view">
            <header class="game-name-info">

            </header>
            <div class="wrapper-games-trades">
                <div class="chart-name">
                    <GameDisplay></GameDisplay>
                    <portfolio></portfolio>
                </div>
                <Trade @open-modal="openTradeModal"></Trade>
                <transition name="modal-fade">
                    <div class="modal" v-show="isModalVisible">
                        <CompletedTrade v-if="isModalVisible" @modal-closed="closeModal"></CompletedTrade>
                    </div>
                </transition>

            </div>
            <CompetitorList></CompetitorList>

        </div>

    </div>
</template>
<script>
// import StartGame from '../components/StartGame.vue';
import GameDisplay from '../components/GameDisplay.vue';
import Trade from '../components/Trade.vue';
import CompetitorList from '../components/CompetitorList.vue';
import Portfolio from '../components/Portfolio.vue';
import CompletedTrade from '../components/CompletedTrade.vue';

export default {
    components: {
        GameDisplay,
        CompetitorList,
        Trade,
        Portfolio,
        CompletedTrade,
    },
    data() {
        return {
            isModalVisible: false,
        };
    },

    methods: {
        openTradeModal() {
            // Trigger the modal in the parent component
            this.isModalVisible = true;
        },
        closeModal() {
            this.isModalVisible = false;
        },

    },

}
</script>
<style>
.container-game-view {
    width: 89vw;
    margin: 0 auto;
    padding: 20px;
    background: #FEF9ED;
    font-family: Arial, sans-serif;
    border-radius: 10px;
}

.game-name-info {
    background-color: #A5DCFF;
    color: #fff;
    padding: 15px;
    text-align: center;
    border-radius: 10px;
}

.wrapper-games-trades {
    display: flex;
    justify-content: space-between;
    margin: 20px;
}


.GameDisplay {
    flex: 0 0 60%;
    background-color: #fff;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}


.portfolio {
    flex: 0 0 30%;
    background-color: #fff;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}


.Trade {
    flex: 0 0 30%;
    background-color: #fff;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    margin-top: 20px;
}


.CompetitorList {
    margin-top: 20px;
    background-color: #fff;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>