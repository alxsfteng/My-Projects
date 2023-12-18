package com.techelevator.controller;

import com.techelevator.exception.ServiceException;
import com.techelevator.model.dtos.PortfolioSummaryDto;
import com.techelevator.services.GameService;
import com.techelevator.services.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
    private GameService gameService;

    public PortfolioController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/shares/{gamePlayerId}/{stockSymbol}")
    public ResponseEntity<Integer> getNumberOfShares(@PathVariable("gamePlayerId") int gamePlayerId, @PathVariable("stockSymbol") String stockSymbol, Principal principal){
        try{
            int numberOfShares = gameService.getNumberOfSharesForPlayer(gamePlayerId, stockSymbol, principal);
            return ResponseEntity.ok(numberOfShares);
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }
    @GetMapping("/cash/{gamePlayerId}")
    public ResponseEntity<BigDecimal> getCash(@PathVariable("gamePlayerId") int gamePlayerId, Principal principal){
        try{
            BigDecimal cash = gameService.getCashForPlayer(gamePlayerId, principal);
            return ResponseEntity.ok(cash);
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }
    @GetMapping("/{gamePlayerId}")
    public ResponseEntity<List<PortfolioSummaryDto>> getPortfolio(@PathVariable("gamePlayerId") int gamePlayerId, Principal principal){
        try{
            List<PortfolioSummaryDto> portfolio = gameService.getPortfolio(gamePlayerId, principal);
            return ResponseEntity.ok(portfolio);
        }catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }

    @GetMapping("/worth/{gamePlayerId}")
    public ResponseEntity<BigDecimal> getportfolioWorth(@PathVariable("gamePlayerId") int gamePlayerId){
        try{
            BigDecimal portfolioWorth = gameService.getPortfolioNetWorth(gamePlayerId);
            return ResponseEntity.ok(portfolioWorth);
        }catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }


}
