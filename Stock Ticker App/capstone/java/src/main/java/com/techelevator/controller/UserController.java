package com.techelevator.controller;


import com.techelevator.exception.ServiceException;
import com.techelevator.model.GameDetails;
import com.techelevator.model.authority.UserDto;
import com.techelevator.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> listUsers(Principal principal){
        try{
            List<UserDto> users = userService.getUsers(principal);
            return users;
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }


}
