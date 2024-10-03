package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.ItemMovementDto;
import com.looptracker.looptracker.entity.ItemMovement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMovementMapper extends BaseMapper<ItemMovement, ItemMovementDto> {
}
