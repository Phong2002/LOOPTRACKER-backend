package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.PassengerDto;
import com.looptracker.looptracker.service.IPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/passenger")
public class PassengerController {
    @Autowired
    private IPassengerService passengerService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPassengers(Pageable pageable) {
        return ResponseEntity.ok(passengerService.getPassengers(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPassenger(@RequestBody PassengerDto passengerDto) {
        passengerService.addPassenger(passengerDto);
        return ResponseEntity.ok("Passenger created");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePassenger(@RequestBody PassengerDto passengerDto) {
        passengerService.updatePassenger(passengerDto);
        return ResponseEntity.ok("Passenger updated");
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deletePassenger(@RequestBody List<String> passengerDto) {
        passengerService.deletePassenger(passengerDto);
        return ResponseEntity.ok("Passenger deleted");
    }

}
