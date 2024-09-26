package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.response.VerifyOtpResponse;

public interface IOtpRequestService {

    VerifyOtpResponse verifyOtp(String email, String otp);
}
