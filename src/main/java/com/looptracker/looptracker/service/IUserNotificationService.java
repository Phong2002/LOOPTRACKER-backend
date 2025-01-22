package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.NotificationUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserNotificationService {
    Page<NotificationUserDto> getUserNotifications(String userId, Pageable pageable);
    void updateReadNotifications(String notificationUserId, boolean read);
}
