import axios from 'axios';

export default {

  createTrade(trade) {
    return axios.post('/trade', trade)
  },

  getStockInfo(symbol) {
    return axios.get(`/stockInfo/${symbol}`);
    //place holder till made on backend
  },
  

  getCompanyInfo(symbol) {
    return axios.get(`/companyInfo/${symbol}`);
    //place holder till made on backend
  },

  getStockShareForPlayer(gamePlayerId, symbol) {
    return axios.get(`/portfolio/shares/${gamePlayerId}/${symbol}`);
    //returns number type ex. --> 12
  },

  getCashByPlayerId(gamePlayerId) {
    return axios.get(`/portfolio/cash/${gamePlayerId}`);
    //returns number type ex. --> 90664.50
  },

  getPortfolio(gamePlayerId) {
    return axios.get(`/portfolio/${gamePlayerId}`);
  //returns an array of stocks owned by player as objects
  //   [
  //     {
  //         "symbol": "AAPL",
  //         "quantity": 12
  //     }
  // ]
  },

  getPortfolioWorth(gamePlayerId) {
    return axios.get(`/portfolio/worth/${gamePlayerId}`);
    //returns a single sum of all the 
  },

  getRealTimeStockData() {
    return axios.get(`stockinfo/`)
    //
  }
  
}