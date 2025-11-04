package ui.core;

import ui.models.User;
import ui.core.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple API client
 */
public class APIClient {
    private static final String BASE_URL = "http://localhost:8081/api";
    
    // Authentication methods
    public static User login(String username, String password) throws Exception {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        return parseUser(postRequest("/users/login", credentials));
    }
    
    public static User register(String username, String email, String password) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("email", email);
        data.put("password", password);
        data.put("role", "USER");
        return parseUser(postRequest("/users/register", data));
    }
    
    public static User adminLogin(String username, String password) throws Exception {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        return parseUser(postRequest("/users/admin/login", credentials));
    }
    
    public static User adminRegister(String username, String email, String password) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("email", email);
        data.put("password", password);
        return parseUser(postRequest("/users/admin/register", data));
    }
    
    // Skill methods
    public static List<Map<String, Object>> getAllSkills() throws Exception {
        return getRequestArray("/skills");
    }
    
    // User methods
    public static Integer getUserPoints(Long userId) throws Exception {
        Map<String, Object> response = getRequest("/users/" + userId + "/points");
        return response != null ? (Integer) response.get("points") : 0;
    }
    
    public static User getUserById(Long userId) throws Exception {
        return parseUser(getRequest("/users/" + userId));
    }
    
    // Session methods
    public static List<Map<String, Object>> getUpcomingSessions(Long userId) throws Exception {
        return getRequestArray("/sessions/user/" + userId + "/upcoming");
    }
    
    public static List<Map<String, Object>> getPastSessions(Long userId) throws Exception {
        return getRequestArray("/sessions/user/" + userId + "/past");
    }
    
    // Helper methods
    private static User parseUser(Map<String, Object> data) {
        if (data == null) return null;
        User user = new User();
        
        try {
            if (data.get("id") != null) {
                Object idObj = data.get("id");
                if (idObj instanceof Number) {
                    user.setId(((Number) idObj).longValue());
                } else {
                    user.setId(Long.parseLong(idObj.toString()));
                }
            }
            
            if (data.get("username") != null) user.setUsername(data.get("username").toString());
            if (data.get("email") != null) user.setEmail(data.get("email").toString());
            if (data.get("role") != null) user.setRole(data.get("role").toString());
            if (data.get("location") != null) user.setLocation(data.get("location").toString());
            if (data.get("bio") != null) user.setBio(data.get("bio").toString());
            
            if (data.get("rating") != null) {
                Object ratingObj = data.get("rating");
                if (ratingObj instanceof Number) {
                    user.setRating(((Number) ratingObj).doubleValue());
                } else {
                    user.setRating(Double.parseDouble(ratingObj.toString()));
                }
            }
            
            if (data.get("totalRatings") != null) {
                Object totalRatingsObj = data.get("totalRatings");
                if (totalRatingsObj instanceof Number) {
                    user.setTotalRatings(((Number) totalRatingsObj).intValue());
                } else {
                    user.setTotalRatings(Integer.parseInt(totalRatingsObj.toString()));
                }
            }
            
            if (data.get("points") != null) {
                Object pointsObj = data.get("points");
                if (pointsObj instanceof Number) {
                    user.setPoints(((Number) pointsObj).intValue());
                } else {
                    user.setPoints(Integer.parseInt(pointsObj.toString()));
                }
            }
            
            // Handle array fields - skip if they're arrays, we'll set empty lists
            if (data.get("skillsTaught") instanceof List) {
                @SuppressWarnings("unchecked")
                List<String> skillsTaught = (List<String>) data.get("skillsTaught");
                user.setSkillsTaught(skillsTaught != null ? skillsTaught : new java.util.ArrayList<>());
            }
            
            if (data.get("skillsToLearn") instanceof List) {
                @SuppressWarnings("unchecked")
                List<String> skillsToLearn = (List<String>) data.get("skillsToLearn");
                user.setSkillsToLearn(skillsToLearn != null ? skillsToLearn : new java.util.ArrayList<>());
            }
        } catch (Exception e) {
            // Log error but continue with partial user data
            System.err.println("Error parsing user data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return user;
    }
    
    private static Map<String, Object> postRequest(String endpoint, Map<String, String> data) throws Exception {
        URL url = new URI(BASE_URL + endpoint).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        
        // Build JSON string
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (!first) json.append(",");
            json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
            first = false;
        }
        json.append("}");
        
        // Send request
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return JSONParser.parse(response.toString());
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            throw new RuntimeException("API Error: " + response.toString());
        }
    }
    
    private static Map<String, Object> getRequest(String endpoint) throws Exception {
        URL url = new URI(BASE_URL + endpoint).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return JSONParser.parse(response.toString());
        }
        return null;
    }
    
    private static List<Map<String, Object>> getRequestArray(String endpoint) throws Exception {
        URL url = new URI(BASE_URL + endpoint).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return JSONParser.parseArray(response.toString());
        }
        return new ArrayList<>();
    }
}
