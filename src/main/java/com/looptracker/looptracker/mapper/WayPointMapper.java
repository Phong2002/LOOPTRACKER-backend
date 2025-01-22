package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.WayPointDto;
import com.looptracker.looptracker.entity.WayPoint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WayPointMapper extends BaseMapper<WayPoint, WayPointDto> {
}
