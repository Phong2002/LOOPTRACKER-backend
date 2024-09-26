package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.PassengerDto;
import com.looptracker.looptracker.entity.Passenger;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassengerMapper extends BaseMapper<Passenger, PassengerDto> {
}
