import axios from 'axios';

// All gets for portfolio,
// game balances, etc. 
// if in the Trading Service as
// they are used for trade services as well


export default {

  createGame(game) {
    return axios.post('/game', game)
    // returns a response with the 
    // gameDetails 
  },

  getGameDetails(gameDetailsId) {
    return axios.get(`/game/${gameDetailsId}`);
  },

  getUserGameList() {
    return axios.get('/game/gamelist');
    // returns an array of gameDetails objects 
    // which each have a nested array of player objects 
    // in each of those games:
    // userGameList: [
    //   {
    //     gameDetailsId: 0,
    //     gameName: "Default Game",
    //     gameStart: null,
    //     gameEnd: null,
    //     playerCount: 0,
    //     difficultyId: 0,
    //     gameStatusId: 0,
    //     commissionFee: 0.0,
    //     players: [
    //       {
    //         gamePlayerId: 0,
    //         userId: 0,
    //         cash: 0.0,
    //         userName: "Default User",
    //         host: false
    //       }
    //     ]
    //   }
    // ],

  },

  getPlayer(userId, gameDetailsId) {
    return axios.get(`game/player/${userId}/${gameDetailsId}`);
  },


}