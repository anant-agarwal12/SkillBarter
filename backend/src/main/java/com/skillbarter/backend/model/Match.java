package com.skillbarter.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;
    
    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;
    
    private String skillOffered; // Skill user1 can teach
    private String skillWanted;  // Skill user1 wants to learn
    
    private Double matchScore; // AI calculated compatibility score
    private String status; // PENDING, ACCEPTED, REJECTED
    
    private LocalDateTime createdAt;
    private LocalDateTime matchedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = "PENDING";
        }
    }
    
    public Match() {}
    
    public Match(User user1, User user2, String skillOffered, String skillWanted, Double matchScore) {
        this.user1 = user1;
        this.user2 = user2;
        this.skillOffered = skillOffered;
        this.skillWanted = skillWanted;
        this.matchScore = matchScore;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser1() { return user1; }
    public void setUser1(User user1) { this.user1 = user1; }
    
    public User getUser2() { return user2; }
    public void setUser2(User user2) { this.user2 = user2; }
    
    public String getSkillOffered() { return skillOffered; }
    public void setSkillOffered(String skillOffered) { this.skillOffered = skillOffered; }
    
    public String getSkillWanted() { return skillWanted; }
    public void setSkillWanted(String skillWanted) { this.skillWanted = skillWanted; }
    
    public Double getMatchScore() { return matchScore; }
    public void setMatchScore(Double matchScore) { this.matchScore = matchScore; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getMatchedAt() { return matchedAt; }
    public void setMatchedAt(LocalDateTime matchedAt) { this.matchedAt = matchedAt; }
}

