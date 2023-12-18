package com.techelevator.model.dtos;

import java.util.Objects;

public class TradeDto {
    private String stockSymbol;
    private int numberOfShares;
    private int tradeTypeId;
    private int gameDetailsId;


    public TradeDto(String stockSymbol, int numberOfShares, int tradeTypeId, int gameDetailsId) {
        this.stockSymbol = stockSymbol;
        this.numberOfShares = numberOfShares;
        this.tradeTypeId = tradeTypeId;
        this.gameDetailsId = gameDetailsId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeDto tradeDto = (TradeDto) o;
        return numberOfShares == tradeDto.numberOfShares && tradeTypeId == tradeDto.tradeTypeId && gameDetailsId == tradeDto.gameDetailsId && Objects.equals(stockSymbol, tradeDto.stockSymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockSymbol, numberOfShares, tradeTypeId, gameDetailsId);
    }

    @Override
    public String toString() {
        return "TradeDto{" +
                "stockSymbol='" + stockSymbol + '\'' +
                ", numberOfShares=" + numberOfShares +
                ", tradeTypeId=" + tradeTypeId +
                ", gameDetailsId=" + gameDetailsId +
                '}';
    }
}
