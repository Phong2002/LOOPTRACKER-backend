package com.looptracker.looptracker.dto.request;

import lombok.Data;

@Data
public class AssignmentItemRequest {
    private String id;
    private String tourAssignmentsId;
    private String itemId;
    private Integer quantity;
    private String status;
}
