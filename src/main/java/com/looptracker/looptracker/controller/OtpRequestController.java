package com.looptracker.looptracker.controller;


import com.looptracker.looptracker.dto.request.VerifyOtpRequest;
import com.looptracker.looptracker.entity.OtpRequest;
import com.looptracker.looptracker.service.IOtpRequestService;
import com.looptracker.looptracker.service.OtpRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpRequestController {
    @Autowired
    private IOtpRequestService otpRequestService;

    @PutMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) {
       return ResponseEntity.ok(otpRequestService.verifyOtp(verifyOtpRequest.getEmail(), verifyOtpRequest.getOtp()));
    }


}
