package com.looptracker.looptracker.service;
import com.looptracker.looptracker.dto.request.AssignmentItemRequest;
import com.looptracker.looptracker.dto.request.ListAssignmentItem;
import com.looptracker.looptracker.entity.AssignmentItem;
import com.looptracker.looptracker.entity.Item;
import com.looptracker.looptracker.entity.TourAssignment;
import com.looptracker.looptracker.entity.enums.AssignmentStatus;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.repository.AssignmentItemRepository;
import com.looptracker.looptracker.repository.ITourAssignmentRepository;
import com.looptracker.looptracker.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AssignmentItemService implements IAssignmentItemService {
    @Autowired
    private AssignmentItemRepository assignmentItemRepository;

    @Autowired
    private ITourAssignmentRepository tourAssignmentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    @Transactional
    public void assignmentItemTo(AssignmentItemRequest assignmentItemRequest,String tourAssignmentId) {
        TourAssignment tourAssignment = tourAssignmentRepository.findById(tourAssignmentId).orElseThrow(
                () -> new CustomException(ErrorCode.TOUR_PACKAGE_ALREADY_EXISTS,"TourAssignment not found", HttpStatus.BAD_REQUEST)
        );

        Item item = itemRepository.findById(assignmentItemRequest.getItemId()).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND,"Item not found",HttpStatus.BAD_REQUEST)
        );

        AssignmentItem assignmentItem = new AssignmentItem();
        assignmentItem.setItem(item);
        assignmentItem.setTourAssignments(tourAssignment);
        assignmentItem.setQuantity(assignmentItemRequest.getQuantity());
        assignmentItem.setStatus(AssignmentStatus.BORROWED);
        assignmentItemRepository.save(assignmentItem);
    }

    @Override
    @Transactional
    public void updateAssignmentItem(AssignmentItemRequest assignmentItemRequest,String tourAssignmentId) {
        if(Objects.isNull(assignmentItemRequest.getId())){
            assignmentItemTo(assignmentItemRequest,tourAssignmentId);
        }
        else {
            AssignmentItem assignmentItem = assignmentItemRepository.findById(assignmentItemRequest.getId()).orElseThrow(
                    () -> new CustomException(ErrorCode.ASSIGNMENT_ITEM_NOT_FOUND, "Assignment Item Not Found", HttpStatus.NOT_FOUND)
            );
            if(Objects.equals(assignmentItem.getItem().getId(), assignmentItemRequest.getItemId()) && Objects.equals(assignmentItem.getQuantity(), assignmentItemRequest.getQuantity())){
                return;
            }
            if (!Objects.equals(assignmentItem.getItem().getId(), assignmentItemRequest.getItemId())) {
                Item item = itemRepository.findById(assignmentItemRequest.getItemId()).orElseThrow(
                        () -> new CustomException(ErrorCode.ITEM_NOT_FOUND, "Item Not Found", HttpStatus.NOT_FOUND)
                );
                assignmentItem.setItem(item);
            }
            if(!Objects.equals(assignmentItem.getQuantity(), assignmentItemRequest.getQuantity())){
                assignmentItem.setQuantity(assignmentItemRequest.getQuantity());
            }
            assignmentItemRepository.save(assignmentItem);
        }
    }

    @Override
    @Transactional
    public void deleteAssignmentItem(String assignmentId) {
        assignmentItemRepository.deleteById(assignmentId);
    }

    @Override
    @Transactional
    public void deleteListAssignmentItem(List<String> listAssignmentItem) {
        assignmentItemRepository.deleteAllById(listAssignmentItem);
    }

    @Override
    @Transactional
    public void returnedListAssignmentItem(ListAssignmentItem listAssignmentItem) {
        List<AssignmentItem> assignmentItemList = assignmentItemRepository.findAllById(listAssignmentItem.getListAssignmentId());
        assignmentItemList.forEach(a -> a.setStatus(AssignmentStatus.RETURNED));
    }
}
