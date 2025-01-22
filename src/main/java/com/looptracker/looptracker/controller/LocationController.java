package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.request.UserLocation;
import com.looptracker.looptracker.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.List;

@RestController
@RequestMapping("api/v1/locations")
@EnableWebSocket
public class LocationController {
    @Autowired
    private ILocationService locationService;

    @MessageMapping("/updateLocation")
    public void updateLocation(@Payload UserLocation userLocation) {
        locationService.updateUserLocation(userLocation);
    }

    @GetMapping("persons/{tourId}")
    public ResponseEntity<List<UserLocation>> getAllUserLocationByTourId(@PathVariable String tourId) {
        List<UserLocation> userLocations = locationService.getAllUserLocationByTourId(tourId);
        return ResponseEntity.ok(userLocations);
    }
}