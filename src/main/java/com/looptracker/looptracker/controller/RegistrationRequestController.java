package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.request.RegistrationRequestDto;
import com.looptracker.looptracker.dto.request.SigninForm;
import com.looptracker.looptracker.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration-request")
public class RegistrationRequestController {

    @Autowired
    private RegistrationRequestService registrationRequestService;

    @PostMapping("/easy-rider")
    public ResponseEntity<?> registrationRequest(@RequestBody RegistrationRequestDto registrationRequestDto) {
        registrationRequestService.registrationRequest(registrationRequestDto);
        return ResponseEntity.ok("Gửi yêu cầu thành công");
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SigninForm login) {
        return null;
    }
}
