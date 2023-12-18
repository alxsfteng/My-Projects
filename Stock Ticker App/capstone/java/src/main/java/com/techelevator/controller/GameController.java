package com.techelevator.controller;

import com.techelevator.exception.ServiceException;
import com.techelevator.model.GameDetails;
import com.techelevator.model.GamePlayer;
import com.techelevator.model.dtos.GameDetailsDto;
import com.techelevator.services.GameService;
import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<GameDetails> createGame(@RequestBody GameDetailsDto gameDetailsDto, Principal principal){
        GameDetails newGame;

        try {
            String gameName = gameDetailsDto.getGameName();
            LocalDateTime gameStart = gameDetailsDto.getGameStart();
            LocalDateTime gameEnd = gameDetailsDto.getGameEnd();
            int difficultyId = gameDetailsDto.getDifficultyId();
            double commissionFee = gameDetailsDto.getCommissionFee();
            int playerCount = gameDetailsDto.getPlayerCount();
            BigDecimal cash = gameDetailsDto.getCash();
            List<String> invitedFriends = gameDetailsDto.getInvitedFriends();


            newGame = gameService.createGame(gameName, gameStart, gameEnd, difficultyId, commissionFee,
                    playerCount, cash, invitedFriends, principal);
            return ResponseEntity.ok(newGame);
        }catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }

    @GetMapping("/gamelist")
    public List<GameDetails> listGamesByUsername(Principal principal){
        return gameService.getGameListByUsername(principal.getName());

    }

    @GetMapping("/{gameId}")
    public GameDetails getGame(@PathVariable("gameId") int id){
        return gameService.getGameById(id);
    }

    @GetMapping("/player/{userId}/{gameDetailsId}")
    public GamePlayer getPlayer(@PathVariable("userId") int userId, @PathVariable("gameDetailsId") int gameDetailsId){
        return gameService.getGamePlayerByUserIdAndGameDetailsId(userId, gameDetailsId);
    }
}
