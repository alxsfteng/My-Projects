package com.techelevator.model;

import java.util.Objects;

public class PortfolioStock {
    private int gamePlayerId;
    private String stock;
    private int numberOfShares;

    public PortfolioStock() {
    }

    public PortfolioStock(int gamePlayerId, String stock, int numberOfShares) {
        this.gamePlayerId = gamePlayerId;
        this.stock = stock;
        this.numberOfShares = numberOfShares;
    }


    public int getGamePlayerId() {
        return gamePlayerId;
    }

    public void setGamePlayerId(int gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioStock that = (PortfolioStock) o;
        return gamePlayerId == that.gamePlayerId && numberOfShares == that.numberOfShares && Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gamePlayerId, stock, numberOfShares);
    }

    @Override
    public String toString() {
        return "PortfolioStock{" +
                "gamePlayerId=" + gamePlayerId +
                ", stock='" + stock + '\'' +
                ", numberOfShares=" + numberOfShares +
                '}';
    }
}
