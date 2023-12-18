package com.techelevator.model.dtos;

import com.techelevator.model.PortfolioStock;

import java.math.BigDecimal;
import java.util.*;

public class TradeResponseDto {
    private String stockSymbol;
    private int numberOfShares;
    private int tradeTypeId;
    private int gameDetailsId;
    private BigDecimal stockPrice;
    private BigDecimal playerUpdatedBalance;

    public TradeResponseDto(String stockSymbol, int numberOfShares, int tradeTypeId, int gameDetailsId, BigDecimal stockPrice, BigDecimal playerUpdatedBalance) {
        this.stockSymbol = stockSymbol;
        this.numberOfShares = numberOfShares;
        this.tradeTypeId = tradeTypeId;
        this.gameDetailsId = gameDetailsId;
        this.stockPrice = stockPrice;
        this.playerUpdatedBalance = playerUpdatedBalance;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public int getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(int tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }

    public int getGameDetailsId() {
        return gameDetailsId;
    }

    public void setGameDetailsId(int gameDetailsId) {
        this.gameDetailsId = gameDetailsId;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    public BigDecimal getPlayerUpdatedBalance() {
        return playerUpdatedBalance;
    }

    public void setPlayerUpdatedBalance(BigDecimal playerUpdatedBalance) {
        this.playerUpdatedBalance = playerUpdatedBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeResponseDto that = (TradeResponseDto) o;
        return numberOfShares == that.numberOfShares && tradeTypeId == that.tradeTypeId && gameDetailsId == that.gameDetailsId && Objects.equals(stockSymbol, that.stockSymbol) && Objects.equals(stockPrice, that.stockPrice) && Objects.equals(playerUpdatedBalance, that.playerUpdatedBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockSymbol, numberOfShares, tradeTypeId, gameDetailsId, stockPrice, playerUpdatedBalance);
    }

    @Override
    public String toString() {
        return "TradeResponseDto{" +
                "stockSymbol='" + stockSymbol + '\'' +
                ", numberOfShares=" + numberOfShares +
                ", tradeTypeId=" + tradeTypeId +
                ", gameDetailsId=" + gameDetailsId +
                ", stockPrice=" + stockPrice +
                ", playerUpdatedBalance=" + playerUpdatedBalance +
                '}';
    }
}
