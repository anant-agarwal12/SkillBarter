package ui.models;

/**
 * User model
 */
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String location;
    private String bio;
    private Double rating = 0.0;
    private Integer totalRatings = 0;
    private Integer points = 0;
    private java.util.List<String> skillsTaught;
    private java.util.List<String> skillsToLearn;
    
    public User() {
        this.skillsTaught = new java.util.ArrayList<>();
        this.skillsToLearn = new java.util.ArrayList<>();
    }
    
    public User(Long id, String username, String password, String email) {
        this();
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = "USER";
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    
    public Integer getTotalRatings() { return totalRatings; }
    public void setTotalRatings(Integer totalRatings) { this.totalRatings = totalRatings; }
    
    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
    
    public java.util.List<String> getSkillsTaught() { return skillsTaught; }
    public void setSkillsTaught(java.util.List<String> skillsTaught) { this.skillsTaught = skillsTaught; }
    
    public java.util.List<String> getSkillsToLearn() { return skillsToLearn; }
    public void setSkillsToLearn(java.util.List<String> skillsToLearn) { this.skillsToLearn = skillsToLearn; }
}
