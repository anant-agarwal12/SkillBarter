package ui.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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
    
    public static List<Map<String, Object>> getAllSkills() throws Exception {
        return getRequestArray("/skills");
    }
    
    public static Integer getUserPoints(Long userId) throws Exception {
        Map<String, Object> response = getRequest("/users/" + userId + "/points");
        return response != null ? (Integer) response.get("points") : 0;
    }
    
    public static List<Map<String, Object>> getUpcomingSessions(Long userId) throws Exception {
        return getRequestArray("/sessions/user/" + userId);
    }
    
    private static Map<String, Object> getRequest(String endpoint) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
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
            return parseJson(response.toString());
        }
        return null;
    }
    
    private static List<Map<String, Object>> getRequestArray(String endpoint) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
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
            return parseJsonArray(response.toString());
        }
        return new ArrayList<>();
    }
    
    private static Map<String, Object> parseJson(String json) {
        Map<String, Object> map = new HashMap<>();
        // Simplified JSON parsing
        try {
            json = json.trim();
            if (json.startsWith("{") && json.endsWith("}")) {
                json = json.substring(1, json.length() - 1);
                String[] pairs = json.split(",");
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim().replaceAll("\"", "");
                        String value = keyValue[1].trim().replaceAll("\"", "");
                        map.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            // Simple parsing - handle errors gracefully
        }
        return map;
    }
    
    private static List<Map<String, Object>> parseJsonArray(String json) {
        List<Map<String, Object>> list = new ArrayList<>();
        // Simplified array parsing
        try {
            json = json.trim();
            if (json.startsWith("[") && json.endsWith("]")) {
                json = json.substring(1, json.length() - 1);
                if (!json.isEmpty()) {
                    String[] items = json.split("\\},\\s*\\{");
                    for (String item : items) {
                        item = item.replaceAll("^\\{", "").replaceAll("\\}$", "");
                        list.add(parseJson("{" + item + "}"));
                    }
                }
            }
        } catch (Exception e) {
            // Simple parsing - return empty list on error
        }
        return list;
    }
}
