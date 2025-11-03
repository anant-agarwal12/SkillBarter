package com.skillbarter.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
public class Session {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;
    
    private LocalDateTime scheduledTime;
    private String sessionLink; // Zoom or other meeting link
    private String status; // SCHEDULED, COMPLETED, CANCELLED
    
    private String notes;
    private Integer pointsCost;
    private Integer pointsEarned;
    
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = "SCHEDULED";
        }
    }
    
    public Session() {}
    
    public Session(Skill skill, User teacher, User student, LocalDateTime scheduledTime, String sessionLink) {
        this.skill = skill;
        this.teacher = teacher;
        this.student = student;
        this.scheduledTime = scheduledTime;
        this.sessionLink = sessionLink;
        this.pointsCost = skill != null ? skill.getPointsRequired() : 0;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }
    
    public User getTeacher() { return teacher; }
    public void setTeacher(User teacher) { this.teacher = teacher; }
    
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    
    public LocalDateTime getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(LocalDateTime scheduledTime) { this.scheduledTime = scheduledTime; }
    
    public String getSessionLink() { return sessionLink; }
    public void setSessionLink(String sessionLink) { this.sessionLink = sessionLink; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public Integer getPointsCost() { return pointsCost; }
    public void setPointsCost(Integer pointsCost) { this.pointsCost = pointsCost; }
    
    public Integer getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

