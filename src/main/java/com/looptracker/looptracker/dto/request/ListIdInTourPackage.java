package com.looptracker.looptracker.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ListIdInTourPackage {
    private List<String> tourAssignmentIds;
    private List<String> assignmentItemIds;
}
