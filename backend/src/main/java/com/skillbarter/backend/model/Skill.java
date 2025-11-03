package com.skillbarter.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "skills")
public class Skill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private String category;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;
    
    private Integer pointsRequired;
    private Double rating = 0.0;
    private Integer totalRatings = 0;
    private Integer studentCount = 0;
    
    private String skillLevel; // BEGINNER, INTERMEDIATE, ADVANCED
    private String location;
    
    private LocalDateTime createdAt;
    private Boolean active = true;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public Skill() {}
    
    public Skill(String name, String description, String category, User teacher, 
                 Integer pointsRequired, String skillLevel, String location) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.teacher = teacher;
        this.pointsRequired = pointsRequired;
        this.skillLevel = skillLevel;
        this.location = location;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public User getTeacher() { return teacher; }
    public void setTeacher(User teacher) { this.teacher = teacher; }
    
    public Integer getPointsRequired() { return pointsRequired; }
    public void setPointsRequired(Integer pointsRequired) { this.pointsRequired = pointsRequired; }
    
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    
    public Integer getTotalRatings() { return totalRatings; }
    public void setTotalRatings(Integer totalRatings) { this.totalRatings = totalRatings; }
    
    public Integer getStudentCount() { return studentCount; }
    public void setStudentCount(Integer studentCount) { this.studentCount = studentCount; }
    
    public String getSkillLevel() { return skillLevel; }
    public void setSkillLevel(String skillLevel) { this.skillLevel = skillLevel; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
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
    
    public void incrementStudentCount() {
        this.studentCount++;
    }
}

