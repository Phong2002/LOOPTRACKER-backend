package com.looptracker.looptracker.dto.request;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class UserLocation {
    private String userId;
    private String fullName;
    private double lat;
    private double lon;
    private String tourInstanceId;
    private String role;

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("fullName", fullName);
        map.put("lat", String.valueOf(lat));
        map.put("lon", String.valueOf(lon));
        map.put("tourInstanceId", tourInstanceId);
        map.put("role", role);
        return map;
    }

    public static UserLocation fromMap(Map<String, String> map) {
        UserLocation userLocation = new UserLocation();
        userLocation.setUserId(map.get("userId"));
        userLocation.setFullName(map.get("fullName"));
        userLocation.setLat(Double.parseDouble(map.get("lat")));
        userLocation.setLon(Double.parseDouble(map.get("lon")));
        userLocation.setTourInstanceId(map.get("tourInstanceId"));
        userLocation.setRole(map.get("role"));
        return userLocation;
    }
}