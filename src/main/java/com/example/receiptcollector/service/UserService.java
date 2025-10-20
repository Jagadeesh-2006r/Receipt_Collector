package com.example.receiptcollector.service;



import com.example.receiptcollector.model.User;
import com.example.receiptcollector.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        user.setJoinDate(new Timestamp(System.currentTimeMillis()));

        return userRepository.save(user);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
