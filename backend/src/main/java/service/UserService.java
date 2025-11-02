package com.skillbarter.backend.service;

import com.skillbarter.backend.model.User;
import com.skillbarter.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        // Example: Prevent duplicate emails
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists!");
        }
        return userRepository.save(user);
    }
}
