package com.techelevator.services;

import com.techelevator.model.Company;
import com.techelevator.model.dtos.CompanyInfoDto;

public interface CompanyService {
  //  Company getCompany();
      void insertCompanyIntoDatabase(String symbol);
    CompanyInfoDto getCompanyInfo(String symbol);
//    Company getSymbol();
//    Company getAssetType();
//    Company getDescription();

}
