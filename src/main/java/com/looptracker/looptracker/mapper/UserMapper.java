package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.UserDto;
import com.looptracker.looptracker.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto> {
}
