package com.techelevator.services;

import com.techelevator.dao.company.CompanyDao;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.Company;
import com.techelevator.model.GlobalQuote;
import com.techelevator.model.Stock;
import com.techelevator.model.dtos.CompanyInfoDto;
import com.techelevator.model.dtos.StockInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService{

    private static final String API_URL = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=";
    private static final String API_KEY = "2T3K6J25XU1IUIHC";
    private static final String SCHOENLY_API_KEY = "XE6IORLGMPWB78PQ";
    private static final String EXTRA_API_KEY = "KV3BU396JXRZ762N";
    private RestTemplate restTemplate = new RestTemplate();
    private CompanyDao companyDao;

    @Autowired
    public CompanyServiceImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public CompanyServiceImpl() {
    }

    @Override
     public void insertCompanyIntoDatabase(String symbol) {
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
            String apiUrl = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + company + "&apikey=" + SCHOENLY_API_KEY;
            Company companyInfo = restTemplate.getForObject(apiUrl, Company.class);
            companyDao.insertCompanyInfo(companyInfo);
        }
    }

    @Override
    public CompanyInfoDto getCompanyInfo(String symbol){
        try {
            return companyDao.getCompanyInfoByStockSymbol(symbol);
        }catch (ServiceException e){
            throw new ServiceException("Error occurred " + e.getMessage());
        }
    }


}
