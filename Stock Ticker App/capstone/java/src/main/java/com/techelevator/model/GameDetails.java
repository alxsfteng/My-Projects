package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameDetails {
    private int gameDetailsId;
    private String gameName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime gameStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime gameEnd;
    private int playerCount;
    private int difficultyId;
    private int gameStatusId;
    private double commissionFee;
    private List<GamePlayer> players = new ArrayList<>();

    public GameDetails(){

    }

    public GameDetails(int gameDetailsId, String gameName, LocalDateTime gameStart, LocalDateTime gameEnd, int playerCount, int difficultyId, int gameStatusId, double commissionFee, List<GamePlayer> players) {
        this.gameDetailsId = gameDetailsId;
        this.gameName = gameName;
        this.gameStart = gameStart;
        this.gameEnd = gameEnd;
        this.playerCount = playerCount;
        this.difficultyId = difficultyId;
        this.gameStatusId = gameStatusId;
        this.commissionFee = commissionFee;
        this.players = players;
    }

    public int getGameDetailsId() {
        return gameDetailsId;
    }

    public void setGameDetailsId(int gameDetailsId) {
        this.gameDetailsId = gameDetailsId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public LocalDateTime getGameStart() {
        return gameStart;
    }

    public void setGameStart(LocalDateTime gameStart) {
        this.gameStart = gameStart;
    }

    public LocalDateTime getGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(LocalDateTime gameEnd) {
        this.gameEnd = gameEnd;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(int difficultyId) {
        this.difficultyId = difficultyId;
    }

    public int getGameStatusId() {
        return gameStatusId;
    }

    public void setGameStatusId(int gameStatusId) {
        this.gameStatusId = gameStatusId;
    }

    public double getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(double commissionFee) {
        this.commissionFee = commissionFee;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<GamePlayer> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDetails that = (GameDetails) o;
        return gameDetailsId == that.gameDetailsId && playerCount == that.playerCount && difficultyId == that.difficultyId && gameStatusId == that.gameStatusId && Double.compare(that.commissionFee, commissionFee) == 0 && Objects.equals(gameName, that.gameName) && Objects.equals(gameStart, that.gameStart) && Objects.equals(gameEnd, that.gameEnd) && Objects.equals(players, that.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameDetailsId, gameName, gameStart, gameEnd, playerCount, difficultyId, gameStatusId, commissionFee, players);
    }

    @Override
    public String toString() {
        return "GameDetails{" +
                "gameDetailsId=" + gameDetailsId +
                ", gameName='" + gameName + '\'' +
                ", gameStart=" + gameStart +
                ", gameEnd=" + gameEnd +
                ", playerCount=" + playerCount +
                ", difficultyId=" + difficultyId +
                ", gameStatusId=" + gameStatusId +
                ", commissionFee=" + commissionFee +
                ", players=" + players +
                '}';
    }

    public void getStartingAmount(){
        if(players != null && !players.isEmpty()){
            players.get(0).getCash();
        }
    }
}

