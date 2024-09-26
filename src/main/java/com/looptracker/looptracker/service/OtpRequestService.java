package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.response.VerifyOtpResponse;
import com.looptracker.looptracker.entity.OtpRequest;
import com.looptracker.looptracker.entity.RegistrationRequest;
import com.looptracker.looptracker.entity.enums.RegistrationStatus;
import com.looptracker.looptracker.repository.IOtpRequestRepository;
import com.looptracker.looptracker.repository.IRegistrationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpRequestService implements IOtpRequestService {
    @Autowired
    private IOtpRequestRepository otpRequestRepository;

    @Autowired
    private IRegistrationRequestRepository registrationRequestRepository;

    @Override
    public VerifyOtpResponse verifyOtp(String email, String otp) {
        Optional<OtpRequest> optionalOtpRequest = Optional.ofNullable(otpRequestRepository.findByEmail(email));
        VerifyOtpResponse verifyOtpResponse = new VerifyOtpResponse();

        if (optionalOtpRequest.isEmpty()) {
            return verifyOtpResponse.builder().code(10001).success(false).message("Không tìm thấy OTP").build();
        }
        OtpRequest otpRequest = optionalOtpRequest.get();
        if (!otpRequest.getOtp().equals(otp)) {
            return verifyOtpResponse.builder().code(10002).success(false).message("Mã OTP không khớp").build();

        }
        if (LocalDateTime.now().isBefore(otpRequest.getExpiresAt())) {
            return verifyOtpResponse.builder().code(10003).success(false).message("Mã OTP hết hạn").build();
        }
        RegistrationRequest registrationRequest = registrationRequestRepository.findByEmail(email);
        if (registrationRequest == null) {
            return verifyOtpResponse.builder().code(10004).success(false).message("Không tìm thấy dữ liệu đăng ký").build();
        }
        registrationRequest.setStatus(RegistrationStatus.PENDING);
        registrationRequestRepository.save(registrationRequest);
        otpRequestRepository.delete(otpRequest);
        return verifyOtpResponse.builder().code(20000).success(true).message("Xác thực OTP thành công").build();
    }
}

