package com.looptracker.looptracker.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TourAssignmentRequest {
    private String id;
    private Long passenger;
    private String rider;
    private String licensePlates;
    private List<AssignmentItemRequest> listItems;
}
