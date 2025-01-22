package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.NotificationUserDto;
import com.looptracker.looptracker.entity.NotificationUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationUserMapper extends BaseMapper<NotificationUser, NotificationUserDto> {
}
