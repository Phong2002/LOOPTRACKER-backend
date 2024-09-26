package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.TourPackageDto;
import com.looptracker.looptracker.entity.TourPackage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourPackageMapper extends BaseMapper<TourPackage, TourPackageDto>{
}
