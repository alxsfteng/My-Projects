package com.techelevator.model.dtos;

import com.techelevator.model.Stock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class PortfolioDto {
    private List<String> portfolio;
    private BigDecimal costOfTrade;
    private BigDecimal currentCash;

    public PortfolioDto(List<String> portfolio, BigDecimal costOfTrade, BigDecimal currentCash) {
        this.portfolio = portfolio;
        this.costOfTrade = costOfTrade;
        this.currentCash = currentCash;
    }

    public PortfolioDto(List<String> portfolio, BigDecimal costOfTrade) {
        this.portfolio = portfolio;
        this.costOfTrade = costOfTrade;
    }

    public List<String> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(List<String> portfolio) {
        this.portfolio = portfolio;
    }

    public BigDecimal getCostOfTrade() {
        return costOfTrade;
    }

    public void setCostOfTrade(BigDecimal costOfTrade) {
        this.costOfTrade = costOfTrade;
    }

    public BigDecimal getCurrentCash() {
        return currentCash;
    }

    public void setCurrentCash(BigDecimal currentCash) {
        this.currentCash = currentCash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioDto that = (PortfolioDto) o;
        return Objects.equals(portfolio, that.portfolio) && Objects.equals(costOfTrade, that.costOfTrade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolio, costOfTrade);
    }

    @Override
    public String toString() {
        return "PortfolioDto{" +
                "portfolio=" + portfolio +
                ", costOfTrade=" + costOfTrade +
                '}';
    }
}
