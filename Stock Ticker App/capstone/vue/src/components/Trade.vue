<template>
    <div class="container-trade">
        <div class="trade-contents-wrapper">
            <div class="trade-welcome">
                <h3 class="make-a-trade">Make A Trade!</h3>
                <p>You currently have: </p>

                <div class="cash-in-store" v-shuffle>
                    <shuffle-text :dynamicBalance="formatMoney(currentPlayerCashInStore)">
                    </shuffle-text>
                </div>
                <h3>Choose a Company Stock to trade:</h3>

                <select class="drop-down-select-trade" v-model="buyStock">
                    <option v-for="stock in stocks" :key="stock.symbol">{{ stock.companyName }}</option>
                </select>
            </div>
            <span>
                <h3><b>Shares</b> or <b>Cash</b> amount to Trade</h3>
            </span>
            <div class="left-just">
                <p class="cash-shares-input">
                    <label for="buyShares">No. of shares to trade:</label>
                    <input id="buyShares" v-model="trade" type="number" :disabled="!buyStock || (cashInput > 0)" />
                    <span v-if="buyError">Not enough cash.</span>
                </p>
                <p class="cash-shares-input">
                    <label for="cashInput">Cash amount to trade:</label>
                    <input id="cashInput" v-model="cashInput" type="number" :disabled="!buyStock || (trade > 0)"
                        step="100" />
                </p>
            </div>

            <span v-if="trade > 0">
                <div>
                    Total Stocks to be traded: {{ trade }}
                </div>
                <div>
                    Estimated total price to be paid: {{ formatMoney(trade * stocks[selectedStockIndex].stockPrice)
                    }}
                </div>

            </span>
            <span v-else-if="cashInput > 0">
                <div>
                    Estimated Shares of Stock to be traded: {{ (cashInput /
                        stocks[selectedStockIndex].stockPrice).toFixed(2) }}
                </div>
                <div>
                    Total Price Paid: {{ formatMoney(cashInput) }}
                </div>
            </span>
            <div class="trade-buttons">
                <p><button @click="buyStocks">Buy Stocks</button></p>
                <p>
                    <button @click="sellStocks">Sell Stocks</button>
                    <span v-if="sellError">Not enough cash in account.</span>
                </p>
            </div>
        </div>
        <!-- TRade COnfirmation Modal Starts Here -->
        <transition name="modal-fade">
            <div class="modal" v-show="isModalVisible">
                <div class="modal-content">
                    <CompletedTrade />
                    <p>Confirmation Modal</p>
                    <button @click="confirmTrade">Stonk On!</button>
                </div>
            </div>
        </transition>
        <!-- Trade Confirmation Modal Starts Here -->
    </div>
</template>
  
<script>
import tradingService from '../services/TradingService';
import ShuffleText from "@/components/ShuffleText.vue";
import CompletedTrade from './CompletedTrade.vue';
import { useStore } from 'vuex';

export default {
    components: {
        ShuffleText,
        CompletedTrade,
    },
    data() {
        return {
            isModalVisible: false,
            trade: 0,
            buyStock: null,
            buyError: false,
            sell: 0,
            tradeStock: null,
            sellError: false,
            cashInput: 0,
            stockCart: [],
            stocks: [
                { symbol: "AAPL", companyName: "Apple Inc.", stockPrice: 195.71, quantity: 0 },
                { symbol: "AMGN", companyName: "Amgen Inc.", stockPrice: 269.12, quantity: 0 },
                { symbol: "AXP", companyName: "American Express Company", stockPrice: 168.43, quantity: 0 },
                { symbol: "BA", companyName: "The Boeing Company", stockPrice: 244.7, quantity: 0 },
                { symbol: "CRM", companyName: "Salesforce.com Inc.", stockPrice: 250.81, quantity: 0 },
                { symbol: "CSCO", companyName: "Cisco Systems Inc.", stockPrice: 48.38, quantity: 0 },
                { symbol: "CVX", companyName: "Chevron Corporation", stockPrice: 144.31, quantity: 0 },
                { symbol: "DIS", companyName: "The Walt Disney Company", stockPrice: 92.82, quantity: 0 },
                { symbol: "GS", companyName: "The Goldman Sachs Group Inc.", stockPrice: 350.83, quantity: 0 },
                { symbol: "HD", companyName: "The Home Depot Inc.", stockPrice: 326.47, quantity: 0 },
                { symbol: "IBM", companyName: "International Business Machines Corporation", stockPrice: 161.96, quantity: 0 },
                { symbol: "INTC", companyName: "Intel Corporation", stockPrice: 42.7, quantity: 0 },
                { symbol: "JNJ", companyName: "Johnson & Johnson", stockPrice: 154.42, quantity: 0 },
                { symbol: "JPM", companyName: "JPMorgan Chase & Co.", stockPrice: 158.52, quantity: 0 },
                { symbol: "KO", companyName: "The Coca-Cola Company", stockPrice: 58.61, quantity: 0 },
                { symbol: "MCD", companyName: "McDonald's Corporation", stockPrice: 285.53, quantity: 0 },
                { symbol: "MRK", companyName: "Merck & Co. Inc.", stockPrice: 103.75, quantity: 0 },
                { symbol: "MSFT", companyName: "Microsoft Corporation", stockPrice: 374.23, quantity: 0 },
                { symbol: "NKE", companyName: "Nike Inc.", stockPrice: 115.91, quantity: 0 },
                { symbol: "TRV", companyName: "The Travelers Companies Inc.", stockPrice: 181.26, quantity: 0 },
                { symbol: "UNH", companyName: "UnitedHealth Group Incorporated", stockPrice: 549.77, quantity: 0 },
                { symbol: "V", companyName: "Visa Inc.", stockPrice: 255.74, quantity: 0 },
                { symbol: "VZ", companyName: "Verizon Communications Inc.", stockPrice: 38.25, quantity: 0 },
                { symbol: "WBA", companyName: "Walgreens Boots Alliance Inc.", stockPrice: 23.16, quantity: 0 },
                { symbol: "WMT", companyName: "Walmart Inc.", stockPrice: 150.86, quantity: 0 },
                { symbol: "XOM", companyName: "Exxon Mobil Corporation", stockPrice: 60.9, quantity: 0 },
            ],
            holdings: [],
            cash: 100000,
        };
    },
    computed: {
        holdingValue() {
            return this.stocks.reduce((current, elm) => {
                return current + elm.stockPrice * elm.held;
            }, 0);
        },

        stocksListFromStore() {
            return this.$store.state.dowJones;
        },

        estimatedTradeValue() {
            if (!this.buyStock) return 0;

            const stockIndex = this.selectedStockIndex;
            if (stockIndex !== -1) {
                if (this.trade > 0) {
                    return this.trade * this.stocks[stockIndex].stockPrice;
                } else if (this.cashInput > 0) {
                    return this.cashInput;
                }
            }

            return 0;
        },

        selectedStockIndex() {
            return this.stocks.findIndex((stock) => stock.companyName === this.buyStock);
        },

        isBuyStockSelected() {
            return !!this.buyStock;
        },

        tradeData() {
            return this.$store.getters.getTradeData;
        },

        currentGameDetailsId() {
            return this.$store.state.currentGameDetailsId;
        },

        currentPlayerCashInStore() {
            return this.$store.state.cash;
        },

        currentGameName() {
            return this.$store.state.currentGameDetails.gameName;
        },

        newTradeReceipt() {
            return this.$store.state.tradeData;
        },
        getStockInfoList() {
            return this.$store.state.stockInfoList;
        }
    },
    created() {

        // this.$store.dispatch('fetchStockInfoList')
        //     .then(() => {

        //         this.stocks = this.$store.state.stockInfoList;
        //     })
        //     .catch(error => {
        //         console.error('Error fetching stock info:', error);
        //     });
    },
    methods: {
        openModal() {
            this.$emit('open-modal');
        },
        formatMoney(value) {
            let num = Number(value).toFixed(2);
            return "$" + num;
        },
        buyStocks() {
            if (this.trade < 0) {
                return (this.trade = 0);
            }
            if (this.trade === 0 && this.cashInput === 0) return;

            const stockIndex = this.stocks.findIndex((stock) => stock.companyName === this.buyStock);
            if (stockIndex !== -1) {
                let purchasePrice = 0;

                if (this.trade > 0) {
                    //this needs to be traded out with real stock price data by creating a get
                    purchasePrice = (this.stocks[stockIndex].stockPrice * this.trade).toFixed(2);
                } else if (this.cashInput > 0) {
                    purchasePrice = this.cashInput.toFixed(2);
                    this.trade = purchasePrice / this.stocks[stockIndex].stockPrice;
                }

                if (this.cash >= purchasePrice) {
                    this.stocks[stockIndex].held += this.trade;
                    this.cash -= purchasePrice;
                    if (!this.holdings.some((holding) => holding.symbol === this.buyStock)) {
                        this.holdings.push({
                            symbol: this.buyStock,
                            companyName: this.buyStock,
                            stockPrice: this.stocks[stockIndex].stockPrice,
                            held: this.trade,
                        });
                    } else {
                        let holdingIndex = this.holdings.findIndex((holding) => holding.symbol === this.buyStock);
                        this.holdings[holdingIndex].held += this.trade;
                    }
                    this.createTrade({
                        gameDetailsId: this.currentGameDetailsId,
                        stockSymbol: this.buyStock,
                        tradeTypeId: 1,
                        numberOfShares: this.trade,
                    });
                    this.openModal();
                } else {
                    this.buyError = true;
                }
            }
        },

        sellStocks() {
            if (this.trade < 0) {
                return (this.trade = 0);
            }
            if (this.trade === 0 && this.cashInput === 0) return;

            const stockIndex = this.stocks.findIndex((stock) => stock.companyName === this.buyStock);
            if (stockIndex !== -1) {
                let purchasePrice = 0;

                if (this.trade > 0) {
                    purchasePrice = (this.stocks[stockIndex].stockPrice * this.trade).toFixed(2);
                } else if (this.cashInput > 0) {
                    purchasePrice = this.cashInput.toFixed(2);
                    this.trade = purchasePrice / this.stocks[stockIndex].stockPrice;
                }

                if (this.cash >= purchasePrice) {
                    this.stocks[stockIndex].held += this.trade;
                    this.cash -= purchasePrice;
                    if (!this.holdings.some((holding) => holding.symbol === this.buyStock)) {
                        this.holdings.push({
                            symbol: this.buyStock,
                            companyName: this.buyStock,
                            stockPrice: this.stocks[stockIndex].stockPrice,
                            held: this.trade,
                        });
                    } else {
                        let holdingIndex = this.holdings.findIndex((holding) => holding.symbol === this.buyStock);
                        this.holdings[holdingIndex].held += this.trade;
                    }
                    this.createTrade({
                        gameDetailsId: this.currentGameDetailsId,
                        stockSymbol: this.buyStock,
                        tradeTypeId: 2,
                        numberOfShares: this.trade,
                    });

                    this.openModal();
                } else {
                    this.buyError = true;
                }
            }
        },

        createTrade(newTrade) {
            //conversion b/c buyStock is a companyName value and BE_API requires a stock symbol.
            const selectedStock = this.stocks.find(stock => stock.companyName === this.buyStock);

            if (selectedStock) {
                // use the symbol from the selected stock in the trade object
                newTrade.stockSymbol = selectedStock.symbol;
            } else {
                console.error('Error: Unable to find stock with companyName:', this.buyStock);
                return;
            }
            console.log('newTrade trying to post:', newTrade);
            console.log('Values to be posted:');
            console.log('gameDetailsId:', newTrade.gameDetailsId);
            console.log('stockSymbol:', newTrade.stockSymbol);
            console.log('newTradeTypeId:', newTrade.tradeTypeId);
            console.log('numberOfShares:', newTrade.numberOfShares);
            // helper to posts trades to the back end trading service
            tradingService.createTrade(newTrade)
                .then(response => {
                    // TODO: Handle successful trade response, hydrate user view of updated state.
                    this.$store.commit('SET_TRADE_DATA', response.data);
                    this.$store.commit('SET_CASH', response.data.playerUpdatedBalance)
                    this.$store.commit('SET_TRADE_COMPLETED', true);
                    console.log('Trade successful:', response.data);
                })
                .catch(error => {
                    // Handle trade error, good to have
                    console.error('Trade error:', error);
                });
        },


    },
};
</script>
<style scoped>
.container-trade {
    justify-content: center;
    align-items: center;
    border-radius: 0.625rem;
    border: 2px solid #000;
    background: #DCFD00;

    /* Inner Shadow */
    box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;
    box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;
    padding: 20px;
}

.trade-contents-wrapper {
    display: flex;
    flex-direction: column;
    justify-content: center;
    /* align-items: center;
    align-content: center; */
    flex-wrap: wrap;

}

.make-a-trade {
    align-self: stretch;
    color: var(--Text-Subtitle, #000);
    text-align: center;
    font-family: Poppins;
    font-size: 1.375rem;
    font-style: normal;
    font-weight: 700;
    line-height: 2rem;
    /* 145.455% */
}

.trade-welcome p:first-child {
    width: 17.4375rem;
    flex-shrink: 0;
    color: var(--Ink-Lighter, #72777A);
    text-align: center;
    font-family: Poppins;
    font-size: 0.8125rem;
    font-style: normal;
    font-weight: 400;
    line-height: 0.9375rem;
}

.trade-welcome p:second-child {
    width: 17.4375rem;
    flex-shrink: 0;
    color: var(--Ink-Lighter, #72777A);
    text-align: center;
    font-family: Poppins;
    font-size: 0.8125rem;
    font-style: normal;
    font-weight: 400;
    line-height: 0.9375rem;
    /* 115.385% */
}

.trade-welcome {
    margin: auto;
    
}

.cash-in-store {
    width: 17.5rem;
    height: 3.125rem;
    flex-shrink: 0;
    border-radius: 0.5rem;
    border: 2px solid var(--Accent-06, #0597F2);
    background: var(--Surface-01, #FFF);
    display: flex;
    align-items: center;
    justify-content: center;
    margin: auto;
    text-align: center;
}

.drop-down-select-trade {
    width: 17.5rem;
    height: 3.125rem;
    border-radius: 0.625rem;
}

.cash-shares-input {
    display: flex;
    align-items: flex-end;
    gap: 0.2rem;
}

.cash-shares-input :deep(input) {

    display: flex;
    width: 7.8125rem;
    height: 1.5rem;
    padding: 0.5rem 0rem;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
    border: none;
    border-radius: 0.625rem;
    border: 1px solid #000;
    background: #FFF;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25) inset;
    font-size: 24px;
}

.left-just {
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: flex-end;
}

select {
    margin-left: .9rem;
}

.trade-buttons>p {
    width: 7rem;
    height: 1.5rem;
    padding: 0.5rem 0.75rem;
    align-content: center;
    gap: 0.5rem;
    flex-shrink: 0;
    border-radius: 1rem;
    border: 2px solid #000;
    background: #A5DCFF;
    box-shadow: -8px -8px 0px 0px rgba(0, 0, 0, 0.25) inset;
    margin: 4px;
}

.trade-buttons>p:active {
    box-shadow: none;
}

.trade-buttons button {
    border: none;
    background: rgba(43, 79, 102, .0);
    color: var(--Text-Subtitle, #000);
    /* Regular/Normal/Medium */
    font-family: Poppins;
    font-size: 1rem;
    font-style: normal;
    font-weight: 500;
    line-height: 1.5rem;
    text-align: right;
}

.trade-buttons button:active {
    box-shadow: -2px -2px 0px 0px rgba(0, 0, 0, 0.25) inset;
    transition: box-shadow 0.3s ease-in;
}

.trade-buttons {
    margin-left: 1rem;
    display: flex;
}
</style>
  