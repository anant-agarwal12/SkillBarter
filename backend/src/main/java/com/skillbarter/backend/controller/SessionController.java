package com.skillbarter.backend.controller;

import com.skillbarter.backend.model.Session;
import com.skillbarter.backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "*")
public class SessionController {
    
    @Autowired
    private SessionService sessionService;
    
    @PostMapping
    public Session createSession(@RequestParam Long skillId, 
                                  @RequestParam Long studentId,
                                  @RequestParam String scheduledTime,
                                  @RequestParam String sessionLink) {
        LocalDateTime time = LocalDateTime.parse(scheduledTime);
        return sessionService.createSession(skillId, studentId, time, sessionLink);
    }
    
    @GetMapping("/user/{userId}")
    public List<Session> getSessionsByUser(@PathVariable Long userId) {
        return sessionService.getSessionsByUser(userId);
    }
    
    @GetMapping("/user/{userId}/upcoming")
    public List<Session> getUpcomingSessions(@PathVariable Long userId) {
        return sessionService.getUpcomingSessions(userId);
    }
    
    @PostMapping("/{sessionId}/complete")
    public Session completeSession(@PathVariable Long sessionId,
                                   @RequestParam Double teacherRating,
                                   @RequestParam Double studentRating) {
        return sessionService.completeSession(sessionId, teacherRating, studentRating);
    }
    
    @PutMapping("/{sessionId}/link")
    public Session updateSessionLink(@PathVariable Long sessionId, @RequestParam String link) {
        return sessionService.updateSessionLink(sessionId, link);
    }
    
    @PostMapping("/{sessionId}/cancel")
    public void cancelSession(@PathVariable Long sessionId) {
        sessionService.cancelSession(sessionId);
    }
}

