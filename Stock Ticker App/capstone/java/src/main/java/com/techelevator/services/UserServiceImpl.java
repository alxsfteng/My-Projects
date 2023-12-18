package com.techelevator.services;

import com.techelevator.dao.UserDao;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.GameDetails;
import com.techelevator.model.authority.User;
import com.techelevator.model.authority.UserDto;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> getUsers(Principal principal){
        List<UserDto> users = new ArrayList<>();
        User loggedInUser = userDao.getUserByUsername(principal.getName());

        try{
            List<User> userForDto = userDao.getUsersOtherThanLoggedInUser(loggedInUser.getId());
            for(User user: userForDto){
                UserDto userDto = new UserDto(user.getUsername(), user.getId());
                users.add(userDto);
            }
        }catch (ServiceException e){
            throw new ServiceException("Error occurred " + e.getMessage());
        }

        return users;
    }

}
