package com.skillbarter.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String role = "USER"; // USER or ADMIN
    
    // Profile information
    private String location;
    private String bio;
    private Double rating = 0.0;
    private Integer totalRatings = 0;
    
    // Points system
    private Integer points = 0;
    
    // Skills
    @ElementCollection
    private List<String> skillsTaught = new ArrayList<>();
    
    @ElementCollection
    private List<String> skillsToLearn = new ArrayList<>();

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    
    public Integer getTotalRatings() { return totalRatings; }
    public void setTotalRatings(Integer totalRatings) { this.totalRatings = totalRatings; }
    
    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
    
    public List<String> getSkillsTaught() { return skillsTaught; }
    public void setSkillsTaught(List<String> skillsTaught) { this.skillsTaught = skillsTaught; }
    
    public List<String> getSkillsToLearn() { return skillsToLearn; }
    public void setSkillsToLearn(List<String> skillsToLearn) { this.skillsToLearn = skillsToLearn; }
    
    public void addPoints(int amount) {
        this.points += amount;
    }
    
    public boolean deductPoints(int amount) {
        if (this.points >= amount) {
            this.points -= amount;
            return true;
        }
        return false;
    }
    
    public void updateRating(double newRating) {
        if (totalRatings == 0) {
            this.rating = newRating;
            this.totalRatings = 1;
        } else {
            double currentTotal = this.rating * this.totalRatings;
            this.totalRatings++;
            this.rating = (currentTotal + newRating) / this.totalRatings;
        }
    }
}
