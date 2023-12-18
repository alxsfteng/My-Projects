package com.techelevator.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GamePlayer {
    private int gamePlayerId;
    private int userId;
    private BigDecimal cash;
    private boolean isHost = false;
    private String userName;

    public GamePlayer() {

    }

    public GamePlayer(int gamePlayerId, int userId, BigDecimal cash, boolean isHost, String userName) {
        this.gamePlayerId = gamePlayerId;
        this.userId = userId;
        this.cash = cash;
        this.isHost = isHost;
        this.userName = userName;
    }

    public int getGamePlayerId() {
        return gamePlayerId;
    }

    public void setGamePlayerId(int gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePlayer that = (GamePlayer) o;
        return gamePlayerId == that.gamePlayerId && userId == that.userId && isHost == that.isHost && Objects.equals(cash, that.cash) && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gamePlayerId, userId, cash, isHost, userName);
    }

    @Override
    public String toString() {
        return "GamePlayer{" +
                "gamePlayerId=" + gamePlayerId +
                ", userId=" + userId +
                ", cash=" + cash +
                ", isHost=" + isHost +
                ", userName='" + userName + '\'' +
                '}';
    }
}
