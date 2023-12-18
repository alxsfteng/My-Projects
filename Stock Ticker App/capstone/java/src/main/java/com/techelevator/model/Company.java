package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class Company {
    public int companyId;
    @JsonProperty("Symbol")
    public String symbol;
    @JsonProperty("AssetType")
    public String assetType;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Description")
    public String description;
    @JsonProperty("Exchange")
    public String exchange;
    @JsonProperty("Currency")
    public String currency;
    @JsonProperty("Country")
    public String country;
    @JsonProperty("Sector")
    public String sector;
    @JsonProperty("Industry")
    public String industry;
    @JsonProperty("Address")
    public String address;
    @JsonProperty("EPS")
    public BigDecimal EPS;
    @JsonProperty("ForwardPE")
    public BigDecimal forwardPE;
    @JsonProperty("52WeekHigh")
    public BigDecimal _52WeekHigh;
    @JsonProperty("52WeekLow")
    public BigDecimal _52WeekLow;
    @JsonProperty("50DayMovingAverage")
    public BigDecimal _50DayMovingAverage;
    public String flavicon;
    public String companyUrl;

    public Company() {
    }

    public Company(int companyId, String symbol, String assetType, String name, String description, String exchange, String currency, String country, String sector, String industry, String address, BigDecimal EPS, BigDecimal forwardPE, BigDecimal _52WeekHigh, BigDecimal _52WeekLow, BigDecimal _50DayMovingAverage, String flavicon, String companyUrl) {
        this.companyId = companyId;
        this.symbol = symbol;
        this.assetType = assetType;
        this.name = name;
        this.description = description;
        this.exchange = exchange;
        this.currency = currency;
        this.country = country;
        this.sector = sector;
        this.industry = industry;
        this.address = address;
        this.EPS = EPS;
        this.forwardPE = forwardPE;
        this._52WeekHigh = _52WeekHigh;
        this._52WeekLow = _52WeekLow;
        this._50DayMovingAverage = _50DayMovingAverage;
        this.flavicon = flavicon;
        this.companyUrl = companyUrl;
    }

    public BigDecimal getEPS() {
        return EPS;
    }

    public void setEPS(BigDecimal EPS) {
        this.EPS = EPS;
    }

    public BigDecimal getForwardPE() {
        return forwardPE;
    }

    public void setForwardPE(BigDecimal forwardPE) {
        this.forwardPE = forwardPE;
    }

    public BigDecimal get_52WeekHigh() {
        return _52WeekHigh;
    }

    public void set_52WeekHigh(BigDecimal _52WeekHigh) {
        this._52WeekHigh = _52WeekHigh;
    }

    public BigDecimal get_52WeekLow() {
        return _52WeekLow;
    }

    public void set_52WeekLow(BigDecimal _52WeekLow) {
        this._52WeekLow = _52WeekLow;
    }

    public BigDecimal get_50DayMovingAverage() {
        return _50DayMovingAverage;
    }

    public void set_50DayMovingAverage(BigDecimal _50DayMovingAverage) {
        this._50DayMovingAverage = _50DayMovingAverage;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
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

    public String getFlavicon() {
        return flavicon;
    }

    public void setFlavicon(String flavicon) {
        this.flavicon = flavicon;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyId == company.companyId && Objects.equals(symbol, company.symbol) && Objects.equals(assetType, company.assetType) && Objects.equals(name, company.name) && Objects.equals(description, company.description) && Objects.equals(exchange, company.exchange) && Objects.equals(currency, company.currency) && Objects.equals(country, company.country) && Objects.equals(sector, company.sector) && Objects.equals(industry, company.industry) && Objects.equals(address, company.address) && Objects.equals(EPS, company.EPS) && Objects.equals(forwardPE, company.forwardPE) && Objects.equals(_52WeekHigh, company._52WeekHigh) && Objects.equals(_52WeekLow, company._52WeekLow) && Objects.equals(_50DayMovingAverage, company._50DayMovingAverage) && Objects.equals(flavicon, company.flavicon) && Objects.equals(companyUrl, company.companyUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, symbol, assetType, name, description, exchange, currency, country, sector, industry, address, EPS, forwardPE, _52WeekHigh, _52WeekLow, _50DayMovingAverage, flavicon, companyUrl);
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", symbol='" + symbol + '\'' +
                ", assetType='" + assetType + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", exchange='" + exchange + '\'' +
                ", currency='" + currency + '\'' +
                ", country='" + country + '\'' +
                ", sector='" + sector + '\'' +
                ", industry='" + industry + '\'' +
                ", address='" + address + '\'' +
                ", EPS=" + EPS +
                ", forwardPE=" + forwardPE +
                ", _52WeekHigh=" + _52WeekHigh +
                ", _52WeekLow=" + _52WeekLow +
                ", _50DayMovingAverage=" + _50DayMovingAverage +
                ", flavicon='" + flavicon + '\'' +
                ", companyUrl='" + companyUrl + '\'' +
                '}';
    }
}
