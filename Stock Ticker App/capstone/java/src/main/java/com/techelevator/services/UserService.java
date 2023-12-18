package com.techelevator.services;

import com.techelevator.model.GameDetails;
import com.techelevator.model.authority.UserDto;

import java.security.Principal;
import java.util.List;

public interface UserService {

    public List<UserDto> getUsers(Principal principal);
}
