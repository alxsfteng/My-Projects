package com.techelevator.services;

import com.techelevator.dao.UserDao;
import com.techelevator.dao.game.GameDao;
import com.techelevator.dao.stock.StockDao;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.GamePlayer;
import com.techelevator.model.GlobalQuote;
import com.techelevator.model.Stock;
import com.techelevator.model.authority.User;
import com.techelevator.model.dtos.StockInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EnableAsync
@EnableScheduling
@Service
public class StockServiceImpl implements StockService {
    private static final String API_BASE_URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=";
    private static final String ALEXF_API_KEY = "&apikey=SKKPAUGETLTHD2NX";
    private static final String SCHOENLY_API_KEY = "&apikey=XE6IORLGMPWB78PQ";
    private static final String EXTRA_API_KEY = "&apikey=KV3BU396JXRZ762N";
    private RestTemplate restTemplate = new RestTemplate();
    private StockDao stockDao;
    private UserDao userDao;
    private GameDao gameDao;

    public StockServiceImpl() {
    }

    @Autowired
    public StockServiceImpl(StockDao stockDao, UserDao userDao, GameDao gameDao) {
        this.stockDao = stockDao;
        this.userDao = userDao;
        this.gameDao = gameDao;
    }

    @Override
    public void insertStockInfo(String symbol) {
        List<String> dowJonesCompanies = new ArrayList<>();
        dowJonesCompanies.add("AXP");
        dowJonesCompanies.add("AMGN");
        dowJonesCompanies.add("AAPL");
        dowJonesCompanies.add("BA");
        dowJonesCompanies.add("CSCO");
        dowJonesCompanies.add("CVX");
        dowJonesCompanies.add("GS");
        dowJonesCompanies.add("HD");
        dowJonesCompanies.add("IBM");
        dowJonesCompanies.add("INTC");
        dowJonesCompanies.add("JNJ");
        dowJonesCompanies.add("KO");
        dowJonesCompanies.add("JPM");
        dowJonesCompanies.add("MCD");
        dowJonesCompanies.add("MRK");
        dowJonesCompanies.add("MSFT");
        dowJonesCompanies.add("DIS");
        dowJonesCompanies.add("NKE");
        dowJonesCompanies.add("TRV");
        dowJonesCompanies.add("UNH");
        dowJonesCompanies.add("CRM");
        dowJonesCompanies.add("VZ");
        dowJonesCompanies.add("V");
        dowJonesCompanies.add("WMT");
        dowJonesCompanies.add("WBA");






        for(String company : dowJonesCompanies) {
            String apiUrl = API_BASE_URL + company + ALEXF_API_KEY;
            GlobalQuote globalQuote = restTemplate.getForObject(apiUrl, GlobalQuote.class);
            Stock stock = globalQuote.getStock();
            stockDao.insertStockInfo(stock);
        }
    }

    @Scheduled(cron = "0 0 9 * * *")
    @Override
    public void updateStockHistory() {
        List<String> dowJonesCompanies = new ArrayList<>();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        dowJonesCompanies.add("AXP");
        dowJonesCompanies.add("AMGN");
        dowJonesCompanies.add("AAPL");
        dowJonesCompanies.add("BA");
        dowJonesCompanies.add("CSCO");
        dowJonesCompanies.add("CVX");
        dowJonesCompanies.add("GS");
        dowJonesCompanies.add("HD");
        dowJonesCompanies.add("IBM");
        dowJonesCompanies.add("INTC");
        dowJonesCompanies.add("JNJ");
        dowJonesCompanies.add("KO");
        dowJonesCompanies.add("JPM");
        dowJonesCompanies.add("MCD");
        dowJonesCompanies.add("MRK");
        dowJonesCompanies.add("MSFT");
        dowJonesCompanies.add("DIS");
        dowJonesCompanies.add("NKE");
        dowJonesCompanies.add("TRV");
        dowJonesCompanies.add("UNH");
        dowJonesCompanies.add("CRM");
        dowJonesCompanies.add("VZ");
        dowJonesCompanies.add("V");
        dowJonesCompanies.add("WMT");
        dowJonesCompanies.add("WBA");



        for(String company : dowJonesCompanies) {
            String apiUrl = API_BASE_URL + company + EXTRA_API_KEY;
            GlobalQuote globalQuote = restTemplate.getForObject(apiUrl, GlobalQuote.class);
            Stock stock = globalQuote.getStock();
            stockDao.insertIntoStockPerformance(stock.getSymbol(), stock.getPrice(), dateTimeNow);
        }
    }

    @Override
    public BigDecimal getCurrentStockPriceBySymbol(String symbol){
        try {
            return stockDao.getCurrentStockPriceBySymbol(symbol);
        }catch (ServiceException e){
            throw new ServiceException("Error occurred " + e.getMessage());
        }

    }
    @Override
    public StockInfoDto getStockInfo(String symbol){
        try {
            return stockDao.getStockInfo(symbol);
        }catch (ServiceException e){
            throw new ServiceException("Error occurred " + e.getMessage());
        }
    }




//    public StockInfoDto mapStockToDTO
}
