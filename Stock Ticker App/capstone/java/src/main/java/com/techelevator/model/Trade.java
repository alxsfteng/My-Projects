package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Trade {
    private int tradeId;
    private int gamePlayerId;
    private String stockSymbol;
    private int tradeTypeId;
    private int numberOfShares;
    private BigDecimal priceOfStockAtTrade;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime date_time;

    public Trade(int tradeId, int gamePlayerId, String stockSymbol, int tradeTypeId, int numberOfShares, BigDecimal priceOfStockAtTrade, LocalDateTime date_time) {
        this.tradeId = tradeId;
        this.gamePlayerId = gamePlayerId;
        this.stockSymbol = stockSymbol;
        this.tradeTypeId = tradeTypeId;
        this.numberOfShares = numberOfShares;
        this.priceOfStockAtTrade = priceOfStockAtTrade;
        this.date_time = date_time;
    }

    public Trade(){

    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public int getGamePlayerId() {
        return gamePlayerId;
    }

    public void setGamePlayerId(int gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public int getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(int tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public BigDecimal getPriceOfStockAtTrade() {
        return priceOfStockAtTrade;
    }

    public void setPriceOfStockAtTrade(BigDecimal priceOfStockAtTrade) {
        this.priceOfStockAtTrade = priceOfStockAtTrade;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return tradeId == trade.tradeId && gamePlayerId == trade.gamePlayerId && tradeTypeId == trade.tradeTypeId && numberOfShares == trade.numberOfShares && Objects.equals(stockSymbol, trade.stockSymbol) && Objects.equals(priceOfStockAtTrade, trade.priceOfStockAtTrade) && Objects.equals(date_time, trade.date_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId, gamePlayerId, stockSymbol, tradeTypeId, numberOfShares, priceOfStockAtTrade, date_time);
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", gamePlayerId=" + gamePlayerId +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", tradeTypeId=" + tradeTypeId +
                ", numberOfShares=" + numberOfShares +
                ", priceOfStockAtTrade=" + priceOfStockAtTrade +
                ", date_time=" + date_time +
                '}';
    }
}
