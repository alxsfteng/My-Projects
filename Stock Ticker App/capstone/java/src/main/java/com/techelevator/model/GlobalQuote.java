package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class GlobalQuote {

    @JsonProperty("Global Quote")
    private Stock stock;

    public GlobalQuote() {
    }
    public GlobalQuote(Stock stock) {
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlobalQuote that = (GlobalQuote) o;
        return Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stock);
    }

    @Override
    public String toString() {
        return "GlobalQuote{" +
                "stock=" + stock +
                '}';
    }
}
