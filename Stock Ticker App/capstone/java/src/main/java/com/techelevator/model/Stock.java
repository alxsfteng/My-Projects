package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Stock {
    @JsonProperty("01. symbol")
    public String symbol;
    @JsonProperty("02. open")
    public BigDecimal open;
    @JsonProperty("03. high")
    public BigDecimal high;
    @JsonProperty("04. low")
    public BigDecimal low;
    @JsonProperty("05. price")
    public BigDecimal price;
    @JsonProperty("06. volume")
    public int volume;
    @JsonProperty("07. latest trading day")
    public LocalDate latestTradingDay;
    @JsonProperty("08. previous close")
    public BigDecimal previousClose;
    @JsonProperty("09. change")
    public BigDecimal change;
    @JsonProperty("10. change percent")
    public String changePercent;

    public Stock(){
    }

    public Stock(String symbol, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal price, int volume, LocalDate latestTradingDay, BigDecimal previousClose, BigDecimal change, String changePercent) {
        this.symbol = symbol;
        this.open = open;
        this.high = high;
        this.low = low;
        this.price = price;
        this.volume = volume;
        this.latestTradingDay = latestTradingDay;
        this.previousClose = previousClose;
        this.change = change;
        this.changePercent = changePercent;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public LocalDate getLatestTradingDay() {
        return latestTradingDay;
    }

    public void setLatestTradingDay(LocalDate latestTradingDay) {
        this.latestTradingDay = latestTradingDay;
    }

    public BigDecimal getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(BigDecimal previousClose) {
        this.previousClose = previousClose;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return volume == stock.volume && Objects.equals(symbol, stock.symbol) && Objects.equals(open, stock.open) && Objects.equals(high, stock.high) && Objects.equals(low, stock.low) && Objects.equals(price, stock.price) && Objects.equals(latestTradingDay, stock.latestTradingDay) && Objects.equals(previousClose, stock.previousClose) && Objects.equals(change, stock.change) && Objects.equals(changePercent, stock.changePercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, open, high, low, price, volume, latestTradingDay, previousClose, change, changePercent);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", price=" + price +
                ", volume=" + volume +
                ", latestTradingDay=" + latestTradingDay +
                ", previousClose=" + previousClose +
                ", change=" + change +
                ", changePercent='" + changePercent + '\'' +
                '}';
    }
}
