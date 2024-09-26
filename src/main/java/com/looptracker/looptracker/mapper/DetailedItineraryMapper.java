package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.DetailedItineraryDto;
import com.looptracker.looptracker.entity.DetailedItinerary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DetailedItineraryMapper extends BaseMapper<DetailedItinerary, DetailedItineraryDto> {
}
