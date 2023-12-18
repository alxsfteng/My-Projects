package com.techelevator.dao.trade;

import com.techelevator.model.Stock;
import com.techelevator.model.Trade;

import java.math.BigDecimal;

public interface TradeDao {
    Trade createTrade(int gamePlayerId, String stockSymbol, int tradeTypeId, int numberOfShares, BigDecimal priceOfStock);
    Trade getTradeByTradeId(int id);

}
