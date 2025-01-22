package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.PassengerDto;
import com.looptracker.looptracker.dto.response.PassengerResponse;
import com.looptracker.looptracker.entity.Passenger;
import com.looptracker.looptracker.entity.enums.Gender;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.mapper.PassengerMapper;
import com.looptracker.looptracker.repository.IPassengerRepository;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static kotlin.jvm.optionals.OptionalsKt.getOrDefault;

@Service
public class PassengerService implements IPassengerService {
    @Autowired
    private IPassengerRepository passengerRepository;

    @Autowired
    private PassengerMapper passengerMapper;

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
                () -> new CustomException(ErrorCode.PASSENGER_NOT_FOUND, "Passenger Not Found", HttpStatus.NOT_FOUND)
        );
        passengerMapper.toEntity(passengerDto, passenger);
        passengerRepository.save(passenger);
    }

    @Override
    @Transactional
    public void deletePassenger(String passengerId) {
            passengerRepository.deleteById(Long.valueOf(passengerId));

    }

    @Override
    public Page<PassengerResponse> getPassengers(String search, String status, Pageable pageable) {
        Page<Tuple> passengersPage = passengerRepository.findPassengersBySearchNameAndStatus(search,status,pageable);


        List<PassengerResponse> passengerResponses = passengersPage.stream()
                .map(tuple -> new PassengerResponse(
                                tuple.get("id", Long.class),
                                tuple.get("passenger_first_name", String.class),
                                tuple.get("passenger_last_name", String.class),
                                tuple.get("passenger_phone_number", String.class),
                                tuple.get("passenger_email", String.class),
                                tuple.get("passenger_notes", String.class),
                                tuple.get("passenger_gender", String.class),
                                tuple.get("status", String.class),
                                tuple.get("tour_package_name", String.class),
                                getOrDefault(tuple.get("tour_package_day", Integer.class), 0),
                                getOrDefault(tuple.get("tour_package_night", Integer.class), 0),
                                tuple.get("tour_package_description", String.class),
                                getLocalDate(tuple.get("start_date", java.sql.Date.class)),
                                getLocalDate(tuple.get("end_date", java.sql.Date.class)),
                                tuple.get("guide_first_name", String.class),
                                tuple.get("guide_last_name", String.class),
                                tuple.get("rider_first_name", String.class),
                                tuple.get("rider_last_name", String.class)
                        )
                )
                .collect(Collectors.toList());

        return new PageImpl<>(passengerResponses, pageable, passengersPage.getTotalElements());
    }

    private <T> T getOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
    private LocalDate getLocalDate(java.sql.Date sqlDate) {
        return sqlDate != null ? sqlDate.toLocalDate() : null;
    }

    @Override
    public List<PassengerDto> getPassengersNoTour() {
        List<Passenger> passengerList =  passengerRepository.findAllPassengerNoTour();
        return passengerMapper.toListDto(passengerList);
    }
}
