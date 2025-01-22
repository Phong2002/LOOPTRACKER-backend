package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.IncidentDto;
import com.looptracker.looptracker.entity.Incident;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncidentMapper extends BaseMapper<Incident, IncidentDto> {
}
