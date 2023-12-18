package com.techelevator.model.dtos;

import java.util.Objects;

public class StockInfoDto {
    private String exchange;
    private String assetType;
    private String fiftyTwoWeekHigh;
    private String fiftyTwoWeekLow;
    private String stockSymbol;
    private String stockPrice;
    private String dailyHigh;
    private String dailyLow;
    private String volume;
    private String companyName;

    public StockInfoDto() {
    }

    public StockInfoDto(String exchange, String assetType, String fiftyTwoWeekHigh, String fiftyTwoWeekLow, String stockSymbol, String stockPrice, String dailyHigh, String dailyLow, String volume, String companyName) {
        this.exchange = exchange;
        this.assetType = assetType;
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
        this.stockSymbol = stockSymbol;
        this.stockPrice = stockPrice;
        this.dailyHigh = dailyHigh;
        this.dailyLow = dailyLow;
        this.volume = volume;
        this.companyName = companyName;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getFiftyTwoWeekHigh() {
        return fiftyTwoWeekHigh;
    }

    public void setFiftyTwoWeekHigh(String fiftyTwoWeekHigh) {
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
    }

    public String getFiftyTwoWeekLow() {
        return fiftyTwoWeekLow;
    }

    public void setFiftyTwoWeekLow(String fiftyTwoWeekLow) {
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getDailyHigh() {
        return dailyHigh;
    }

    public void setDailyHigh(String dailyHigh) {
        this.dailyHigh = dailyHigh;
    }

    public String getDailyLow() {
        return dailyLow;
    }

    public void setDailyLow(String dailyLow) {
        this.dailyLow = dailyLow;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockInfoDto that = (StockInfoDto) o;
        return Objects.equals(exchange, that.exchange) && Objects.equals(assetType, that.assetType) && Objects.equals(fiftyTwoWeekHigh, that.fiftyTwoWeekHigh) && Objects.equals(fiftyTwoWeekLow, that.fiftyTwoWeekLow) && Objects.equals(stockSymbol, that.stockSymbol) && Objects.equals(stockPrice, that.stockPrice) && Objects.equals(dailyHigh, that.dailyHigh) && Objects.equals(dailyLow, that.dailyLow) && Objects.equals(volume, that.volume) && Objects.equals(companyName, that.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchange, assetType, fiftyTwoWeekHigh, fiftyTwoWeekLow, stockSymbol, stockPrice, dailyHigh, dailyLow, volume, companyName);
    }

    @Override
    public String toString() {
        return "StockInfoDto{" +
                "exchange='" + exchange + '\'' +
                ", assetType='" + assetType + '\'' +
                ", fiftyTwoWeekHigh='" + fiftyTwoWeekHigh + '\'' +
                ", fiftyTwoWeekLow='" + fiftyTwoWeekLow + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", stockPrice='" + stockPrice + '\'' +
                ", dailyHigh='" + dailyHigh + '\'' +
                ", dailyLow='" + dailyLow + '\'' +
                ", volume='" + volume + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
