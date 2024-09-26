package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.TourPackageDto;
import com.looptracker.looptracker.entity.TourPackage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TourPackageMapper extends BaseMapper<TourPackage, TourPackageDto>{
    @Mapping(source = "detailedItineraries", target = "detailedItineraryDtoList")
    TourPackageDto toDto(TourPackage tourPackage);

    @Mapping(source = "detailedItineraryDtoList", target = "detailedItineraries")
    TourPackage toEntity(TourPackageDto tourPackageDto);
}
