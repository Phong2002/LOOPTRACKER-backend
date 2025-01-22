package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.RegistrationRequestDto;
import com.looptracker.looptracker.dto.request.IdentificationImages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRegistrationRequestService {
    void registrationRequest(RegistrationRequestDto registrationRequestDto, IdentificationImages identificationImages) throws Exception;
    void confirm(String requestId);
    void reject(String requestId);
    void delete(String requestId) throws Exception;
    void validateForm(String email,String phoneNumber,String cccdNumber,String gplxNumber);
    Page<RegistrationRequestDto> getRegistrationRequests(String search,String status,Pageable pageable);
    Integer getTotal();
}
