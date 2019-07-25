package models;

import java.util.HashMap;

public class GlobalConfig {
    private HashMap<String, String> properties;

    public GlobalConfig() {
        properties = new HashMap<>();
    }

    public void addProperty(String propertyName, String value){
        properties.put(propertyName, value);
    }

    public String getProperty(String propertyName){
        return properties.get(propertyName);
    }
}
