package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.RegistrationRequestDto;
public interface IRegistrationRequestService {
    void registrationRequest(RegistrationRequestDto registrationRequestDto);
    void confirm(String requestId);
}
