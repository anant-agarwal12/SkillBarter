package com.skillbarter.backend.controller;

import com.skillbarter.backend.model.Skill;
import com.skillbarter.backend.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "*")
public class SkillController {
    
    @Autowired
    private SkillService skillService;
    
    @PostMapping("/teacher/{teacherId}")
    public Skill createSkill(@PathVariable Long teacherId, @RequestBody Skill skill) {
        return skillService.createSkill(skill, teacherId);
    }
    
    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }
    
    @GetMapping("/{id}")
    public Optional<Skill> getSkillById(@PathVariable Long id) {
        return skillService.getSkillById(id);
    }
    
    @GetMapping("/category/{category}")
    public List<Skill> getSkillsByCategory(@PathVariable String category) {
        return skillService.getSkillsByCategory(category);
    }
    
    @GetMapping("/level/{level}")
    public List<Skill> getSkillsByLevel(@PathVariable String level) {
        return skillService.getSkillsByLevel(level);
    }
    
    @GetMapping("/location/{location}")
    public List<Skill> getSkillsByLocation(@PathVariable String location) {
        return skillService.getSkillsByLocation(location);
    }
    
    @GetMapping("/search")
    public List<Skill> searchSkills(@RequestParam String query) {
        return skillService.searchSkills(query);
    }
    
    @GetMapping("/teacher/{teacherId}")
    public List<Skill> getSkillsByTeacher(@PathVariable Long teacherId) {
        return skillService.getSkillsByTeacher(teacherId);
    }
    
    @PutMapping("/{id}")
    public Skill updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
        return skillService.updateSkill(id, skill);
    }
    
    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
    }
    
    @PostMapping("/{id}/rate")
    public void rateSkill(@PathVariable Long id, @RequestParam Double rating) {
        skillService.rateSkill(id, rating);
    }
}

