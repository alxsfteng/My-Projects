package com.techelevator.dao.stock;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Company;
import com.techelevator.model.Stock;
import com.techelevator.model.dtos.PortfolioDto;
import com.techelevator.model.dtos.PortfolioSummaryDto;
import com.techelevator.model.dtos.StockInfoDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcStockDao implements StockDao {

    JdbcTemplate jdbcTemplate;

    public JdbcStockDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getCurrentStockPriceBySymbol(String symbol){
        String sql = "SELECT stock_price FROM stock_performance WHERE stock_symbol = ? ORDER BY date_time DESC LIMIT 1;";

        try{
            return jdbcTemplate.queryForObject(sql, BigDecimal.class, symbol);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
    }

    @Override
    public void insertStockInfo(Stock stock){
        String sql = "INSERT INTO stock (stock_symbol, open, daily_high, daily_low, volume, latest_trading_day, previous_close, change, change_percent) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Double stockChangePercent = Double.parseDouble(stock.getChangePercent().substring(0, stock.getChangePercent().length() - 1));
        try{
            int result = jdbcTemplate.update(sql, stock.getSymbol(), stock.getOpen(), stock.getHigh(), stock.getLow(), stock.getVolume(), stock.getLatestTradingDay(), stock.getPreviousClose(),
                                             stock.getChange(), stockChangePercent);

            if(result == 0){
                throw new DaoException("Zero rows affected. Expected at least one row");
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    }
    @Override
    public StockInfoDto getStockInfo(String symbol){
        StockInfoDto stockInfoDto = null;
        String sql = "SELECT c.exchange, c.asset_type, c.fifty_two_week_high, c.fifty_two_week_low, " +
                "s.stock_symbol, sp.stock_price, s.daily_high, s.daily_low, s.volume, c.company_name " +
                "FROM company c " +
                "join stock s ON c.stock_symbol = s.stock_symbol " +
                "join stock_performance sp ON s.stock_symbol = sp.stock_symbol " +
                "WHERE c.stock_symbol = ? " +
                "ORDER BY sp.date_time DESC " +
                "LIMIT 1;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, symbol);
            if(results.next()){
                stockInfoDto = mapRowToStock(results);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return stockInfoDto;
    }

    public void insertIntoStockPerformance(String symbol, BigDecimal stockPrice, LocalDateTime dateTimeNow){
        String sql = "INSERT INTO stock_performance (stock_symbol, stock_price, date_time) VALUES (?, ?, ?);";
        try{
            int result = jdbcTemplate.update(sql, symbol, stockPrice, dateTimeNow);

            if (result == 0){
                throw new DaoException("Zero rows affected. Expected at least one row");

            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }

    }

    @Override
    public List<PortfolioSummaryDto> getPortfolio(int gamePlayerId){
        List<PortfolioSummaryDto> portfolioSummaryDtoList = new ArrayList<>();
        String sql = "SELECT stock_symbol, number_of_shares FROM game_player_stock WHERE game_player_id = ?;";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, gamePlayerId);
            while (rowSet.next()) {
                String stockSymbol = rowSet.getString("stock_symbol");
                int numberOfShares = rowSet.getInt("number_of_shares");

                PortfolioSummaryDto portfolio = new PortfolioSummaryDto(stockSymbol, numberOfShares);
                portfolioSummaryDtoList.add(portfolio);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return portfolioSummaryDtoList;
    }

    public StockInfoDto mapRowToStock(SqlRowSet rowSet) {
        StockInfoDto stockInfo = new StockInfoDto();
        stockInfo.setExchange(rowSet.getString("exchange"));
        stockInfo.setAssetType(rowSet.getString("asset_type"));
        stockInfo.setFiftyTwoWeekHigh(rowSet.getString("fifty_two_week_high"));
        stockInfo.setFiftyTwoWeekLow(rowSet.getString("fifty_two_week_low"));
        stockInfo.setStockSymbol(rowSet.getString("stock_symbol"));
        stockInfo.setStockPrice(rowSet.getString("stock_price"));
        stockInfo.setDailyHigh(rowSet.getString("daily_high"));
        stockInfo.setDailyLow(rowSet.getString("daily_low"));
        stockInfo.setVolume(rowSet.getString("volume"));
        stockInfo.setCompanyName(rowSet.getString("company_name"));
        return stockInfo;
    }
}
