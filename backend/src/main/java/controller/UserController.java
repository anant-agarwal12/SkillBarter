package com.skillbarter.backend.controller;

import com.skillbarter.backend.model.User;
import com.skillbarter.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private com.skillbarter.backend.repository.UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id);
    }
    
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingOpt = userRepository.findById(id);
        if (existingOpt.isPresent()) {
            User existing = existingOpt.get();
            if (user.getUsername() != null) existing.setUsername(user.getUsername());
            if (user.getEmail() != null) existing.setEmail(user.getEmail());
            if (user.getLocation() != null) existing.setLocation(user.getLocation());
            if (user.getBio() != null) existing.setBio(user.getBio());
            if (user.getSkillsTaught() != null) existing.setSkillsTaught(user.getSkillsTaught());
            if (user.getSkillsToLearn() != null) existing.setSkillsToLearn(user.getSkillsToLearn());
            return userRepository.save(existing);
        }
        throw new RuntimeException("User not found");
    }
    
    @GetMapping("/{id}/points")
    public Integer getPoints(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getPoints).orElse(0);
    }
}
