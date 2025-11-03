package com.skillbarter.backend.service;

import com.skillbarter.backend.model.*;
import com.skillbarter.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchmakingService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MatchRepository matchRepository;
    
    @Autowired
    private SkillRepository skillRepository;
    
    public List<Match> findMatches(Long userId, String skillLevel, String location, Double minRating) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return List.of();
        }
        
        User user = userOpt.get();
        List<User> allUsers = userRepository.findAll();
        
        List<Match> matches = new ArrayList<>();
        
        for (User otherUser : allUsers) {
            if (otherUser.getId().equals(userId)) continue;
            
            // Check if users can match (one teaches what other wants to learn)
            for (String skillIWant : user.getSkillsToLearn()) {
                if (otherUser.getSkillsTaught().contains(skillIWant)) {
                    // Check if other user wants to learn what I can teach
                    for (String skillICanTeach : user.getSkillsTaught()) {
                        if (otherUser.getSkillsToLearn().contains(skillICanTeach)) {
                            // Calculate match score
                            double score = calculateMatchScore(user, otherUser, skillLevel, location, minRating);
                            
                            // Apply filters
                            if (matchesFilters(otherUser, skillLevel, location, minRating)) {
                                Match match = new Match(user, otherUser, skillICanTeach, skillIWant, score);
                                matches.add(match);
                            }
                        }
                    }
                }
            }
        }
        
        // Sort by match score (descending)
        matches.sort((m1, m2) -> Double.compare(m2.getMatchScore(), m1.getMatchScore()));
        
        // Save matches
        matches.forEach(match -> {
            Optional<Match> existing = matchRepository.findByUser1AndUser2(match.getUser1(), match.getUser2());
            if (existing.isEmpty()) {
                matchRepository.save(match);
            }
        });
        
        return matches.stream().limit(20).collect(Collectors.toList());
    }
    
    private double calculateMatchScore(User user1, User user2, String skillLevel, String location, Double minRating) {
        double score = 0.0;
        
        // Rating factor (0-4 points)
        score += user2.getRating() * 0.8;
        
        // Location match (0-2 points)
        if (user1.getLocation() != null && user2.getLocation() != null) {
            if (user1.getLocation().equalsIgnoreCase(user2.getLocation())) {
                score += 2.0;
            } else if (user1.getLocation().contains(user2.getLocation().split(",")[0]) || 
                      user2.getLocation().contains(user1.getLocation().split(",")[0])) {
                score += 1.0;
            }
        }
        
        // Skills match (0-2 points)
        long matchingSkills = user1.getSkillsToLearn().stream()
            .filter(user2.getSkillsTaught()::contains)
            .count();
        score += Math.min(matchingSkills * 0.5, 2.0);
        
        // Rating count factor (0-1 point)
        if (user2.getTotalRatings() > 10) {
            score += 1.0;
        } else if (user2.getTotalRatings() > 5) {
            score += 0.5;
        }
        
        // Normalize to 0-10 scale
        return Math.min(score, 10.0);
    }
    
    private boolean matchesFilters(User user, String skillLevel, String location, Double minRating) {
        if (minRating != null && user.getRating() < minRating) {
            return false;
        }
        
        if (location != null && !location.isEmpty()) {
            if (user.getLocation() == null || !user.getLocation().toLowerCase().contains(location.toLowerCase())) {
                return false;
            }
        }
        
        // Skill level filter would need to be stored on skills, not users
        // For now, we'll skip this check
        
        return true;
    }
    
    public List<Match> getMyMatches(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(u -> matchRepository.findByUser1OrUser2(u, u)).orElse(List.of());
    }
    
    public Match acceptMatch(Long matchId) {
        Optional<Match> matchOpt = matchRepository.findById(matchId);
        if (matchOpt.isPresent()) {
            Match match = matchOpt.get();
            match.setStatus("ACCEPTED");
            match.setMatchedAt(java.time.LocalDateTime.now());
            return matchRepository.save(match);
        }
        throw new RuntimeException("Match not found");
    }
    
    public void rejectMatch(Long matchId) {
        Optional<Match> matchOpt = matchRepository.findById(matchId);
        if (matchOpt.isPresent()) {
            Match match = matchOpt.get();
            match.setStatus("REJECTED");
            matchRepository.save(match);
        }
    }
}

