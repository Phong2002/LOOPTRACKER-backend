package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.PassengerDto;
import com.looptracker.looptracker.dto.response.PassengerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPassengerService {
    void addPassenger(PassengerDto passengerDto);
    void updatePassenger(PassengerDto passengerDto);
    void deletePassenger(String listPassengerId);
    Page<PassengerResponse> getPassengers(String search,String status,Pageable pageable);
    List<PassengerDto> getPassengersNoTour();
}
