package com.looptracker.looptracker.mapper;

import com.looptracker.looptracker.dto.IncidentEvidenceDto;
import com.looptracker.looptracker.entity.IncidentEvidence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncidentEvidenceMapper extends BaseMapper<IncidentEvidence, IncidentEvidenceDto> {
}
