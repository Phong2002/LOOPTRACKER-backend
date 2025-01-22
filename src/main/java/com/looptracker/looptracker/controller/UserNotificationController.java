package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.service.IUserNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user-notifications")
public class UserNotificationController {
    @Autowired
    private IUserNotificationService userNotificationService;

    @GetMapping("get-all")
    ResponseEntity<?> getAll(@RequestParam String userId,
                             Pageable pageable){
        return ResponseEntity.ok(userNotificationService.getUserNotifications(userId,pageable));
    }

}
