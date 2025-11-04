package com.skillbarter.backend.controller;

import com.skillbarter.backend.model.User;
import com.skillbarter.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    
    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        User user = userService.login(username, password);
        if (user == null) {
            throw new RuntimeException("Invalid username or password!");
        }
        // Don't return password
        user.setPassword(null);
        return user;
    }
    
    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String email = data.get("email");
        String password = data.get("password");
        String role = data.getOrDefault("role", "USER");
        
        User user = userService.register(username, email, password, role);
        // Don't return password
        user.setPassword(null);
        return user;
    }
    
    @PostMapping("/admin/login")
    public User adminLogin(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        User user = userService.login(username, password);
        if (user == null || !"ADMIN".equals(user.getRole())) {
            throw new RuntimeException("Invalid admin credentials!");
        }
        // Don't return password
        user.setPassword(null);
        return user;
    }
    
    @PostMapping("/admin/register")
    public User adminRegister(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String email = data.get("email");
        String password = data.get("password");
        
        User user = userService.register(username, email, password, "ADMIN");
        // Don't return password
        user.setPassword(null);
        return user;
    }
    
    @GetMapping("/username/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(null); // Don't return password
        }
        return Optional.ofNullable(user);
    }
}

