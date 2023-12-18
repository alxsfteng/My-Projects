<template>
    <div>
     
        <div class="main-container1">
           <!-- <stock-info></stock-info>
           <Portfolio></Portfolio> -->
           <!-- <completed-trade></completed-trade> -->
           <games-list></games-list>
           
            <!-- <CompetitorDisplay></CompetitorDisplay> -->
        </div>
    </div>
</template>
<script>
import StockInfo from '../components/StockInfo.vue';
import Portfolio from '../components/Portfolio.vue';
import CompletedTrade from '../components/CompletedTrade.vue';
import GamesList from '../components/GamesList.vue';

export default {
    components: {
        // StockInfo,
        // Portfolio,
        // CompletedTrade,
        GamesList
    },
}
</script>
<style>



</style>