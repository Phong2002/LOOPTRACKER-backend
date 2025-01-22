package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourAssignmentDto;
import com.looptracker.looptracker.dto.request.TourAssignmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITourAssignmentService {
    void addTourAssignment(TourAssignmentRequest tourAssignmentRequest,Long tourInstanceId);
    void updateTourAssignment(TourAssignmentRequest tourAssignmentRequest,Long tourInstanceId);
    void deleteTourAssignment(String id);
    void deleteAllTourAssignments(List<String> ids);
    Page<TourAssignmentDto> getAllTourAssignments(Pageable pageable);
    Page<TourAssignmentDto> getAllTourAssignmentsByTourInstance(Pageable pageable,Long tourInstanceId);
    TourAssignmentDto getById(String id);
}
