package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.TourAssignmentDto;
import com.looptracker.looptracker.entity.TourAssignment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourAssignmentMapper extends BaseMapper<TourAssignment, TourAssignmentDto> {
}
