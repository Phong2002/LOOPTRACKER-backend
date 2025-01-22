package com.looptracker.looptracker.service;

import com.looptracker.looptracker.entity.Notification;
import com.looptracker.looptracker.entity.NotificationUser;
import com.looptracker.looptracker.entity.User;
import com.looptracker.looptracker.entity.enums.NotificationAction;
import com.looptracker.looptracker.entity.enums.NotificationType;
import com.looptracker.looptracker.entity.enums.Role;
import com.looptracker.looptracker.repository.INotificationRepository;
import com.looptracker.looptracker.repository.INotificationUserRepository;
import com.looptracker.looptracker.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    INotificationRepository notificationRepository;

    @Autowired
    IUserRepository userRepository;
    @Autowired
    INotificationUserRepository notificationUserRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Override

    @Transactional
    public void sendNotificationGlobal(String title, String content) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(content);
        notification.setActionKey(NotificationAction.NOTIFICATION);
        notification.setType(NotificationType.ALL);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.saveAndFlush(notification);
        List<User> users = userRepository.findAll();
        List<NotificationUser> notificationUsers = new ArrayList<>();
        for (User user : users) {
            if(!user.getRole().equals(Role.ADMIN)){
                NotificationUser notificationUser = new NotificationUser();
                notificationUser.setNotification(notification);
                notificationUser.setUserId(user.getId());
                notificationUser.setIsRead(false);
                notificationUsers.add(notificationUser);
            }
        }
        notificationUserRepository.saveAllAndFlush(notificationUsers);
        for(NotificationUser notificationUser : notificationUsers){
        messagingTemplate.convertAndSend("/queue/user." + notificationUser.getUserId(), notificationUser);
        }
    }

    @Override
    public void sendNotificationTourGroup(String title, String content, long tourInstanceId) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(content);
        notification.setActionKey(NotificationAction.NOTIFICATION);
        notification.setType(NotificationType.RIDER);
        notificationRepository.saveAndFlush(notification);
//        List<User> users = userRepository.findAll();
//        List<NotificationUser> notificationUsers = new ArrayList<>();
//        for (User user : users) {
//            if(!user.getRole().equals(Role.ADMIN)){
//                NotificationUser notificationUser = new NotificationUser();
//                notificationUser.setNotification(notification);
//                notificationUsers.add(notificationUser);
//            }
//        }
//        notificationUserRepository.saveAllAndFlush(notificationUsers);
//        for(NotificationUser notificationUser : notificationUsers){
//            messagingTemplate.convertAndSend("/queue/user." + notificationUser.getUserId(), notificationUser);
//        }
    }

    @Override
    public void sendNotificationPrivate(String title, String content, String userId, NotificationAction action) {

    }
}
