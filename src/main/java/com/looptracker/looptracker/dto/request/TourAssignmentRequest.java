package com.looptracker.looptracker.dto.request;

import lombok.Data;

@Data
public class TourAssignmentRequest {
    private String id;
    private Long tourInstances;
    private Long passenger;
    private String rider;
    private String licensePlates;
}
