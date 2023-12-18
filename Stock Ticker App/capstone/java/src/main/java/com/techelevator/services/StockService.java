package com.techelevator.services;

import com.techelevator.model.Stock;
import com.techelevator.model.dtos.StockInfoDto;

import java.math.BigDecimal;
import java.security.Principal;

public interface StockService {
    void insertStockInfo(String symbol);
    BigDecimal getCurrentStockPriceBySymbol(String symbol);
    void updateStockHistory();
    StockInfoDto getStockInfo(String symbol);

}
