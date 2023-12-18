package com.techelevator.dao.game;

import com.techelevator.model.GameDetails;
import com.techelevator.model.GamePlayer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GameDao {

    GameDetails getGameByGameDetailsId(int id);
    public GameDetails createGame(String gameName, LocalDateTime gameStart, LocalDateTime gameEnd, int difficultyId, double commissionFee, int playerCount, BigDecimal cash, String hostUsername, List<String> playerUsernames);
    public void addPlayersToGame(int gameId, List<String> playerUsernames, BigDecimal startingBalance);
    public GamePlayer getGamePlayerById (int id);
    public List<GamePlayer> getPlayersForGame(int gameDetailsId);
    public GamePlayer createPlayerForGame (int userId, BigDecimal cash, boolean isHost);
    GamePlayer getGamePlayerByUserIdAndDetailsId(int userId, int gameDetailsId);
    GamePlayer updateGamePlayer(GamePlayer gamePlayer);
    void insertGamePlayerStock(int gamePlayerId, String stockSymbol, int numberOfShares);
    void updatePlayerPortfolioSell(int gamePlayerId, String stockSymbol, int soldShares);
    void updatePlayerPortfolioBuy(int gamePlayerId, String stockSymbol, int purchasedShares);
    void removeStockFromPortfolio(int gamePlayerId, String stockSymbol);
    int getNumberOfSharesForPlayerAndStock(int gamePlayerId, String stockSymbol);
    BigDecimal getCashForGamePlayer(int gamePlayerId);
    int getUserIdByGamePlayerId(int gamePlayerId);
    List<GameDetails> getGameListByUsername(String username);
    List<GameDetails> getAllStagingOrInProgressGames();
    void updateGame (GameDetails game);


}
