package com.techelevator.dao.company;

import com.techelevator.model.Company;
import com.techelevator.model.dtos.CompanyInfoDto;

public interface CompanyDao {

    void insertCompanyInfo(Company company);
    CompanyInfoDto getCompanyInfoByStockSymbol(String stockSymbol);
}
