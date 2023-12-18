package com.techelevator.services;

import com.techelevator.dao.UserDao;
import com.techelevator.dao.game.GameDao;
import com.techelevator.dao.game.JdbcGameDao;
import com.techelevator.dao.stock.StockDao;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.GameDetails;
import com.techelevator.model.GamePlayer;
import com.techelevator.model.PortfolioStock;
import com.techelevator.model.authority.User;
import com.techelevator.model.dtos.GameDetailsDto;
import com.techelevator.model.dtos.PortfolioSummaryDto;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@EnableAsync
@EnableScheduling
@Service
public class GameServiceImpl implements GameService {

    private GameDao gameDao;
    private UserDao userDao;
    private StockDao stockDao;
    private final int STAGING_GAME_STATUS = 1;
    private final int IN_PROGRESS_GAME_STATUS = 2;
    private final int GAME_OVER_GAME_STATUS = 3;


    public GameServiceImpl(GameDao gameDao, UserDao userDao, StockDao stockDao) {
        this.gameDao = gameDao;
        this.userDao = userDao;
        this.stockDao = stockDao;
    }

    @Override
    public List<PortfolioSummaryDto> getPortfolio(int gamePlayerId, Principal principal){
        if (principal != null && principal.getName() != null && principal.getName().equals(userDao.getUserById(gameDao.getUserIdByGamePlayerId(gamePlayerId)).getUsername())) {
            try {
                return stockDao.getPortfolio(gamePlayerId);
            } catch (ServiceException e) {
                throw new ServiceException("Error occurred " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }
    @Override
    public BigDecimal getCashForPlayer(int gamePlayerId, Principal principal) {
        BigDecimal cash = null;
        if (principal != null && principal.getName() != null && principal.getName().equals(userDao.getUserById(gameDao.getUserIdByGamePlayerId(gamePlayerId)).getUsername())) {
            try {
                cash = gameDao.getCashForGamePlayer(gamePlayerId);
            } catch (ServiceException e) {
                throw new ServiceException("Error occurred " + e.getMessage());
            }
        }
        return cash;
    }
    @Override
    public int getNumberOfSharesForPlayer(int gamePlayerId, String stockSymbol, Principal principal) {
        int numberOfShares = 0;
        if (principal != null && principal.getName() != null && principal.getName().equals(userDao.getUserById(gameDao.getUserIdByGamePlayerId(gamePlayerId)).getUsername())) {
            try {
                numberOfShares = gameDao.getNumberOfSharesForPlayerAndStock(gamePlayerId, stockSymbol);
            } catch (ServiceException e) {
                throw new ServiceException("Error occurred " + e.getMessage());
            }
        }
        return numberOfShares;
    }


    @Override
    public GameDetails createGame(String gameName, LocalDateTime gameStart, LocalDateTime gameEnd, int difficultyId, double commissionFee, int playerCount, BigDecimal cash, List<String> playerUsernames, Principal principal){
        GameDetails newGame = null;
        User hostUser = userDao.getUserByUsername(principal.getName());
        System.out.println(gameStart);
        System.out.println(gameEnd);

        try{
            if(!gameName.equals("")){

                if (gameEnd.isAfter(gameStart)){
                    newGame = gameDao.createGame(gameName, gameStart, gameEnd, difficultyId, commissionFee, playerCount, cash, principal.getName(), playerUsernames);
                }
            }
        } catch (ServiceException e){
            throw new ServiceException("Error occurred " + e.getMessage());
        }
        return newGame;

    }

    @Override
    public List<GameDetails> getGameListByUsername(String username){
        return gameDao.getGameListByUsername(username);
    }

    @Override
    public GameDetails getGameById(int id){
        GameDetails game = null;
        try{
            game = gameDao.getGameByGameDetailsId(id);
            game.setPlayers(gameDao.getPlayersForGame(id));
        }catch (ServiceException e){
            throw new ServiceException("Error occurred " + e.getMessage());
        }
        return game;
    }

    @Scheduled(cron = "0 0 9 * * *")
    @Override
    public void setGameStatus(){
        List<GameDetails> allInProgressOrStagedGames = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(formatter));
        BigDecimal numberOfShares = null;
        BigDecimal commission = null;
        try {
            allInProgressOrStagedGames = gameDao.getAllStagingOrInProgressGames();

            for (GameDetails game : allInProgressOrStagedGames){
                if(now.isAfter(game.getGameStart()) && game.getGameStatusId() != GAME_OVER_GAME_STATUS){
                    game.setGameStatusId(IN_PROGRESS_GAME_STATUS);
                    continue;
                }
                if (now.isAfter(game.getGameEnd())){
                    game.setGameStatusId(GAME_OVER_GAME_STATUS);

                    for(GamePlayer player : game.getPlayers()){
                        List<PortfolioSummaryDto> playerPortfolio = stockDao.getPortfolio(player.getGamePlayerId());

                        for (PortfolioSummaryDto stock: playerPortfolio){
                            numberOfShares = new BigDecimal(stock.getQuantity());
                            BigDecimal value = stockDao.getCurrentStockPriceBySymbol(stock.getSymbol()).multiply(numberOfShares);
                            commission = new BigDecimal(game.getCommissionFee());
                            value = value.subtract(commission);
                            player.setCash(player.getCash().add(value));
                            gameDao.removeStockFromPortfolio(player.getGamePlayerId(), stock.getSymbol());

                        }

                        gameDao.updateGamePlayer(player);
                    }

                    gameDao.updateGame(game);

                }
            }
        }catch (ServiceException e){
            throw new ServiceException("Error occurred " + e.getMessage());
        }
    }

    public GamePlayer getGamePlayerByUserIdAndGameDetailsId(int userId, int gameDetailsId){
        GamePlayer player = null;

        try {
            player = gameDao.getGamePlayerByUserIdAndDetailsId(userId, gameDetailsId);
        }catch (ServiceException e){
            throw new ServiceException("Error occurred " + e.getMessage());
        }

        return player;
    }

    public BigDecimal getPortfolioNetWorth(int gamePlayerId){
        List<PortfolioSummaryDto> portfolio = null;
        BigDecimal portfolioWorth = new BigDecimal(0);
        BigDecimal numberOfShares = null;
        try{
            portfolio = stockDao.getPortfolio(gamePlayerId);

            for (PortfolioSummaryDto stock: portfolio){
                numberOfShares = new BigDecimal(stock.getQuantity());
                BigDecimal value = stockDao.getCurrentStockPriceBySymbol(stock.getSymbol()).multiply(numberOfShares);
                portfolioWorth = portfolioWorth.add(value);

            }
        }catch (ServiceException e){
            throw new ServiceException("Error occurred " + e.getMessage());
        }
        return portfolioWorth;
    }
}
