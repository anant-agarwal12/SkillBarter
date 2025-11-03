package com.skillbarter.backend.service;

import com.skillbarter.backend.model.*;
import com.skillbarter.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    
    @Autowired
    private SessionRepository sessionRepository;
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Transactional
    public Session createSession(Long skillId, Long studentId, LocalDateTime scheduledTime, String sessionLink) {
        Optional<Skill> skillOpt = skillRepository.findById(skillId);
        Optional<User> studentOpt = userRepository.findById(studentId);
        
        if (skillOpt.isEmpty() || studentOpt.isEmpty()) {
            throw new RuntimeException("Skill or student not found");
        }
        
        Skill skill = skillOpt.get();
        User student = studentOpt.get();
        User teacher = skill.getTeacher();
        
        // Check if student has enough points
        if (student.getPoints() < skill.getPointsRequired()) {
            throw new RuntimeException("Insufficient points");
        }
        
        // Deduct points from student
        student.deductPoints(skill.getPointsRequired());
        userRepository.save(student);
        
        // Create transaction record
        Transaction transaction = new Transaction(student, "SPENT", skill.getPointsRequired(), 
            "Enrolled in " + skill.getName());
        transactionRepository.save(transaction);
        
        // Create session
        Session session = new Session(skill, teacher, student, scheduledTime, sessionLink);
        session = sessionRepository.save(session);
        
        return session;
    }
    
    public List<Session> getSessionsByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(u -> sessionRepository.findByTeacherOrStudent(u, u)).orElse(List.of());
    }
    
    public List<Session> getUpcomingSessions(Long userId) {
        List<Session> sessions = getSessionsByUser(userId);
        LocalDateTime now = LocalDateTime.now();
        return sessions.stream()
            .filter(s -> s.getScheduledTime().isAfter(now) && s.getStatus().equals("SCHEDULED"))
            .toList();
    }
    
    @Transactional
    public Session completeSession(Long sessionId, double teacherRating, double studentRating) {
        Optional<Session> sessionOpt = sessionRepository.findById(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new RuntimeException("Session not found");
        }
        
        Session session = sessionOpt.get();
        session.setStatus("COMPLETED");
        
        // Calculate points earned (base on rating)
        int basePoints = session.getPointsCost();
        int bonusPoints = (int) (teacherRating * 10); // Bonus based on rating
        int totalPoints = basePoints + bonusPoints;
        
        // Give points to teacher
        User teacher = session.getTeacher();
        teacher.addPoints(totalPoints);
        userRepository.save(teacher);
        
        // Update teacher rating
        teacher.updateRating(teacherRating);
        userRepository.save(teacher);
        
        // Create transaction for teacher
        Transaction transaction = new Transaction(teacher, "EARNED", totalPoints, 
            "Completed session: " + session.getSkill().getName());
        transaction.setSession(session);
        transactionRepository.save(transaction);
        
        // Update skill rating
        session.getSkill().updateRating(teacherRating);
        skillRepository.save(session.getSkill());
        
        session.setPointsEarned(totalPoints);
        return sessionRepository.save(session);
    }
    
    public Session updateSessionLink(Long sessionId, String newLink) {
        Optional<Session> sessionOpt = sessionRepository.findById(sessionId);
        if (sessionOpt.isPresent()) {
            Session session = sessionOpt.get();
            session.setSessionLink(newLink);
            return sessionRepository.save(session);
        }
        throw new RuntimeException("Session not found");
    }
    
    public void cancelSession(Long sessionId) {
        Optional<Session> sessionOpt = sessionRepository.findById(sessionId);
        if (sessionOpt.isPresent()) {
            Session session = sessionOpt.get();
            session.setStatus("CANCELLED");
            
            // Refund points to student
            User student = session.getStudent();
            student.addPoints(session.getPointsCost());
            userRepository.save(student);
            
            sessionRepository.save(session);
        }
    }
}

