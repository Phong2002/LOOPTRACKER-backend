package com.looptracker.looptracker.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class IncidentDto {
    private Long id;
    private String incidentType;
    private String description;
    private BigDecimal damageCost;
    private String latitude;
    private String longitude;
    private LocalDateTime incidentDate;
    private UserDto createdBy;
    private String tourAssignments;
    private String involvedRole;
    private List<IncidentEvidenceDto> incidentEvidences;
}
