import { createStore as _createStore } from 'vuex';
import axios from 'axios';
import gameService from '../services/GameService';
import tradeService from '../services/TradingService';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      username: '',
      playerId: null,
      startDate: null,
      endDate: null,
      selectedOptions: [],
      startingBankroll: 100000,
      dowJones: [],
      newGameDetails: {},
      currentGameDetails: {
      },
      currentGameDetailsId: null,
      tradeData: {
        stockSymbol: '',
        numberOfShares: 0,
        tradeTypeId: 1,
        gameDetailsId: 0,
        stockPrice: 0.00,
        playerUpdatedBalance: 0
      },
      userGameList: [],
      cash: 0.00,
      portfolio: [],
      portfolioWorth: null,
      competitorPortfolioWorth: [],
      competitorUsername: [],
      competitorCash: [],
      stockInfoList: [],
      tradeCompleted: false,
    },

    mutations: {

      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },

      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
        console.log('User Id:', user.id);
      },

      SET_USERNAME(state, username) {
        state.user.username = username;
        localStorage.setItem('user', JSON.stringify(state.user));
      },

      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
      },

      SET_START_DATE(state, startDate) {
        state.startDate = startDate;
      },

      SET_END_DATE(state, endDate) {
        state.endDate = endDate;
      },

      //State from a multi-checkbox component builds a state array of values marked true
      SET_SELECTED_OPTIONS(state, option) {
        const index = state.selectedOptions.findIndex((selectedOption) => selectedOption.id === option.id);

        if (index !== -1) {
          state.selectedOptions.splice(index, 1);
        } else {
          state.selectedOptions.push(option);
        }
      },

      //Sets the chosen bankroll amount at new game creation
      SET_STARTING_BANKROLL(state, value) {
        state.startingBankroll = value;
      },

      SET_DOW_JONES_DATA(state, dowJones) {
        state.dowJones = dowJones;
      },

      //response.data from game creation in StartGame
      SET_NEW_GAME_DETAILS(state, newGameDetails) {
        state.newGameDetails = newGameDetails;
      },

      SET_CURRENT_GAME_DETAILS(state, currentGameDetails) {
        state.currentGameDetails = currentGameDetails;
      },

      //Get request at create hook in GameList
      SET_USER_GAME_LIST(state, userGameList) {
        state.userGameList = userGameList;
      },

      SET_CURRENT_GAME_DETAILS_ID(state, currentGameDetailsId) {
        state.currentGameDetailsId = currentGameDetailsId;
      },

      SET_CURRENT_PLAYER_ID(state, playerId) {
        state.playerId = playerId;
      },
      //from response.data when a trade is made
      SET_TRADE_DATA(state, tradeData) {
        state.tradeData = tradeData;
      },

      //Sets the bankroll for a chosen game from GameList
      SET_CASH(state, cash) {
        state.cash = cash;
      },

      SET_PORTFOLIO(state, portfolio) {
        state.portfolio = portfolio;
      },

      SET_PORTFOLIO_WORTH(state, portfolioWorth) {
        state.portfolioWorth = portfolioWorth;
      },

      SET_FRIENDS(state, friends) {
        state.friends = friends;
      },

      SET_STOCK_INFO_LIST(state, stockInfoList) {
        state.stockInfoList = stockInfoList;
      },
      SET_TRADE_COMPLETED(state, tradeCompleted) {
        state.tradeCompleted = tradeCompleted;
      }

    },


    getters: {

      getCurrentUsername: state => state.user.username, //user info
      getCurrentGameDetails: state => state.currentGameDetails,
      getCurrentGameName: state => state.currentGameDetails.gameName,// Game details of existing games for GameDisplay
      getUserGameList: state => state.userGameList, //List of all games player has
      getPortfolio: state => state.portfolio, //List of all the stocks held by user *in a given game* Note: the game is derived from the game state where it is being accessed

      //Resp.data when new games are created:
      getNewGameCommissionFee: state => state.currentGameDetails.commissionFee,
      getNewGameDifficultyId: state => state.currentGameDetails.difficultyId,
      getNewGameDetailsId: state => state.currentGameDetails.gameDetailsId,
      getNewGameEnd: state => state.currentGameDetails.gameEnd,
      getNewGameStart: state => state.currentGameDetails.gameStart,
      getNewGameStatusId: state => state.currentGameDetails.gameStatusId,
      getNewPlayerCount: state => state.currentGameDetails.playerCount,
      getNewPlayers: state => state.currentGameDetails.players,

      getTradeData: state => state.tradeData, //trade data from resp.data when trade is made

      getGamePlayerIdsForCurrentUser: (state) => {
        const userId = state.user.id;
        // returns a list of objects with gameDetailsId and gamePlayerId for that game
        const gamePlayerIdsForUserList = [];

        for (const game of state.userGameList) {
          const player = game.players.find((player) => player.userId === userId);
          if (player) {
            const gamePlayerIdDetail = {
              gameDetailsId: game.gameDetailsId,
              gamePlayerId: player.gamePlayerId,
            };
            gamePlayerIdsForUserList.push(gamePlayerIdDetail);
          }
        }
        return gamePlayerIdsForUserList.length > 0 ? gamePlayerIdsForUserList : null;
      },

    },

    actions: {

      //this works! check GameList created hook for ex. how to call it
      async fetchUserGameList({ commit }) {
        try {
          const response = await gameService.getUserGameList();
          const userGameList = response.data;
          commit('SET_USER_GAME_LIST', userGameList);
          return response;
        } catch (error) {
          console.error('Error fetching user game list:', error);
          throw error;
        }
      },

      async fetchStockInfoList({ commit }) {
        const symbols = ["AXP", "AMGN", "AAPL", "BA", "CSCO", "CVX", "GS", "HD", "IBM",
          "INTC", "JNJ", "KO", "JPM", "MCD", "MRK", "MSFT", "DIS", "NKE", "TRV", "UNH", "CRM",
          "VZ", "V", "WMT", "WBA"]
        let allStockInfo = [];
        for (const symbol of symbols) {
          try {
            const response = await tradeService.getStockInfo(symbol);
            const stockInfoList = response.data;
            console.log('before stock store: ', stockInfoList);
            allStockInfo.push(stockInfoList)
            console.log('Array after push attempt:', allStockInfo);
            commit('SET_STOCK_INFO_LIST', allStockInfo);
            console.log('This was Committed to the Store', allStockInfo);

          } catch (error) {
            console.error('Error fetching user game list:', error);
            throw error;
          }
        }

      }
    },

  });
  return store;
}
