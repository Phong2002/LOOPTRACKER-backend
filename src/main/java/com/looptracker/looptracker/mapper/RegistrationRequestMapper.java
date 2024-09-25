package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.request.RegistrationRequestDto;
import com.looptracker.looptracker.entity.RegistrationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationRequestMapper extends BaseMapper<RegistrationRequest, RegistrationRequestDto> {
}
