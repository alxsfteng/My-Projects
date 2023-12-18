package com.techelevator.controller;

import com.techelevator.dao.trade.TradeDao;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.Trade;
import com.techelevator.model.dtos.TradeDto;
import com.techelevator.model.dtos.TradeResponseDto;
import com.techelevator.services.TradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/trade")
public class TradeController {
    private TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<TradeResponseDto> createTrade(@RequestBody TradeDto tradeDto, Principal principal){
        try{
            int gameDetailsId = tradeDto.getGameDetailsId();
            String stockSymbol = tradeDto.getStockSymbol();
            int tradeTypeId = tradeDto.getTradeTypeId();
            int numberOfShares = tradeDto.getNumberOfShares();


            TradeResponseDto newTrade = tradeService.createTrade(gameDetailsId, stockSymbol, tradeTypeId, numberOfShares, principal);

            return ResponseEntity.ok(newTrade);
        }catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }
}
