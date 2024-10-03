package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.request.AssignmentItemRequest;

public interface IAssignmentItemService {
    void assignmentItemTo(AssignmentItemRequest assignmentItemRequest);
    void updateAssignmentItem(AssignmentItemRequest assignmentItemRequest);
    void deleteAssignmentItem(AssignmentItemRequest assignmentItemRequest);
}
