package com.looptracker.looptracker.service;


import com.looptracker.looptracker.dto.RiderInforDto;
import com.looptracker.looptracker.dto.UserDto;
import com.looptracker.looptracker.dto.request.CreateUserFormRequest;
import com.looptracker.looptracker.dto.request.IdentificationImages;
import com.looptracker.looptracker.dto.request.UpdateInforRequest;
import com.looptracker.looptracker.entity.RiderInfor;
import com.looptracker.looptracker.entity.User;
import com.looptracker.looptracker.entity.enums.DriverStatus;
import com.looptracker.looptracker.entity.enums.Role;
import com.looptracker.looptracker.event_listener.event.SendAccountInforEvent;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.mapper.UserMapper;
import com.looptracker.looptracker.repository.IRegistrationRequestRepository;
import com.looptracker.looptracker.repository.IRiderInforRepository;
import com.looptracker.looptracker.repository.IUserRepository;
import com.looptracker.looptracker.security.Authentication;
import com.looptracker.looptracker.security.service.UserDetailsImpl;
import com.looptracker.looptracker.service.storage.IMinioService;
import com.looptracker.looptracker.utils.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IMinioService minioService;

    @Autowired
    private Authentication authentication;

    @Autowired
    private IRiderInforRepository riderInforRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private IRegistrationRequestRepository registrationRequestRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return new UserDetailsImpl(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

    @Override
    public List<UserDto> findAllTourGuide() {
        List<User> users = userRepository.findByRoleIn(List.of(Role.TOUR_GUIDE.toString()));
        return userMapper.toListDto(users);
    }

    @Override
    public List<UserDto> findAllRider() {
        List<User> users = userRepository.findByRoleIn(List.of(Role.TOUR_GUIDE.toString(),Role.EASY_RIDER.toString()));
        return userMapper.toListDto(users);
    }

    @Override
    public Page<UserDto> findAll(String search, Pageable pageable) {
        Page<User> users = userRepository.findAll(search, pageable);
        Page<UserDto> usersDto = userMapper.toPageDto(users);
        return usersDto;
    }

    @Override
    @Transactional
    public void createAccount(CreateUserFormRequest createUserFormRequest, IdentificationImages identificationImages) throws Exception {
        UserDto userDto = createUserFormRequest.getUser();
        if (userRepository.existsByEmail(userDto.getEmail())
                || registrationRequestRepository.existsByEmail(userDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS,"Email already exists",HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())
                || registrationRequestRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new CustomException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS,"Phone number already exists",HttpStatus.BAD_REQUEST);
        }

        String cleanFirstName = StringUtils.removeAccent(userDto.getFirstName()).toLowerCase();
        String cleanLastName = StringUtils.removeAccent(userDto.getLastName()).toLowerCase();
        String username = generateUniqueUsername(generateUsername(cleanFirstName, cleanLastName));
//        String password = PasswordGenerator.generatePassword(10);
        String password = String.valueOf('a');
        String passwordEncode = authentication.passwordEncoder().encode(password);

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(username);
        user.setPassword(passwordEncode);
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRole(userDto.getRole());
        user.setGender(userDto.getGender());
        user.setIsAccountNonLocked(true);
        String userId = userRepository.saveAndFlush(user).getId();
        user.setId(userId);

        if(user.getRole().equals(Role.TOUR_GUIDE)||user.getRole().equals(Role.EASY_RIDER)){
            RiderInforDto riderInforDto = createUserFormRequest.getRiderInfor();
            if(riderInforRepository.existsByCitizenIdNumber(riderInforDto.getCitizenIdNumber())
                    || registrationRequestRepository.existsByCitizenIdNumber(riderInforDto.getCitizenIdNumber())){
                throw new CustomException(ErrorCode.CCCD_ALREADY_EXISTS,"CCCD already exists", HttpStatus.BAD_REQUEST);
            }
            if (riderInforRepository.existsByLicenseNumber(riderInforDto.getLicenseNumber())
                    || registrationRequestRepository.existsByLicenseNumber(riderInforDto.getLicenseNumber())
            ){
                throw new CustomException(ErrorCode.GPLX_ALREADY_EXISTS,"GPLX already exists",HttpStatus.BAD_REQUEST);
            }
            RiderInfor riderInfor = new RiderInfor();
            riderInfor.setUser(user);
            riderInfor.setCitizenIdNumber(riderInforDto.getCitizenIdNumber());
            riderInfor.setLicenseNumber(riderInforDto.getLicenseNumber());
            riderInfor.setRiderStatus(DriverStatus.NOT_READY);
            riderInfor.setAddress(riderInforDto.getAddress());

            riderInfor.setCccdFront(processUploadMultipartFile("IdentificationImages","CccdFront",identificationImages.getCccdFrontImage()));
            riderInfor.setCccdBack(processUploadMultipartFile("IdentificationImages","CccdBack",identificationImages.getCccdBackImage()));
            riderInfor.setGplxFront(processUploadMultipartFile("IdentificationImages","GplxFront",identificationImages.getGplxFrontImage()));
            riderInfor.setGplxBack(processUploadMultipartFile("IdentificationImages","GplxBack",identificationImages.getGplxBackImage()));

            riderInforRepository.save(riderInfor);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("fullName",String.join(" ",userDto.getFirstName(), userDto.getLastName()));
        model.put("username", username);
        model.put("password", password);
        applicationEventPublisher.publishEvent(new SendAccountInforEvent(this,user.getEmail(),model));
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword,String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND,"User not found",HttpStatus.NOT_FOUND)
        );

        if(checkPassword(oldPassword,user.getPassword())){
            user.setPassword(authentication.passwordEncoder().encode(newPassword));
            userRepository.saveAndFlush(user);
        }
        else {
            throw new CustomException(ErrorCode.OLD_PASSWORD_IS_INCORRECT,"Old password is incorrect",HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void updateInfor(UpdateInforRequest updateInforRequest,String userId) {

        User user = userRepository.findById(userId).orElseThrow(
                ()-> new CustomException(ErrorCode.USER_NOT_FOUND,"User Not Found",HttpStatus.NOT_FOUND)
        );

        if(!user.getEmail().equals(updateInforRequest.getEmail())){
            if(userRepository.existsByEmail(updateInforRequest.getEmail())){
                throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS,"Email already exists",HttpStatus.BAD_REQUEST);
            }
        }

        if(!user.getPhoneNumber().equals(updateInforRequest.getPhoneNumber())){
            if(userRepository.existsByPhoneNumber(updateInforRequest.getPhoneNumber())){
                throw new CustomException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS,"Phone number already exists",HttpStatus.BAD_REQUEST);
            }
        }

        RiderInfor riderInfor = user.getRiderInfors();
        riderInfor.setAddress(updateInforRequest.getAddress());
        user.setEmail(updateInforRequest.getEmail());
        user.setPhoneNumber(updateInforRequest.getPhoneNumber());
        userRepository.saveAndFlush(user);
        riderInforRepository.save(riderInfor);
    }

    @Override
    @Transactional
    public void forgotPassword(String email, String newPassword) {

    }

    @Override
    @Transactional
    public void lockUserAccount(String id) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.USER_NOT_FOUND,"User not found", HttpStatus.NOT_FOUND)
        );
        user.setIsAccountNonLocked(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void unLockUserAccount(String id) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.USER_NOT_FOUND,"User not found", HttpStatus.NOT_FOUND)
        );
        user.setIsAccountNonLocked(true);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public String updateAvatar(MultipartFile file, String userId) throws Exception {
            User user = userRepository.findById(userId).orElseThrow(
                    ()-> new CustomException(ErrorCode.USER_NOT_FOUND,"User not found", HttpStatus.NOT_FOUND)
            );
            if(!Objects.isNull(user.getAvatarUrl()) && !user.getAvatarUrl().isEmpty()){
                minioService.deleteFile(user.getAvatarUrl());
            }
            String newImageUrl = processUploadMultipartFile("avatar",userId,file);
            user.setAvatarUrl(newImageUrl);
            userRepository.save(user);

            return newImageUrl;
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

    public String processUploadMultipartFile(String path,String type, MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID() +"-"+ type;
        String contentType = file.getContentType();
        InputStream fileStream = file.getInputStream();
        return minioService.uploadFile(path,fileName,fileStream,contentType);
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return authentication.passwordEncoder().matches(plainPassword, hashedPassword);
    }


}
