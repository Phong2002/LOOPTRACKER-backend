package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.NotificationDto;
import com.looptracker.looptracker.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper extends BaseMapper<Notification, NotificationDto> {
}
