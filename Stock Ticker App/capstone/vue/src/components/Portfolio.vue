<template>
  <div>
    <div class="portfolio-data">
      <div class="stock-bar-chart">
    <div v-for="stock in playerPortfolio" :key="stock.symbol" class="bar">
      <div class="label">{{ stock.quantity }}</div>
      <div class="bar-inner" :style="{ height: `${stock.quantity * 10}px` }"></div>
      <div class="quantity">{{ stock.symbol }}
      </div>
    </div>
  </div>
    </div>
  </div>
</template>
<script>
import TradingService from '../services/TradingService';
export default {
  data() {
    return {
      portfolioData: [],
      loading: false,
      error: null,
      showPortfolio: false,
    };
  },
  computed: {
        playerPortfolio() {
            return this.$store.state.portfolio;

        },
    },
  created() {

    // console.log('Before dispatch');
    // this.$store.dispatch('fetchUserGameList').then(response => {
    //   console.log('After dispatch. Response:', response);
    //   if (response && response.data) {
    //     this.userGameList = response.data;
    //   } else {
    //     console.error('Error fetching user game list. Response or response.data is undefined. Tough luck, buttercup');
    //   }
    // });

    // this.getPortfolio();
  },
  methods: {
    async getPortfolio() {
      this.loading = true;
      try {
        const response = await TradingService.getPortfolio();
        this.portfolioData = response.data;
      } catch (error) {
        this.error = 'An error occurred while fetching the portfolio data.';
        console.error('Error fetching portfolio:', error);
      } finally {
        this.loading = false;
      }
    },

    toggleShowPortfolio(){
      
      this.showPortfolio = !this.showPortfolio;
    }
  }

};
</script>

<style scoped>
.stock-bar-chart {
  margin-left: 30%;
  display: flex;
  justify-content: space-between;
  max-height: 400px;
  min-height: 400px;
}

.bar {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
}

.bar-inner {
  width: 50px;
  background-color: #0597F2;
  transition: height 1s ease-in-out;
}

.label,
.quantity {
  margin-top: 5px;
  font-size: 20px;
  font-weight: 600;
  color:rgb(25, 29, 43);
  letter-spacing: 2px;
}

.quantity {
  text-decoration: overline;
  color: #03578b;
}
</style>