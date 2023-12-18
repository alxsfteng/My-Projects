package com.techelevator.services;

import com.techelevator.model.Trade;
import com.techelevator.model.dtos.TradeResponseDto;

import java.security.Principal;

public interface TradeService {
    TradeResponseDto createTrade(int gameDetailsId, String stockSymbol, int tradeTypeId, int numberOfShares, Principal principal);
}
