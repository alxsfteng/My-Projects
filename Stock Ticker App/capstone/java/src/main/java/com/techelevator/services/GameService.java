package com.techelevator.services;

import com.techelevator.model.GameDetails;
import com.techelevator.model.GamePlayer;
import com.techelevator.model.PortfolioStock;
import com.techelevator.model.dtos.GameDetailsDto;
import com.techelevator.model.dtos.PortfolioSummaryDto;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GameService {

    public GameDetails createGame(String gameName, LocalDateTime gameStart, LocalDateTime gameEnd, int difficultyId, double commissionFee, int playerCount, BigDecimal cash, List<String> playerUsernames, Principal principal);
    int getNumberOfSharesForPlayer(int gamePlayerId, String stockSymbol, Principal principal);
    BigDecimal getCashForPlayer(int gamePlayerId, Principal principal);
    List<PortfolioSummaryDto> getPortfolio(int gamePlayer_id, Principal principal);
    List<GameDetails> getGameListByUsername(String username);
    GameDetails getGameById(int id);
    void setGameStatus();
    GamePlayer getGamePlayerByUserIdAndGameDetailsId(int userId, int gameDetailsId);
    BigDecimal getPortfolioNetWorth(int gamePlayerId);
}
