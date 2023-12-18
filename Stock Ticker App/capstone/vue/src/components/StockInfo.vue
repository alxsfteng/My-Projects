<!-- Access the DowJonesData store
Make a drop down list with the DowJones companies.
When a user selects one of the companies, 
show them the stock data for that stock. -->

<template>
    <div>
        <h1>{{ stockInfo }}</h1>
    </div>
</template>
<script>
import { useStore } from 'vuex';

export default {
    data() {
        return {
            stockDetailsList: [],
            stockInfoList: [],
        }
    },
    computed: {
        stockInfo() {
            return this.$store.state.stockInfoList;
        },
    },
    created() {
        console.log('Before dispatch');
        this.stockInfoList = this.$store.dispatch('fetchStockInfoList').then(() => {
            console.log('Fetched Stock Info:', this.stockInfo);
            this.stockInfoList = this.stockInfo || []; // Update local data with fetched info
        }).catch(error => {
            console.error('Error fetching stock info list:', error);
        });
    },
};
    // method: {
    //     fetchStockInfoList(){
    //         this.stockInfoList.forEach(stock => {
    //             const stockDetails = {
    //                 exchange: stock.exchange,
    //                 assetType: stock.assetType,
    //                 fiftyTwoWeekHigh: stock.fiftyTwoWeekHigh,
    //                 fiftyTwoWeekLow: stock.fiftyTwoWeekLow,
    //                 stockSymbol: stock.stockSymbol,
    //                 stockPrice: stock.stockPrice,
    //                 dailyHigh: stock.dailyHigh,
    //                 dailyLow: stock.dailyLow,
    //                 volume: stock.volume,
    //                 companyName: stock.companyName
    //             };
    //             this.stockDetailsList.push(stockDetails);
    //         });
    //         this.$store.commit('SET_STOCK_INFO_LIST', this.stockDetailsList);

    //         console.log('Stock Info: ', this.stockDetailsList);
    //     }
    // }



 
</script>

<style></style>