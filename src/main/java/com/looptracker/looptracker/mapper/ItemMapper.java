package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.ItemDto;
import com.looptracker.looptracker.entity.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper extends BaseMapper<Item, ItemDto> {
}
