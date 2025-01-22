package com.looptracker.looptracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.looptracker.looptracker.dto.request.CreateUserFormRequest;
import com.looptracker.looptracker.dto.request.IdentificationImages;
import com.looptracker.looptracker.dto.request.UpdateInforRequest;
import com.looptracker.looptracker.dto.request.UpdatePasswordRequest;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.security.service.UserDetailsImpl;
import com.looptracker.looptracker.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private IUserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("create-account")
    public ResponseEntity<?> registrationRequest(@RequestPart("registrationRequestJson") String createUserFormRequest,
                                                 @RequestPart(value = "fileCCCDInputFront",required = false) MultipartFile fileCCCDInputFront,
                                                 @RequestPart(value ="fileCCCDInputBack",required = false) MultipartFile fileCCCDInputBack,
                                                 @RequestPart(value ="fileGPLXInputFront",required = false) MultipartFile fileGPLXInputFront,
                                                 @RequestPart(value ="fileGPLXInputBack",required = false) MultipartFile fileGPLXInputBack) throws Exception {

        IdentificationImages identificationImages = new IdentificationImages();
        identificationImages.setCccdBackImage(fileCCCDInputBack);
        identificationImages.setCccdFrontImage(fileCCCDInputFront);
        identificationImages.setGplxBackImage(fileGPLXInputBack);
        identificationImages.setGplxFrontImage(fileGPLXInputFront);

        CreateUserFormRequest createUserForm = objectMapper.readValue(createUserFormRequest, CreateUserFormRequest.class);
        userService.createAccount(createUserForm,identificationImages);
        return ResponseEntity.ok("Gửi yêu cầu thành công");
    }

     @PutMapping("update-infor")
        public ResponseEntity<?> updateUserInformation(@RequestBody UpdateInforRequest updateInforRequest){
        userService.updateInfor(updateInforRequest,getCurrentUserId());
        return ResponseEntity.ok("update profile successfully");
     }

     @PutMapping("update-avatar")
     public ResponseEntity<?> updateUserAvatar(@RequestPart(value = "avatar",required = false) MultipartFile avatar) throws Exception {
         return ResponseEntity.ok( userService.updateAvatar(avatar,getCurrentUserId()));
     }
     @PutMapping("update-password")
        public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest){
        userService.changePassword(updatePasswordRequest.getOldPassword(),updatePasswordRequest.getNewPassword(),getCurrentUserId());
        return ResponseEntity.ok("update password successfully");
     }

    @GetMapping("private/get-all")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false) String search, Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(search,pageable));
    }

    @PutMapping("lock")
    public ResponseEntity<?> lockUserAccount(@RequestPart("id") String id) {
        userService.lockUserAccount(id);
        return ResponseEntity.ok("Account is locked");
    }

    @PutMapping("unlock")
    public ResponseEntity<?> unLockUserAccount(@RequestPart("id") String id) {
        userService.unLockUserAccount(id);
        return ResponseEntity.ok("Account is unlocked");
    }

    @GetMapping("tour-guide/get")
    public ResponseEntity<?> getAllTourGuide() {
        return ResponseEntity.ok(userService.findAllTourGuide());
    }

    @GetMapping("rider/get")
    public ResponseEntity<?> getAllRider() {
        return ResponseEntity.ok(userService.findAllRider());
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getUserId();
        }
        else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND,"can't found user", HttpStatus.NOT_FOUND);
        }
    }
}
