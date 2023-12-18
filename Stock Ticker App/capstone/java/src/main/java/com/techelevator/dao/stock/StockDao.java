package com.techelevator.dao.stock;

import com.techelevator.model.Stock;
import com.techelevator.model.dtos.PortfolioSummaryDto;
import com.techelevator.model.dtos.StockInfoDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface StockDao {

    public BigDecimal getCurrentStockPriceBySymbol(String symbol);
    List<PortfolioSummaryDto> getPortfolio(int gamePlayerId);
    void insertStockInfo(Stock stock);
    void insertIntoStockPerformance(String symbol, BigDecimal stockPrice, LocalDateTime dateTimeNow);
    StockInfoDto getStockInfo(String symbol);
}
