package ui.core;

import java.util.*;

public class JSONParser {
    
    public static Map<String, Object> parse(String json) {
        json = json.trim();
        if (json.startsWith("{")) {
            return parseObject(json);
        } else if (json.startsWith("[")) {
            return Map.of("array", parseArray(json));
        }
        return new HashMap<>();
    }
    
    private static Map<String, Object> parseObject(String json) {
        Map<String, Object> map = new HashMap<>();
        json = json.substring(1, json.length() - 1).trim();
        
        if (json.isEmpty()) return map;
        
        int depth = 0;
        int start = 0;
        String key = null;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{' || c == '[') depth++;
            else if (c == '}' || c == ']') depth--;
            else if (c == '"' && depth == 0) {
                if (key == null) {
                    int end = json.indexOf('"', i + 1);
                    key = json.substring(i + 1, end);
                    i = end + 1;
                    while (i < json.length() && (json.charAt(i) == ' ' || json.charAt(i) == ':')) i++;
                    start = i;
                }
            } else if (c == ',' && depth == 0) {
                if (key != null) {
                    String valueStr = json.substring(start, i).trim();
                    map.put(key, parseValue(valueStr));
                    key = null;
                }
                start = i + 1;
            }
        }
        
        if (key != null) {
            String valueStr = json.substring(start).trim();
            map.put(key, parseValue(valueStr));
        }
        
        return map;
    }
    
    private static List<Object> parseArray(String json) {
        List<Object> list = new ArrayList<>();
        json = json.substring(1, json.length() - 1).trim();
        
        if (json.isEmpty()) return list;
        
        int depth = 0;
        int start = 0;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{' || c == '[') depth++;
            else if (c == '}' || c == ']') depth--;
            else if (c == ',' && depth == 0) {
                String itemStr = json.substring(start, i).trim();
                if (!itemStr.isEmpty()) {
                    list.add(parseValue(itemStr));
                }
                start = i + 1;
            }
        }
        
        String itemStr = json.substring(start).trim();
        if (!itemStr.isEmpty()) {
            list.add(parseValue(itemStr));
        }
        
        return list;
    }
    
    private static Object parseValue(String value) {
        value = value.trim();
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        } else if (value.equals("true")) {
            return true;
        } else if (value.equals("false")) {
            return false;
        } else if (value.equals("null")) {
            return null;
        } else if (value.contains(".")) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return value;
            }
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return Long.parseLong(value);
            }
        }
    }
}

