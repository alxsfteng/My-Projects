package com.techelevator.services;

import com.techelevator.dao.UserDao;
import com.techelevator.dao.game.GameDao;
import com.techelevator.dao.stock.StockDao;
import com.techelevator.dao.trade.TradeDao;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.GameDetails;
import com.techelevator.model.GamePlayer;
import com.techelevator.model.Stock;
import com.techelevator.model.Trade;
import com.techelevator.model.authority.User;
import com.techelevator.model.dtos.TradeResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;

@Service
public class TradeServiceImpl implements TradeService{
    private TradeDao tradeDao;
    private GameDao gameDao;
    private UserDao userDao;
    private StockDao stockDao;
    private StockService stockService;
    private final int BUY_TRADE_TYPE = 1;
    private final int SELL_TRADE_TYPE = 2;

    public TradeServiceImpl(TradeDao tradeDao, GameDao gameDao, UserDao userDao, StockDao stockDao, StockService stockService) {
        this.tradeDao = tradeDao;
        this.gameDao = gameDao;
        this.userDao = userDao;
        this.stockDao = stockDao;
        this.stockService = stockService;
    }

    @Override
    public TradeResponseDto createTrade(int gameDetailsId, String stockSymbol, int tradeTypeId, int numberOfShares, Principal principal){
        Trade newTrade = null;
        TradeResponseDto responseDto = null;
        GameDetails game = gameDao.getGameByGameDetailsId(gameDetailsId);
        BigDecimal commissionFee = new BigDecimal(game.getCommissionFee());
        User currentUser = userDao.getUserByUsername(principal.getName());
        GamePlayer gamePlayer = gameDao.getGamePlayerByUserIdAndDetailsId(currentUser.getId(), gameDetailsId);
        BigDecimal stockPrice = stockDao.getCurrentStockPriceBySymbol(stockSymbol);
        double tradeValue = stockPrice.doubleValue() * (numberOfShares) + commissionFee.doubleValue();
        BigDecimal bigDecimal = new BigDecimal(tradeValue);
        BigDecimal cash = gameDao.getCashForGamePlayer(gamePlayer.getGamePlayerId());

        if (game.getGameStatusId() == 3){
            throw new ServiceException("MARKETS ARE CLOSED FOR GOOD :D");
        }

        try{
            if(tradeTypeId == BUY_TRADE_TYPE){
                if(cash != null && cash.doubleValue() >= tradeValue){
                    newTrade = tradeDao.createTrade(gamePlayer.getGamePlayerId(), stockSymbol, BUY_TRADE_TYPE, numberOfShares, stockPrice);

                    if(gameDao.getNumberOfSharesForPlayerAndStock(gamePlayer.getGamePlayerId(), stockSymbol) == 0){
                        gameDao.insertGamePlayerStock(gamePlayer.getGamePlayerId(), stockSymbol, numberOfShares);
                    }else{
                        gameDao.updatePlayerPortfolioBuy(gamePlayer.getGamePlayerId(), stockSymbol, numberOfShares);
                    }

                    gamePlayer.setCash(gamePlayer.getCash().subtract(bigDecimal.add(commissionFee)));
                    gameDao.updateGamePlayer(gamePlayer);

                    responseDto = new TradeResponseDto(stockSymbol, numberOfShares, tradeTypeId, gameDetailsId, stockPrice, gameDao.getCashForGamePlayer(gamePlayer.getGamePlayerId()));

                }
            } else if (tradeTypeId == SELL_TRADE_TYPE){
                int currentSharesInDatabase = gameDao.getNumberOfSharesForPlayerAndStock(gamePlayer.getGamePlayerId(), stockSymbol);
                if(currentSharesInDatabase >= numberOfShares){
                    newTrade = tradeDao.createTrade(gamePlayer.getGamePlayerId(), stockSymbol, SELL_TRADE_TYPE, numberOfShares, stockPrice);

                    int remainingShares = currentSharesInDatabase - numberOfShares;
                    if(remainingShares == 0) {
                        gameDao.removeStockFromPortfolio(gamePlayer.getGamePlayerId(), stockSymbol);

                    } else {
                        gameDao.updatePlayerPortfolioSell(gamePlayer.getGamePlayerId(), stockSymbol, numberOfShares);

                    }


                    gamePlayer.setCash(gamePlayer.getCash().add(bigDecimal).subtract(commissionFee));
                    gameDao.updateGamePlayer(gamePlayer);

                    responseDto = new TradeResponseDto(stockSymbol, numberOfShares, tradeTypeId, gameDetailsId, stockPrice, gameDao.getCashForGamePlayer(gamePlayer.getGamePlayerId()));
                }

            } else{
                throw new ServiceException("You do not have enough money to make this trade.");
            }
        } catch (ServiceException e){
            throw new ServiceException("Error occurred " + e.getMessage());
        }
        return responseDto;
    }

}
