package com.techelevator.dao.trade;

import com.techelevator.exception.DaoException;
import com.techelevator.model.GamePlayer;
import com.techelevator.model.Stock;
import com.techelevator.model.Trade;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class JdbcTradeDao implements TradeDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTradeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Trade getTradeByTradeId(int id){
        Trade trade = null;
        String sql = "SELECT trade_id, game_player_id, stock_symbol, trade_type_id, number_of_shares FROM trade WHERE trade_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()){
                trade = mapRowToTrade(results);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return trade;
    }
    @Override
    public Trade createTrade(int gamePlayerId, String stockSymbol, int tradeTypeId, int numberOfShares, BigDecimal priceOfStock){
        Trade newTrade = null;
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        String sql = "INSERT INTO trade (game_player_id, stock_symbol, trade_type_id, number_of_shares, price_of_stock_at_trade, date_time) " +
                "VALUES (?,?,?,?,?,?) RETURNING trade_id;";

        try{
            int newTradeId = jdbcTemplate.queryForObject(sql, int.class, gamePlayerId, stockSymbol, tradeTypeId, numberOfShares, priceOfStock, localDateTimeNow);
            newTrade = getTradeByTradeId(newTradeId);

        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return newTrade;

    }

    private Trade mapRowToTrade(SqlRowSet rowSet){
        Trade result = new Trade();
        result.setTradeId(rowSet.getInt("trade_id"));
        result.setGamePlayerId(rowSet.getInt("game_player_id"));
        result.setStockSymbol(rowSet.getString("stock_symbol"));
        result.setTradeTypeId(rowSet.getInt("trade_type_id"));
        result.setNumberOfShares(rowSet.getInt("number_of_shares"));
        return result;
    }
}
