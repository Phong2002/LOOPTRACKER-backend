package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.TourInstanceDto;
import com.looptracker.looptracker.entity.TourInstance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourInstanceMapper extends BaseMapper<TourInstance, TourInstanceDto> {
}
