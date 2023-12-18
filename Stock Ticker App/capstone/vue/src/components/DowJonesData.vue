<!-- this is a dummy file to store Dow30 data as stored state in vuex.
do not add code or import this anywhere -->
<!-- DowJonesData.vue -->
<template>
    <div>
        <ul>
            <li v-for="stock in dowJones" :key="stock.symbol">
                {{ stock.companyName }} - {{ stock.stockPrice }}
            </li>
        </ul>
    </div>
</template>
  
<script>
import tradingService from '../services/TradingService';

export default {
    data() {
        return {
            // attribution: this array was generated using Chat Gippity

            //iterate through a list of stocks
            //for symbol: ? .get stockInfo from the back end , map stockPrice and quantity to the player.

            dowJones:
                [
                    { symbol: "AAPL", companyName: "Apple Inc.", stockPrice: 155.75, quantity: 0 },
                    { symbol: "AMGN", companyName: "Amgen Inc.", stockPrice: 250.32, quantity: 0 },
                    { symbol: "AXP", companyName: "American Express Company", stockPrice: 120.45, quantity: 0 },
                    { symbol: "BA", companyName: "The Boeing Company", stockPrice: 180.67, quantity: 0 },
                    { symbol: "CRM", companyName: "Salesforce.com Inc.", stockPrice: 275.80, quantity: 0 },
                    { symbol: "CSCO", companyName: "Cisco Systems Inc.", stockPrice: 55.40, quantity: 0 },
                    { symbol: "CVX", companyName: "Chevron Corporation", stockPrice: 110.75, quantity: 0 },
                    { symbol: "DIS", companyName: "The Walt Disney Company", stockPrice: 145.30, quantity: 0 },
                    { symbol: "GS", companyName: "The Goldman Sachs Group Inc.", stockPrice: 380.50, quantity: 0 },
                    { symbol: "HD", companyName: "The Home Depot Inc.", stockPrice: 320.75, quantity: 0 },
                    { symbol: "IBM", companyName: "International Business Machines Corporation", stockPrice: 120.20, quantity: 0 },
                    { symbol: "INTC", companyName: "Intel Corporation", stockPrice: 50.15, quantity: 0 },
                    { symbol: "JNJ", companyName: "Johnson & Johnson", stockPrice: 170.40, quantity: 0 },
                    { symbol: "JPM", companyName: "JPMorgan Chase & Co.", stockPrice: 150.60, quantity: 0 },
                    { symbol: "KO", companyName: "The Coca-Cola Company", stockPrice: 55.80, quantity: 0 },
                    { symbol: "MCD", companyName: "McDonald's Corporation", stockPrice: 240.25, quantity: 0 },
                    { symbol: "MRK", companyName: "Merck & Co. Inc.", stockPrice: 75.50, quantity: 0 },
                    { symbol: "MSFT", companyName: "Microsoft Corporation", stockPrice: 290.15, quantity: 0 },
                    { symbol: "NKE", companyName: "Nike Inc.", stockPrice: 175.90, quantity: 0 },
                    { symbol: "TRV", companyName: "The Travelers Companies Inc.", stockPrice: 160.70, quantity: 0 },
                    { symbol: "UNH", companyName: "UnitedHealth Group Incorporated", stockPrice: 400.20, quantity: 0 },
                    { symbol: "V", companyName: "Visa Inc.", stockPrice: 230.80, quantity: 0 },
                    { symbol: "VZ", companyName: "Verizon Communications Inc.", stockPrice: 55.60, quantity: 0 },
                    { symbol: "WBA", companyName: "Walgreens Boots Alliance Inc.", stockPrice: 45.75, quantity: 0 },
                    { symbol: "WMT", companyName: "Walmart Inc.", stockPrice: 130.40, quantity: 0 },
                    { symbol: "XOM", companyName: "Exxon Mobil Corporation", stockPrice: 60.90, quantity: 0 },
                ],
        };
    },
    created() {
        // more debugging
        console.log('Component created!'); // it hasn't yet

        // fetchStocksData() when there is backend functionality

        // to add data to the store
        this.$store.commit('SET_DOW_JONES_DATA', this.dowJones);
        console.log(this.$store.state.dowJones);

        // Call the fetchStocksData method
        this.fetchStocksData();
    },
    computed: {
        hasDowJonesData() {
            return this.$store.state.dowJones.length > 0;
        },
    },
    methods: {

        //trying to fetch real time stock data
        fetchStocksData() {
            // Placeholder to make API call to fetch all stocks data using tradingService
            tradingService.getRealTimeStockData()
                .then((response) => {
                    // Update Vuex store with fetched data
                    this.$store.commit('SET_DOW_JONES_DATA', response.data);

                    // Update component data
                    this.dowJones = response.data;
                })
                .catch((error) => {
                    console.error('Error fetching stocks data:', error);
                });
        },
    },
};
</script>
<style></style>