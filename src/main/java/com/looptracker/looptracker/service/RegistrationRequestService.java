package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.request.RegistrationRequestDto;
import com.looptracker.looptracker.entity.OtpRequest;
import com.looptracker.looptracker.entity.RegistrationRequest;
import com.looptracker.looptracker.entity.RiderInfor;
import com.looptracker.looptracker.entity.User;
import com.looptracker.looptracker.entity.enums.RegistrationStatus;
import com.looptracker.looptracker.entity.enums.Role;
import com.looptracker.looptracker.event_listener.event.SendAccountInforEvent;
import com.looptracker.looptracker.event_listener.event.SendOtpEvent;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.mapper.RegistrationRequestMapper;
import com.looptracker.looptracker.repository.IOtpRequestRepository;
import com.looptracker.looptracker.repository.IRegistrationRequestRepository;
import com.looptracker.looptracker.repository.IRiderInforRepository;
import com.looptracker.looptracker.repository.IUserRepository;
import com.looptracker.looptracker.security.Authentication;
import com.looptracker.looptracker.utils.OTPGenerator;
import com.looptracker.looptracker.utils.PasswordGenerator;
import com.looptracker.looptracker.utils.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationRequestService implements IRegistrationRequestService {

    @Autowired
    private IRegistrationRequestRepository registrationRequestRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private RegistrationRequestMapper registrationRequestMapper;
    @Autowired
    private IRiderInforRepository riderInforRepository;
   @Autowired
   private ApplicationEventPublisher applicationEventPublisher;
   @Autowired
   private IOtpRequestRepository otpRequestRepository;
   @Autowired
   private Authentication authentication;
    @Override
    @Transactional
    public void registrationRequest(RegistrationRequestDto registrationRequestDto) {
        RegistrationRequest registrationRequest = registrationRequestMapper.toEntity(registrationRequestDto);
        if(riderInforRepository.existsByCitizenIdNumber(registrationRequest.getCitizenIdNumber())
                || registrationRequestRepository.existsByCitizenIdNumber(registrationRequest.getCitizenIdNumber())){
          throw new CustomException(400,"CCCD đã tồn tại");
        }
        if (riderInforRepository.existsByLicenseNumber(registrationRequest.getLicenseNumber())
                || registrationRequestRepository.existsByLicenseNumber(registrationRequest.getLicenseNumber())
        ){
            throw new CustomException(400,"GPLX đã tồn tại");
        }
        if (userRepository.existsByEmail(registrationRequest.getEmail())
        || registrationRequestRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new CustomException(400,"Email đã tồn tại");
        }
        if (userRepository.existsByPhoneNumber(registrationRequest.getPhoneNumber())
        || registrationRequestRepository.existsByPhoneNumber(registrationRequest.getPhoneNumber())) {
            throw new CustomException(400,"Số điện thoại đã tồn tại");
        }

        LocalDateTime now = LocalDateTime.now();
        registrationRequest.setStatus(RegistrationStatus.SEND_MAIL);
        registrationRequest.setCreateAt(now);
        registrationRequestRepository.save(registrationRequest);

        String otp = OTPGenerator.generateOTP();
        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setEmail(registrationRequest.getEmail());
        otpRequest.setOtp(otp);
        otpRequest.setCreateAt(now);
        otpRequest.setExpiresAt(now.plusMinutes(30));
        otpRequestRepository.save(otpRequest);

        Map<String, Object> model = new HashMap<>();
        model.put("fullName",String.join(" ",registrationRequest.getFirstName(), registrationRequest.getLastName()));
        model.put("otp", otp);
        applicationEventPublisher.publishEvent(new SendOtpEvent(this,registrationRequest.getEmail(),model));

    }

    @Override
    public void confirm(String requestId) {
         RegistrationRequest registrationRequest = registrationRequestRepository.findById(requestId).orElseThrow(
                ()-> new CustomException(400,"Không tìm thấy yêu cầu")
        );
        String cleanFirstName = StringUtils.removeAccent(registrationRequest.getFirstName()).toLowerCase();
        String cleanLastName = StringUtils.removeAccent(registrationRequest.getLastName()).toLowerCase();
        String username = generateUniqueUsername(generateUsername(cleanFirstName, cleanLastName));
        String password = PasswordGenerator.generatePassword(10);
        String passwordEncode = authentication.passwordEncoder().encode(password);

        User user = new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setUsername(username);
        user.setPassword(passwordEncode);
        user.setEmail(registrationRequest.getEmail());
        user.setPhoneNumber(registrationRequest.getPhoneNumber());
        user.setRole(Role.EASY_RIDER);
        user.setGender(registrationRequest.getGender());
        String userId = userRepository.saveAndFlush(user).getId();
        user.setId(userId);

        RiderInfor riderInfor = new RiderInfor();
        riderInfor.setUser(user);
        riderInfor.setCitizenIdNumber(registrationRequest.getCitizenIdNumber());
        riderInfor.setLicenseNumber(registrationRequest.getLicenseNumber());
        riderInforRepository.save(riderInfor);

        Map<String, Object> model = new HashMap<>();
        model.put("fullName",String.join(" ",registrationRequest.getFirstName(), registrationRequest.getLastName()));
        model.put("username", username);
        model.put("password", password);
        applicationEventPublisher.publishEvent(new SendAccountInforEvent(this,user.getEmail(),model));


    }

    private String generateUsername(String firstName, String lastName) {
        String[] lastNameParts = lastName.split(" ");
        StringBuilder initials = new StringBuilder();
        for (String part : lastNameParts) {
            initials.append(part.charAt(0));
        }
        return firstName + initials.toString();
    }
    private String generateUniqueUsername(String baseUsername) {
        int count = 1;
        String newUsername = baseUsername;
        while (userRepository.existsByUsername(newUsername)) {
            newUsername = baseUsername + count;
            count++;
        }
        return newUsername;
    }
}
