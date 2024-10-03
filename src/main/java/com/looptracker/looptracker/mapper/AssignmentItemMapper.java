package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.AssignmentItemDto;
import com.looptracker.looptracker.entity.AssignmentItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssignmentItemMapper extends BaseMapper<AssignmentItem, AssignmentItemDto> {

}
