package com.skillbarter.backend.controller;

import com.skillbarter.backend.model.Match;
import com.skillbarter.backend.service.MatchmakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matchmaking")
@CrossOrigin(origins = "*")
public class MatchmakingController {
    
    @Autowired
    private MatchmakingService matchmakingService;
    
    @GetMapping("/find")
    public List<Match> findMatches(@RequestParam Long userId,
                                   @RequestParam(required = false) String skillLevel,
                                   @RequestParam(required = false) String location,
                                   @RequestParam(required = false) Double minRating) {
        return matchmakingService.findMatches(userId, skillLevel, location, minRating);
    }
    
    @GetMapping("/user/{userId}")
    public List<Match> getMyMatches(@PathVariable Long userId) {
        return matchmakingService.getMyMatches(userId);
    }
    
    @PostMapping("/{matchId}/accept")
    public Match acceptMatch(@PathVariable Long matchId) {
        return matchmakingService.acceptMatch(matchId);
    }
    
    @PostMapping("/{matchId}/reject")
    public void rejectMatch(@PathVariable Long matchId) {
        matchmakingService.rejectMatch(matchId);
    }
}

