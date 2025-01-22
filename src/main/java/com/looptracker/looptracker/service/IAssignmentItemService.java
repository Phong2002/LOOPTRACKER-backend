package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.request.AssignmentItemRequest;
import com.looptracker.looptracker.dto.request.ListAssignmentItem;

import java.util.List;

public interface IAssignmentItemService {
    void assignmentItemTo(AssignmentItemRequest assignmentItemRequest,String tourAssignment);
    void updateAssignmentItem(AssignmentItemRequest assignmentItemRequest,String tourAssignment);
    void deleteAssignmentItem(String assignmentId);
    void deleteListAssignmentItem(List<String> listAssignmentItem);
    void returnedListAssignmentItem(ListAssignmentItem listAssignmentItem);
}
