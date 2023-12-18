package com.techelevator.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class GameDetailsDto {

    private String gameName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime gameStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime gameEnd;
    private int difficultyId;
    private double commissionFee;
    private BigDecimal cash;
    private int playerCount;
    private List<String> invitedFriends;


    public GameDetailsDto() {
    }

    public GameDetailsDto(String gameName, LocalDateTime gameStart, LocalDateTime gameEnd, int difficultyId, double commissionFee, BigDecimal cash, int playerCount, List<String> invitedFriends) {
        this.gameName = gameName;
        this.gameStart = gameStart;
        this.gameEnd = gameEnd;
        this.difficultyId = difficultyId;
        this.commissionFee = commissionFee;
        this.cash = cash;
        this.playerCount = playerCount;
        this.invitedFriends = invitedFriends;
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

    public int getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(int difficultyId) {
        this.difficultyId = difficultyId;
    }

    public double getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(double commissionFee) {
        this.commissionFee = commissionFee;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public List<String> getInvitedFriends() {
        return invitedFriends;
    }

    public void setInvitedFriends(List<String> invitedFriends) {
        this.invitedFriends = invitedFriends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDetailsDto that = (GameDetailsDto) o;
        return difficultyId == that.difficultyId && Double.compare(that.commissionFee, commissionFee) == 0 && playerCount == that.playerCount && Objects.equals(gameName, that.gameName) && Objects.equals(gameStart, that.gameStart) && Objects.equals(gameEnd, that.gameEnd) && Objects.equals(cash, that.cash) && Objects.equals(invitedFriends, that.invitedFriends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName, gameStart, gameEnd, difficultyId, commissionFee, cash, playerCount, invitedFriends);
    }

    @Override
    public String toString() {
        return "GameDetailsDto{" +
                "gameName='" + gameName + '\'' +
                ", gameStart=" + gameStart +
                ", gameEnd=" + gameEnd +
                ", difficultyId=" + difficultyId +
                ", commissionFee=" + commissionFee +
                ", cash=" + cash +
                ", playerCount=" + playerCount +
                ", invitedFriends=" + invitedFriends +
                '}';
    }
}
