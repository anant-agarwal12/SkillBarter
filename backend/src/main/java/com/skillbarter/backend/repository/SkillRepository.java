package com.skillbarter.backend.repository;

import com.skillbarter.backend.model.Skill;
import com.skillbarter.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByTeacher(User teacher);
    List<Skill> findByCategory(String category);
    List<Skill> findBySkillLevel(String skillLevel);
    List<Skill> findByLocation(String location);
    List<Skill> findByActiveTrue();
    List<Skill> findByNameContainingIgnoreCase(String name);
    List<Skill> findByTeacherAndActiveTrue(User teacher);
}

