package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.PassengerDto;
import com.looptracker.looptracker.entity.Passenger;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.mapper.PassengerMapper;
import com.looptracker.looptracker.repository.IPassengerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService implements IPassengerService {
    @Autowired
    IPassengerRepository passengerRepository;

    @Autowired
    PassengerMapper passengerMapper;

    @Override
    @Transactional
    public void addPassenger(PassengerDto passengerDto) {
        Passenger passenger = passengerMapper.toEntity(passengerDto);
        passengerRepository.save(passenger);
    }

    @Override
    @Transactional
    public void updatePassenger(PassengerDto passengerDto) {
        Passenger passenger = passengerRepository.findById(passengerDto.getId()).orElseThrow(
                ()-> new CustomException(404, "Passenger Not Found")
        );
        passengerMapper.toEntity(passengerDto, passenger);
        passengerRepository.save(passenger);
    }

    @Override
    @Transactional
    public void deletePassenger(List<String> listPassengerId) {
        for (String passengerId : listPassengerId) {
            passengerRepository.deleteById(Long.parseLong(passengerId));
        }
    }

    @Override
    public Page<PassengerDto> getPassengers(Pageable pageable) {
        Page<Passenger> passengers = passengerRepository.findAll(pageable);
        Page<PassengerDto> passengerDtos = passengerMapper.toPageDto(passengers);
        return passengerDtos;
    }
}
