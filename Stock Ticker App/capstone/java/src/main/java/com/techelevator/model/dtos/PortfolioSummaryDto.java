package com.techelevator.model.dtos;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PortfolioSummaryDto {

    private String symbol;
    private int quantity;

    public PortfolioSummaryDto(String symbol, int quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioSummaryDto that = (PortfolioSummaryDto) o;
        return quantity == that.quantity && Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, quantity);
    }

    @Override
    public String toString() {
        return "PortfolioSummaryDto{" +
                "symbol='" + symbol + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
