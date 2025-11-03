package com.skillbarter.backend.repository;

import com.skillbarter.backend.model.Match;
import com.skillbarter.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByUser1(User user);
    List<Match> findByUser2(User user);
    List<Match> findByUser1OrUser2(User user1, User user2);
    List<Match> findByStatus(String status);
    Optional<Match> findByUser1AndUser2(User user1, User user2);
    List<Match> findByUser1AndStatus(User user, String status);
    List<Match> findByUser2AndStatus(User user, String status);
}

