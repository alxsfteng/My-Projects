package com.techelevator.dao;

import com.techelevator.model.GameDetails;
import com.techelevator.model.authority.RegisterUserDto;
import com.techelevator.model.authority.User;

import java.util.List;

public interface UserDao {

    List<User> getUsersOtherThanLoggedInUser(int userId);

    User getUserById(int id);

    User getUserByUsername(String username);

    User createUser(RegisterUserDto user);
}
