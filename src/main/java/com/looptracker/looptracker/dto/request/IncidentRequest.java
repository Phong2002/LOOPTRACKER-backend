package com.looptracker.looptracker.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentRequest {
    private String incidentType;
    private String description;
    private BigDecimal damageCost;
    private String latitude;
    private String longitude;
    private String tourAssignments;
    private String involvedRole;
}