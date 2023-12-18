package com.techelevator.model;

import java.util.Objects;

public class GameStatus {

    private int gameStatusId;
    private String gameStatusDesc;

    public GameStatus(int gameStatusId, String gameStatusDesc) {
        this.gameStatusId = gameStatusId;
        this.gameStatusDesc = gameStatusDesc;
    }

    public int getGameStatusId() {
        return gameStatusId;
    }

    public void setGameStatusId(int gameStatusId) {
        this.gameStatusId = gameStatusId;
    }

    public String getGameStatusDesc() {
        return gameStatusDesc;
    }

    public void setGameStatusDesc(String gameStatusDesc) {
        this.gameStatusDesc = gameStatusDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStatus that = (GameStatus) o;
        return gameStatusId == that.gameStatusId && Objects.equals(gameStatusDesc, that.gameStatusDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameStatusId, gameStatusDesc);
    }

    @Override
    public String toString() {
        return "GameStatus{" +
                "gameStatusId=" + gameStatusId +
                ", gameStatusDesc='" + gameStatusDesc + '\'' +
                '}';
    }
}
