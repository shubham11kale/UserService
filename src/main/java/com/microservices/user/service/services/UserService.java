package com.microservices.user.service.services;

import com.microservices.user.service.models.User;

import java.util.List;

public interface UserService {
    //Create User
    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get Single user of userId
    User getUser(String userId);
}
