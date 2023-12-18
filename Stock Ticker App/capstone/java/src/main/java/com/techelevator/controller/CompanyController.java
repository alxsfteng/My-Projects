package com.techelevator.controller;

import com.techelevator.model.Company;
import com.techelevator.model.Stock;
import com.techelevator.model.dtos.CompanyInfoDto;
import com.techelevator.model.dtos.StockInfoDto;
import com.techelevator.services.CompanyService;
import com.techelevator.services.StockService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
//TODO: change this in Company Service to dynamic query of specific companies. TSLA is test data
public class CompanyController {

    private CompanyService companyService;
    private StockService stockService;

    public CompanyController(CompanyService companyService, StockService stockService) {
        this.companyService = companyService;
        this.stockService = stockService;
    }

    @PostMapping("/companyInsertion/{stockSymbol}")
    public void insertCompanyInfo (@PathVariable("stockSymbol") String symbol){
        companyService.insertCompanyIntoDatabase(symbol);
    }

    @PostMapping("/stockInsertion/{stockSymbol}")
    public void insertStockInfo(@PathVariable("stockSymbol") String symbol) {
        stockService.insertStockInfo(symbol);
    }

    @PostMapping("/test")
    public void insertStockInfo() {
        stockService.updateStockHistory();
    }

    @GetMapping("/stockInfo/{stockSymbol}")
    public StockInfoDto getStock(@PathVariable("stockSymbol") String symbol){
        return stockService.getStockInfo(symbol);
    }
    @GetMapping("/companyInfo/{stockSymbol}")
    public CompanyInfoDto getCompany(@PathVariable("stockSymbol") String symbol){
        return companyService.getCompanyInfo(symbol);
    }
}
