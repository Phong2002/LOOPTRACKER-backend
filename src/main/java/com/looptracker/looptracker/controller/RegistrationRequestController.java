package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.RegistrationRequestDto;
import com.looptracker.looptracker.dto.request.SigninForm;
import com.looptracker.looptracker.repository.IRegistrationRequestRepository;
import com.looptracker.looptracker.service.IRegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration-request")
public class RegistrationRequestController {

    @Autowired
    private IRegistrationRequestService registrationRequestService;

    @Autowired
    private IRegistrationRequestRepository registrationRequestRepository;

    @PostMapping("easy-rider")
    public ResponseEntity<?> registrationRequest(@RequestBody RegistrationRequestDto registrationRequestDto) {
        registrationRequestService.registrationRequest(registrationRequestDto);
        return ResponseEntity.ok("Gửi yêu cầu thành công");
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody SigninForm login) {
        return null;
    }

    @PostMapping("confirm")
    public ResponseEntity<?> confirm(@RequestBody String requestId) {
        registrationRequestService.confirm(requestId);
        return ResponseEntity.ok("ok");

    }
}
