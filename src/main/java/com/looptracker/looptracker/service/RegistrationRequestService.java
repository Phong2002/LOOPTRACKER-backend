package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.RegistrationRequestDto;
import com.looptracker.looptracker.dto.request.IdentificationImages;
import com.looptracker.looptracker.entity.OtpRequest;
import com.looptracker.looptracker.entity.RegistrationRequest;
import com.looptracker.looptracker.entity.RiderInfor;
import com.looptracker.looptracker.entity.User;
import com.looptracker.looptracker.entity.enums.DriverStatus;
import com.looptracker.looptracker.entity.enums.RegistrationStatus;
import com.looptracker.looptracker.entity.enums.Role;
import com.looptracker.looptracker.event_listener.event.SendAccountInforEvent;
import com.looptracker.looptracker.event_listener.event.SendOtpEvent;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.mapper.RegistrationRequestMapper;
import com.looptracker.looptracker.repository.IOtpRequestRepository;
import com.looptracker.looptracker.repository.IRegistrationRequestRepository;
import com.looptracker.looptracker.repository.IRiderInforRepository;
import com.looptracker.looptracker.repository.IUserRepository;
import com.looptracker.looptracker.security.Authentication;
import com.looptracker.looptracker.service.storage.IMinioService;
import com.looptracker.looptracker.utils.OTPGenerator;
import com.looptracker.looptracker.utils.PasswordGenerator;
import com.looptracker.looptracker.utils.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private IMinioService minioService;


    @Override
    @Transactional
    public void registrationRequest(RegistrationRequestDto registrationRequestDto, IdentificationImages identificationImages) throws Exception {
        RegistrationRequest registrationRequest = registrationRequestMapper.toEntity(registrationRequestDto);
        if(riderInforRepository.existsByCitizenIdNumber(registrationRequest.getCitizenIdNumber())
                || registrationRequestRepository.existsByCitizenIdNumber(registrationRequest.getCitizenIdNumber())){
          throw new CustomException(ErrorCode.CCCD_ALREADY_EXISTS,"CCCD already exists", HttpStatus.BAD_REQUEST);
        }
        if (riderInforRepository.existsByLicenseNumber(registrationRequest.getLicenseNumber())
                || registrationRequestRepository.existsByLicenseNumber(registrationRequest.getLicenseNumber())
        ){
            throw new CustomException(ErrorCode.GPLX_ALREADY_EXISTS,"GPLX already exists",HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(registrationRequest.getEmail())
        || registrationRequestRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS,"Email already exists",HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByPhoneNumber(registrationRequest.getPhoneNumber())
        || registrationRequestRepository.existsByPhoneNumber(registrationRequest.getPhoneNumber())) {
            throw new CustomException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS,"Phone number already exists",HttpStatus.BAD_REQUEST);
        }

        registrationRequest.setCccdFront(processUploadMultipartFile("CccdFront",identificationImages.getCccdFrontImage()));
        registrationRequest.setCccdBack(processUploadMultipartFile("CccdBack",identificationImages.getCccdBackImage()));
        registrationRequest.setGplxFront(processUploadMultipartFile("GplxFront",identificationImages.getGplxFrontImage()));
        registrationRequest.setGplxBack(processUploadMultipartFile("GplxBack",identificationImages.getGplxBackImage()));

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
    @Transactional
    public void confirm(String requestId) {
         RegistrationRequest registrationRequest = registrationRequestRepository.findById(requestId).orElseThrow(
                ()-> new CustomException(ErrorCode.REGISTRATION_REQUEST_NOT_FOUND,"Registration request not found",HttpStatus.NOT_FOUND)
        );
        String cleanFirstName = StringUtils.removeAccent(registrationRequest.getFirstName()).toLowerCase();
        String cleanLastName = StringUtils.removeAccent(registrationRequest.getLastName()).toLowerCase();
        String username = generateUniqueUsername(generateUsername(cleanFirstName, cleanLastName));
//        String password = PasswordGenerator.generatePassword(10);
        String password = String.valueOf('a');
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
        riderInfor.setRiderStatus(DriverStatus.NOT_READY);
        riderInfor.setAddress(registrationRequest.getAddress());

        riderInfor.setCccdFront(registrationRequest.getCccdFront());
        riderInfor.setCccdBack(registrationRequest.getCccdBack());
        riderInfor.setGplxFront(registrationRequest.getGplxFront());
        riderInfor.setGplxBack(registrationRequest.getGplxBack());

        riderInforRepository.save(riderInfor);

        Map<String, Object> model = new HashMap<>();
        model.put("fullName",String.join(" ",registrationRequest.getFirstName(), registrationRequest.getLastName()));
        model.put("username", username);
        model.put("password", password);
        registrationRequest.setStatus(RegistrationStatus.APPROVED);
        registrationRequestRepository.save(registrationRequest);
        applicationEventPublisher.publishEvent(new SendAccountInforEvent(this,user.getEmail(),model));
    }

    @Override
    @Transactional
    public void reject(String requestId) {
        RegistrationRequest registrationRequest = registrationRequestRepository.findById(requestId).orElseThrow(
                ()-> new CustomException(ErrorCode.REGISTRATION_REQUEST_NOT_FOUND,"Registration request not found",HttpStatus.NOT_FOUND)
        );
        registrationRequest.setStatus(RegistrationStatus.REJECTED);
        registrationRequestRepository.save(registrationRequest);
    }

    @Override
    public void delete(String requestId) throws Exception {
        RegistrationRequest registrationRequest = registrationRequestRepository.findById(requestId).orElseThrow(
                ()-> new CustomException(ErrorCode.REGISTRATION_REQUEST_NOT_FOUND,"Registration request not found",HttpStatus.NOT_FOUND)
        );
        if(!registrationRequest.getStatus().equals(RegistrationStatus.APPROVED)){
            minioService.deleteFile(registrationRequest.getCccdFront());
            minioService.deleteFile(registrationRequest.getCccdBack());
            minioService.deleteFile(registrationRequest.getGplxFront());
            minioService.deleteFile(registrationRequest.getGplxBack());
        }
        registrationRequestRepository.deleteById(requestId);
    }

    @Override
    public void validateForm(String email, String phoneNumber, String cccdNumber, String gplxNumber) {
        if(riderInforRepository.existsByCitizenIdNumber(cccdNumber)
                || registrationRequestRepository.existsByCitizenIdNumber(cccdNumber)){
            throw new CustomException(ErrorCode.CCCD_ALREADY_EXISTS,"CCCD already exists", HttpStatus.BAD_REQUEST);
        }
        if (riderInforRepository.existsByLicenseNumber(gplxNumber)
                || registrationRequestRepository.existsByLicenseNumber(gplxNumber)
        ){
            throw new CustomException(ErrorCode.GPLX_ALREADY_EXISTS,"GPLX already exists",HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(email)
                || registrationRequestRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS,"Email already exists",HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByPhoneNumber(phoneNumber)
                || registrationRequestRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CustomException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS,"Phone number already exists",HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Page<RegistrationRequestDto> getRegistrationRequests(String search,String status,Pageable pageable) {
        List<String> listStatus = new ArrayList<>();
        if(Objects.isNull(status)||status.equals("")){
            listStatus.addAll(List.of("PENDING","APPROVED","REJECTED"));
        }
        else {
            listStatus.add(status);
        }
        Page<RegistrationRequest> registrationRequests = registrationRequestRepository.findAllByStatusIn(search,listStatus,pageable);
        return registrationRequestMapper.toPageDto(registrationRequests);
    }

    @Override
    public Integer getTotal() {
        return registrationRequestRepository.countPending();
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

    public String processUploadMultipartFile(String type, MultipartFile file) throws Exception {
        String path = "IdentificationImages";
        String fileName = UUID.randomUUID() +"-"+ type;
        String contentType = file.getContentType();
        InputStream fileStream = file.getInputStream();
        return minioService.uploadFile(path,fileName,fileStream,contentType);
    }
}
