package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.entity.Notification;
import com.looptracker.looptracker.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private INotificationService notificationService;

    public void sendGlobalNotification(Notification notification) {
        messagingTemplate.convertAndSend("/topic/public", notification);
    }

//    public void sendTourGroupNotification(Notification notification, Long tourId) {
//        notification.setTargetId(tourId);
//        messagingTemplate.convertAndSend("/topic/tour." + tourId, notification);
//    }
//
//    public void sendPrivateNotification(Notification notification, String userId) {
//        notification.setTargetId(Long.parseLong(userId));
//        messagingTemplate.convertAndSend("/queue/user." + userId, notification);
//    }

    // Endpoint để gửi thông báo thử nghiệm
    @PostMapping("/global")
    public ResponseEntity<?> sendTestNotification() {
        notificationService.sendNotificationGlobal("test thong bao"," demo thong bao 123");
            return  ResponseEntity.ok().build();
    }
}
