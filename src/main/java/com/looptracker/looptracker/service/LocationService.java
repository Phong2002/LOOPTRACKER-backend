package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.request.UserLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Override
    public void updateUserLocation(UserLocation userLocation) {
        String tourKey = "tour:" + userLocation.getTourInstanceId();
        String userKey = "user:" + userLocation.getUserId();

        // Thêm username vào Set của tourKey
        redisTemplate.opsForSet().add(tourKey, userLocation.getUserId());

        // Lưu thông tin chi tiết của user vào Hash
        Map<String, String> userMap = userLocation.toMap();
        redisTemplate.opsForHash().putAll(userKey, userMap);
    }

    @Scheduled(fixedRate = 10000)
    public void getAllUserLocations() {
        // Lấy tất cả các tourKey từ Redis
        Set<String> tourKeys = redisTemplate.keys("tour:*");
        Map<String, List<UserLocation>> groupedData = new HashMap<>();

        if (tourKeys != null && !tourKeys.isEmpty()) {
            for (String tourKey : tourKeys) {
                // Lấy tourInstanceId từ key
                String tourInstanceId = tourKey.substring("tour:".length());

                // Lấy danh sách username từ Set của tourKey
                Set<String> usernames = redisTemplate.opsForSet().members(tourKey);
                List<UserLocation> userLocations = new ArrayList<>();

                if (usernames != null && !usernames.isEmpty()) {
                    for (String username : usernames) {
                        String userKey = "user:" + username;

                        // Lấy dữ liệu Hash của user
                        Map<Object, Object> userHash = redisTemplate.opsForHash().entries(userKey);

                        if (userHash != null && !userHash.isEmpty()) {
                            // Chuyển đổi Hash sang UserLocation
                            Map<String, String> userMap = new HashMap<>();
                            userHash.forEach((key, value) -> userMap.put(key.toString(), value.toString()));

                            UserLocation userLocation = UserLocation.fromMap(userMap);
                            userLocations.add(userLocation);
                        }
                    }
                }

                // Thêm danh sách UserLocation vào Map theo tourInstanceId
                groupedData.put(tourInstanceId, userLocations);

                // Gửi danh sách UserLocation qua WebSocket
                messagingTemplate.convertAndSend("/topic/tracking-location/" + tourInstanceId, groupedData.get(tourInstanceId));
            }
        }

        // Hiển thị dữ liệu trong console
        groupedData.forEach((tourInstanceId, userLocations) -> {
            System.out.println("Tour ID: " + tourInstanceId);
            userLocations.forEach(userLocation -> System.out.println(userLocation));
        });
    }

    public List<UserLocation> getAllUserLocationByTourId(String tourId) {
        // Lấy danh sách username từ Set của tourKey
        String tourKey = "tour:" + tourId;
        Set<String> usernames = redisTemplate.opsForSet().members(tourKey);
        List<UserLocation> userLocations = new ArrayList<>();

        if (usernames != null && !usernames.isEmpty()) {
            for (String username : usernames) {
                String userKey = "user:" + username;

                // Lấy dữ liệu Hash của user
                Map<Object, Object> userHash = redisTemplate.opsForHash().entries(userKey);

                if (userHash != null && !userHash.isEmpty()) {
                    // Chuyển đổi Hash sang UserLocation
                    Map<String, String> userMap = new HashMap<>();
                    userHash.forEach((key, value) -> userMap.put(key.toString(), value.toString()));

                    UserLocation userLocation = UserLocation.fromMap(userMap);
                    userLocations.add(userLocation);
                }
            }
        }

        return userLocations;
    }
}
