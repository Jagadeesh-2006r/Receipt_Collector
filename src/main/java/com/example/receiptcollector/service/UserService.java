package com.example.receiptcollector.service;

<<<<<<< HEAD


import com.example.receiptcollector.model.User;
=======
import com.example.receiptcollector.model.Receipt;
import com.example.receiptcollector.model.User;
import com.example.receiptcollector.repository.ReceiptRepository;
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)
import com.example.receiptcollector.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
<<<<<<< HEAD
=======
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)

@Service
public class UserService {
    @Autowired
<<<<<<< HEAD
    private UserRepository userRepository;
=======
    public UserRepository userRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Transactional
    public void deleteUserById(Integer id) {
        List<Receipt> userReceipts = receiptRepository.findByUserUserId(id);
        if (!userReceipts.isEmpty()) {
            receiptRepository.deleteByUserUserId(id);
        }
        userRepository.deleteById(id);
    }
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)

    public User addUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        user.setJoinDate(new Timestamp(System.currentTimeMillis()));
<<<<<<< HEAD

=======
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)
        return userRepository.save(user);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }
<<<<<<< HEAD
=======

    // -- Add this for PUT/UPDATE
    public void updateUserById(Integer id, User user) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            User existingUser = optUser.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setContactNo(user.getContactNo());
            // DO NOT SET joinDate: Always keep original
            userRepository.save(existingUser);
        }
    }

    // -- Add this for DELETE

>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)
}
