package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.PassengerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPassengerService {
    void addPassenger(PassengerDto passengerDto);
    void updatePassenger(PassengerDto passengerDto);
    void deletePassenger(List<String> listPassengerId);
    Page<PassengerDto> getPassengers(Pageable pageable);
}
