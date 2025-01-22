package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.request.UserLocation;

import java.util.List;

public interface ILocationService {
    void updateUserLocation(UserLocation userLocation);
     List<UserLocation> getAllUserLocationByTourId(String tourId);
}