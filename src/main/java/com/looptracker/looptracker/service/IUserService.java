package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.UserDto;
import com.looptracker.looptracker.dto.request.CreateUserFormRequest;
import com.looptracker.looptracker.dto.request.IdentificationImages;
import com.looptracker.looptracker.dto.request.UpdateInforRequest;
import com.looptracker.looptracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService extends UserDetailsService {
    User findByUsername(String username);
    List<UserDto> findAllTourGuide();
    List<UserDto> findAllRider();
    Page<UserDto> findAll(String search, Pageable pageable);
    void createAccount(CreateUserFormRequest createUserFormRequest, IdentificationImages identificationImages) throws Exception;
    void changePassword(String oldPassword, String newPassword,String userId);
    void updateInfor(UpdateInforRequest updateInforRequest,String userId);
    void forgotPassword(String email,String newPassword);
    void lockUserAccount(String id);
    void unLockUserAccount(String id);
    String updateAvatar(MultipartFile file, String userId) throws Exception;
}

