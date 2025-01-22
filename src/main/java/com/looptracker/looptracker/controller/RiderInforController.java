package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.request.RiderStatusRequest;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.security.service.UserDetailsImpl;
import com.looptracker.looptracker.service.IRiderInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/rider-infor")
public class RiderInforController {

    @Autowired
    private IRiderInforService riderInforService;

    @GetMapping("get")
    public ResponseEntity<?> getRiderInfor(@RequestParam String riderId) {
        return ResponseEntity.ok(riderInforService.getRiderInfor(riderId));
    }

    @PutMapping("update-rider-status")
    public ResponseEntity<?> updateRiderStatus(@RequestBody RiderStatusRequest riderStatusRequest) {
            riderInforService.updateRiderStatus(riderStatusRequest.getDriverStatus(),getCurrentUserId());
        return ResponseEntity.ok("update rider status success");
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







