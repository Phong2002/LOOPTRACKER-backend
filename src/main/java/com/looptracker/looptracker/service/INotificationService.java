package com.looptracker.looptracker.service;

import com.looptracker.looptracker.entity.enums.NotificationAction;

public interface INotificationService {
    void sendNotificationGlobal(String title,String content);
     void sendNotificationTourGroup(String title,String content,long tourInstanceId);
     void sendNotificationPrivate(String title, String content, String userId, NotificationAction action);
}
