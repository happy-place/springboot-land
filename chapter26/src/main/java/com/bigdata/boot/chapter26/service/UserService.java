package com.bigdata.boot.chapter26.service;

import com.bigdata.boot.chapter26.jpa.UserRepository;
import com.bigdata.boot.chapter26.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
