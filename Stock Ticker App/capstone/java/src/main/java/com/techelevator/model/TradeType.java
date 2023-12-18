package com.techelevator.model;

import java.util.Objects;

public class TradeType {
    private int tradeTypeId;
    private String tradeTypeDesc;

    public TradeType(int tradeTypeId, String tradeTypeDesc) {
        this.tradeTypeId = tradeTypeId;
        this.tradeTypeDesc = tradeTypeDesc;
    }

    public int getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(int tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }

    public String getTradeTypeDesc() {
        return tradeTypeDesc;
    }

    public void setTradeTypeDesc(String tradeTypeDesc) {
        this.tradeTypeDesc = tradeTypeDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeType tradeType = (TradeType) o;
        return tradeTypeId == tradeType.tradeTypeId && Objects.equals(tradeTypeDesc, tradeType.tradeTypeDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeTypeId, tradeTypeDesc);
    }

    @Override
    public String toString() {
        return "TradeType{" +
                "tradeTypeId=" + tradeTypeId +
                ", tradeTypeDesc='" + tradeTypeDesc + '\'' +
                '}';
    }
}
