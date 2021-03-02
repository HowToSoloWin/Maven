package com.solomko.service;

import com.solomko.domain.User;
import com.solomko.exception.InvalidArgumentException;
import com.solomko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createOrGetUser(String userName){
        if (userName == null) {
            throw new InvalidArgumentException("Name cant be null");
        }
        User user = userRepository.getUser(userName);
        return user;
    }
}
