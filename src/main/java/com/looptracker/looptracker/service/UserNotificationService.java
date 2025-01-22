package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.NotificationUserDto;
import com.looptracker.looptracker.entity.NotificationUser;
import com.looptracker.looptracker.mapper.NotificationUserMapper;
import com.looptracker.looptracker.repository.INotificationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserNotificationService implements IUserNotificationService{
    @Autowired
    INotificationUserRepository notificationUserRepository;
    @Autowired
    NotificationUserMapper notificationUserMapper;

    @Override
    @Transactional
    public Page<NotificationUserDto> getUserNotifications(String userId, Pageable pageable) {
        Page<NotificationUser> page = notificationUserRepository.findAllByUserId(userId,pageable);
        Page<NotificationUserDto> notificationUserDtoPage = notificationUserMapper.toPageDto(page);
        return notificationUserDtoPage;
    }

    @Override
    public void updateReadNotifications(String notificationUserId, boolean read) {

    }
}
