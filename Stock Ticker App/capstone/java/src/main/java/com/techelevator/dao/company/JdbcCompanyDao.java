package com.techelevator.dao.company;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Company;
import com.techelevator.model.dtos.CompanyInfoDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcCompanyDao implements CompanyDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcCompanyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertCompanyInfo(Company company){
        String sql = "INSERT INTO company (stock_symbol, company_name, description, asset_type, exchange, currency, " +
                "country, sector, industry, address, eps, forward_pe, fifty_day_moving_average, fifty_two_week_high, fifty_two_week_low, " +
                "flavicon, company_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try{
             int result = jdbcTemplate.update(sql, company.getSymbol(), company.getName(), company.getDescription(), company.getAssetType(), company.getExchange(),
                     company.getCurrency(), company.getCountry(), company.getSector(), company.getIndustry(), company.getAddress(), company.getEPS(), company.getForwardPE(),
                     company.get_50DayMovingAverage(), company.get_52WeekHigh(), company.get_52WeekLow(), company.getFlavicon(), company.getCompanyUrl());

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
    public CompanyInfoDto getCompanyInfoByStockSymbol(String stockSymbol){
        CompanyInfoDto company = null;
        String sql = "SELECT stock_symbol, company_name, description, exchange, currency, country, sector, industry, address FROM company WHERE stock_symbol = ?;";

        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, stockSymbol);
            if (result.next()) {
                company = mapRowToCompanyDto(result);
            }
        }catch (CannotGetJdbcConnectionException e) {
                throw new DaoException("Unable to connect to server or database", e);
            }
        return company;
    }

    private CompanyInfoDto mapRowToCompanyDto(SqlRowSet rowSet) {
        CompanyInfoDto result = new CompanyInfoDto();
        result.symbol = rowSet.getString("stock_symbol");
        result.name = rowSet.getString("company_name");
        result.description = rowSet.getString("description");
        result.exchange = rowSet.getString("exchange");
        result.currency = rowSet.getString("currency");
        result.country = rowSet.getString("country");
        result.sector = rowSet.getString("sector");
        result.industry = rowSet.getString("industry");
        result.address = rowSet.getString("address");
        return result;
    }
}

