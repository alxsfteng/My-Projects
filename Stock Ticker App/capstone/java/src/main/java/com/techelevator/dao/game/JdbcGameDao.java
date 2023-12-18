package com.techelevator.dao.game;

import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.GameDetails;
import com.techelevator.model.GamePlayer;
import com.techelevator.model.PortfolioStock;
import com.techelevator.model.authority.User;
import com.techelevator.model.dtos.GameDetailsDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcGameDao implements GameDao {

    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    private final int STAGING_GAME_STATUS = 1;
    private final int IN_PROGRESS_GAME_STATUS = 2;
    private final int GAME_OVER_GAME_STATUS = 3;
    private final int BUY_TRADE_TYPE = 1;
    private final int SELL_TRADE_TYPE = 2;

    public JdbcGameDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public int getUserIdByGamePlayerId(int gamePlayerId){
        String sql = "SELECT user_id FROM game_player WHERE game_player_id = ?;";

        try{
            Integer userId = jdbcTemplate.queryForObject(sql, Integer.class, gamePlayerId);
            return (userId != null) ? userId : 0;
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    }
    @Override
    public BigDecimal getCashForGamePlayer(int gamePlayerId){
        String sql = "SELECT cash FROM game_player WHERE game_player_id = ?;";
        try{
            return jdbcTemplate.queryForObject(sql, BigDecimal.class, gamePlayerId);
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    }
    @Override
    public int getNumberOfSharesForPlayerAndStock(int gamePlayerId, String stockSymbol){

        String sql = "SELECT number_of_shares FROM game_player_stock WHERE game_player_id = ? AND stock_symbol = ?";

        try{
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, gamePlayerId, stockSymbol);

            if (result == 0) {
                return 0;
            } else {
                return result.intValue();
            }
        }catch (EmptyResultDataAccessException e){
            return 0;
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }

    }
    @Override
    public void insertGamePlayerStock(int gamePlayerId, String stockSymbol, int numberOfShares){
        String sql = "INSERT INTO game_player_stock (game_player_id, stock_symbol, number_of_shares) VALUES (?, ?, ?);";

        try{
            jdbcTemplate.update(sql, gamePlayerId, stockSymbol, numberOfShares);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    }
    @Override
    public void updatePlayerPortfolioBuy(int gamePlayerId, String stockSymbol, int purchasedShares){
        try{
            int currentShares = getNumberOfSharesForPlayerAndStock(gamePlayerId, stockSymbol);
            int updatedShares = currentShares + purchasedShares;

                String sql = "UPDATE game_player_stock SET number_of_shares = ? WHERE game_player_id = ? AND stock_symbol = ?;";
                jdbcTemplate.update(sql, updatedShares, gamePlayerId, stockSymbol);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    }
    @Override
    public void updatePlayerPortfolioSell(int gamePlayerId, String stockSymbol, int soldShares){
        try{
            int currentShares = getNumberOfSharesForPlayerAndStock(gamePlayerId, stockSymbol);
            if(currentShares >= soldShares){
                int remainingShares = currentShares - soldShares;
                String sql = "UPDATE game_player_stock SET number_of_shares = ? WHERE game_player_id = ? AND stock_symbol = ?;";
                jdbcTemplate.update(sql, remainingShares, gamePlayerId, stockSymbol);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    }

    public List<PortfolioStock> getPlayerPortfolioByPlayerId(int playerId){
        List<PortfolioStock> playerPortfolio = new ArrayList<>();
        String sql = "SELECT game_player_id, stock_symbol, number_of_shares FROM game_player_stock WHERE game_player_id = ?;";

        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, playerId);
            while(result.next()){
                PortfolioStock singleStock = mapRowToPortfolioStock(result);
                playerPortfolio.add(singleStock);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return playerPortfolio;
    }

    @Override
    public void removeStockFromPortfolio(int gamePlayerId, String stockSymbol){
        String sql = "DELETE FROM game_player_stock WHERE game_player_id = ? AND stock_symbol = ?;";

        try{
            jdbcTemplate.update(sql, gamePlayerId, stockSymbol);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    }
    @Override
    public GamePlayer getGamePlayerByUserIdAndDetailsId(int userId, int gameDetailsId){
        GamePlayer gamePlayer = null;
        String sql = "SELECT gp.game_player_id, gp.user_id, gp.cash, gp.is_host " +
                "FROM game_player gp " +
                "JOIN game_details_game_player ggp ON gp.game_player_id = ggp.game_player_id " +
                "JOIN game_details gd ON ggp.game_details_id = gd.game_details_id " +
                "JOIN users u ON gp.user_id = u.user_id " +
                "WHERE u.user_id = ? AND gd.game_details_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, gameDetailsId);
            if(results.next()){
                gamePlayer = mapRowToGamePlayer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return gamePlayer;
    }
    @Override
    public GamePlayer updateGamePlayer(GamePlayer gamePlayer){
        GamePlayer updateGamePlayer = null;
        String sql = "UPDATE game_player SET user_id = ?, cash = ?, is_host = ? WHERE game_player_id = ?;";

        try{
            int rowsAffected = jdbcTemplate.update(sql, gamePlayer.getUserId(), gamePlayer.getCash(), gamePlayer.isHost(), gamePlayer.getGamePlayerId());

            if(rowsAffected == 0){
                throw new DaoException("Zero rows affected, expected at least one row");
            } else{
                updateGamePlayer = getGamePlayerById(gamePlayer.getGamePlayerId());
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return updateGamePlayer;
    }

    @Override
    public GameDetails getGameByGameDetailsId(int id){
        GameDetails gameDetails = null;
        String sql = "SELECT game_details_id, player_count, game_status_id, difficulty_id, game_name, game_start, game_end, commission_fee FROM game_details WHERE game_details_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()){
                gameDetails = mapRowToGame(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return gameDetails;
    }
    @Override
    public GameDetails createGame (String gameName, LocalDateTime gameStart, LocalDateTime gameEnd, int difficultyId, double commissionFee, int playerCount, BigDecimal cash, String hostUsername, List<String> playerUsernames){
        GameDetails newGame = null;
        String sql = "INSERT INTO game_details (player_count, game_status_id, difficulty_id, game_name," +
                "game_start, game_end, commission_fee) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING game_details_id;";

        try {
            int newGameId = jdbcTemplate.queryForObject(sql, int.class, playerCount, STAGING_GAME_STATUS,
                    difficultyId, gameName, gameStart, gameEnd, commissionFee);

            newGame = getGameByGameDetailsId(newGameId);
            playerUsernames.add(hostUsername);
            addPlayersToGame(newGameId, playerUsernames, cash);
            List<GamePlayer> gamePlayers = getPlayersForGame(newGameId);


            GamePlayer hostPlayer = gamePlayers.get(gamePlayers.size() - 1);
            hostPlayer.setHost(true);
            String setHostSql = "UPDATE game_player SET is_host = true WHERE game_player_id = ?;";
            jdbcTemplate.update(setHostSql, hostPlayer.getGamePlayerId());

            for(GamePlayer player : gamePlayers){
                newGame.getPlayers().add(player);
            }

        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }

        return newGame;
    }
    @Override
    public void addPlayersToGame(int gameId, List<String> playerUsernames, BigDecimal startingBalance) {

        for (String username : playerUsernames) {
//
            try {
                User playerToAdd = userDao.getUserByUsername(username);
                GamePlayer gamePlayer = createPlayerForGame(playerToAdd.getId(), startingBalance, false);
                String insertPlayersSql = "INSERT INTO game_details_game_player (game_details_id, game_player_id) VALUES (?, ?)";
                jdbcTemplate.update(insertPlayersSql, gameId, gamePlayer.getGamePlayerId());
            }catch (CannotGetJdbcConnectionException e){
                throw new DaoException("Unable to connect to server or database", e);
            } catch (DataIntegrityViolationException e){
                throw new DaoException("Data integrity violation", e);
            }

//            }
        }
    }

    @Override
    public List<GamePlayer> getPlayersForGame(int gameDetailsId){
        List<GamePlayer> players = new ArrayList<>();
        String sql = "SELECT gp.game_player_id, gp.cash, gp.is_host, gp.user_id " +
                "FROM game_player gp " +
                "JOIN users u ON gp.user_id = u.user_id " +
                "JOIN game_details_game_player ggp ON ggp.game_player_id = gp.game_player_id " +
                "WHERE ggp.game_details_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameDetailsId);
            while (results.next()) {
                GamePlayer player = mapRowToGamePlayer(results);
                players.add(player);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return players;
    }
    @Override
    public GamePlayer getGamePlayerById (int id){
        GamePlayer player = null;
        String sql = "SELECT game_player_id, user_id, cash, is_host FROM game_player WHERE game_player_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()){
                player = mapRowToGamePlayer(results);
                player.setUserName(userDao.getUserById(player.getUserId()).getUsername());
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return player;
    }

    @Override
    public GamePlayer createPlayerForGame (int userId, BigDecimal cash, boolean isHost){
        int gamePlayerId;
        String sql = "INSERT INTO game_player (user_id, cash, is_host) " +
                    "VALUES (?, ?, ?) RETURNING game_player_id;";

        try{
            gamePlayerId = jdbcTemplate.queryForObject(sql, int.class, userId, cash, isHost);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }

        return getGamePlayerById(gamePlayerId);
    }

    private GamePlayer mapRowToGamePlayer(SqlRowSet rowSet){
        GamePlayer result = new GamePlayer();
        result.setGamePlayerId(rowSet.getInt("game_player_id"));
        result.setCash(rowSet.getBigDecimal("cash"));
        result.setHost(rowSet.getBoolean("is_host"));
        result.setUserId(rowSet.getInt("user_id"));
        result.setUserName(userDao.getUserById(result.getUserId()).getUsername());
        return result;
    }

    private PortfolioStock mapRowToPortfolioStock(SqlRowSet rowSet){
        PortfolioStock result = new PortfolioStock();
        result.setGamePlayerId(rowSet.getInt("game_player_id"));
        result.setNumberOfShares(rowSet.getInt("number_of_shares"));
        result.setStock(rowSet.getString("stock_symbol"));
        return result;
    }

    @Override
    public List<GameDetails> getGameListByUsername(String username){
        List<GameDetails> gameDetailsList = new ArrayList<>();
        String sql = "SELECT gd.game_details_id, gd.game_name, gd.game_start, gd.game_end, gd.player_count, gd.difficulty_id, gd.game_status_id, gd.commission_fee " +
                "FROM game_details gd " +
                "JOIN game_details_game_player ggd ON gd.game_details_id = ggd.game_details_id " +
                "JOIN game_player gp ON ggd.game_player_id = gp.game_player_id " +
                "JOIN users u ON gp.user_id = u.user_id " +
                "WHERE u.user_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userDao.getUserByUsername(username).getId());
            while (results.next()) {
                GameDetails game = mapRowToGame(results);
                game.setPlayers(getPlayersForGame(game.getGameDetailsId()));
                gameDetailsList.add(game);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return gameDetailsList;

    }
    private GameDetails mapRowToGame(SqlRowSet rowSet){
        GameDetails game = new GameDetails();
        game.setGameDetailsId(rowSet.getInt("game_details_id"));
        game.setGameName(rowSet.getString("game_name"));
        game.setDifficultyId(rowSet.getInt("difficulty_id"));
        game.setCommissionFee(rowSet.getBigDecimal("commission_fee").doubleValue());
        game.setGameEnd(rowSet.getTimestamp("game_end").toLocalDateTime());
        game.setGameStart(rowSet.getTimestamp("game_start").toLocalDateTime());
        game.setGameStatusId(rowSet.getInt("game_status_id"));
        game.setPlayerCount(rowSet.getInt("player_count"));
        return game;

    }

    @Override
    public List<GameDetails> getAllStagingOrInProgressGames(){
        List<GameDetails> gameList = new ArrayList<>();
        String sql = "SELECT * FROM game_details WHERE game_status_id = ? OR game_status_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, STAGING_GAME_STATUS, IN_PROGRESS_GAME_STATUS);
            while (results.next()) {
                GameDetails game = mapRowToGame(results);
                game.setPlayers(getPlayersForGame(game.getGameDetailsId()));
                gameList.add(game);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return gameList;
    }

    @Override
    public void updateGame (GameDetails game){
        String sql = "UPDATE game_details SET game_status_id = ?, difficulty_id = ?, game_name = ?, " +
                        "game_start = ?, game_end = ?, commission_fee = ?, player_count = ?;";

        try{
            int rowsAffected = jdbcTemplate.update(sql, game.getGameStatusId(), game.getDifficultyId(), game.getGameName(),
                    game.getGameStart(), game.getGameEnd(), game.getCommissionFee(), game.getPlayerCount());

            if(rowsAffected == 0){
                throw new DaoException("Zero rows affected, expected at least one row");
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    }


}
