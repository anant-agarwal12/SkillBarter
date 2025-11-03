package com.skillbarter.backend.service;

import com.skillbarter.backend.model.Skill;
import com.skillbarter.backend.model.User;
import com.skillbarter.backend.repository.SkillRepository;
import com.skillbarter.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Skill createSkill(Skill skill, Long teacherId) {
        Optional<User> teacherOpt = userRepository.findById(teacherId);
        if (teacherOpt.isPresent()) {
            skill.setTeacher(teacherOpt.get());
            return skillRepository.save(skill);
        }
        throw new RuntimeException("Teacher not found");
    }
    
    public List<Skill> getAllSkills() {
        return skillRepository.findByActiveTrue();
    }
    
    public Optional<Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }
    
    public List<Skill> getSkillsByCategory(String category) {
        return skillRepository.findByCategory(category);
    }
    
    public List<Skill> getSkillsByLevel(String level) {
        return skillRepository.findBySkillLevel(level);
    }
    
    public List<Skill> getSkillsByLocation(String location) {
        return skillRepository.findByLocation(location);
    }
    
    public List<Skill> searchSkills(String searchTerm) {
        return skillRepository.findByNameContainingIgnoreCase(searchTerm);
    }
    
    public List<Skill> getSkillsByTeacher(Long teacherId) {
        Optional<User> teacher = userRepository.findById(teacherId);
        return teacher.map(skillRepository::findByTeacher).orElse(List.of());
    }
    
    public Skill updateSkill(Long id, Skill updatedSkill) {
        Optional<Skill> skillOpt = skillRepository.findById(id);
        if (skillOpt.isPresent()) {
            Skill skill = skillOpt.get();
            skill.setName(updatedSkill.getName());
            skill.setDescription(updatedSkill.getDescription());
            skill.setCategory(updatedSkill.getCategory());
            skill.setPointsRequired(updatedSkill.getPointsRequired());
            skill.setSkillLevel(updatedSkill.getSkillLevel());
            skill.setLocation(updatedSkill.getLocation());
            return skillRepository.save(skill);
        }
        throw new RuntimeException("Skill not found");
    }
    
    public void deleteSkill(Long id) {
        Optional<Skill> skill = skillRepository.findById(id);
        if (skill.isPresent()) {
            skill.get().setActive(false);
            skillRepository.save(skill.get());
        }
    }
    
    public void rateSkill(Long skillId, double rating) {
        Optional<Skill> skillOpt = skillRepository.findById(skillId);
        if (skillOpt.isPresent()) {
            Skill skill = skillOpt.get();
            skill.updateRating(rating);
            skillRepository.save(skill);
        }
    }
}

