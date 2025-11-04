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
        // Prevent duplicate emails
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists!");
        }
        // Prevent duplicate usernames
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists!");
        }
        // Set default role if not specified
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }
    
    public User login(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            // Try email login
            user = userRepository.findByEmailAndPassword(username, password);
        }
        return user;
    }
    
    public User register(String username, String email, String password, String role) {
        User user = new User(username, email, password);
        if (role != null && !role.isEmpty()) {
            user.setRole(role);
        }
        return createUser(user);
    }
}

