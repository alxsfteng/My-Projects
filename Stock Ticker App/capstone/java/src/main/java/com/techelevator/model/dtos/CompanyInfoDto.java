package com.techelevator.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.model.Company;

import java.util.Objects;

public class CompanyInfoDto {
    public String symbol;
    public String name;
    public String description;
    public String exchange;
    public String currency;
    public String country;
    public String sector;
    public String industry;
    public String address;

    public CompanyInfoDto(){

    }

    public CompanyInfoDto(String symbol, String name, String description, String exchange, String currency, String country, String sector, String industry, String address) {
        this.symbol = symbol;
        this.name = name;
        this.description = description;
        this.exchange = exchange;
        this.currency = currency;
        this.country = country;
        this.sector = sector;
        this.industry = industry;
        this.address = address;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyInfoDto that = (CompanyInfoDto) o;
        return Objects.equals(symbol, that.symbol) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(exchange, that.exchange) && Objects.equals(currency, that.currency) && Objects.equals(country, that.country) && Objects.equals(sector, that.sector) && Objects.equals(industry, that.industry) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, name, description, exchange, currency, country, sector, industry, address);
    }

    @Override
    public String toString() {
        return "CompanyInfoDto{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", exchange='" + exchange + '\'' +
                ", currency='" + currency + '\'' +
                ", country='" + country + '\'' +
                ", sector='" + sector + '\'' +
                ", industry='" + industry + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
