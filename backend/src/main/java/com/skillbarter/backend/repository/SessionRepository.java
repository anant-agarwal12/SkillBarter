package com.skillbarter.backend.repository;

import com.skillbarter.backend.model.Session;
import com.skillbarter.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByTeacher(User teacher);
    List<Session> findByStudent(User student);
    List<Session> findByTeacherOrStudent(User teacher, User student);
    List<Session> findByStatus(String status);
    List<Session> findByScheduledTimeAfter(LocalDateTime time);
    List<Session> findByScheduledTimeBefore(LocalDateTime time);
}

