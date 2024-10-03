package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourAssignmentDto;
import com.looptracker.looptracker.dto.request.TourAssignmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITourAssignmentService {
    void addTourAssignment(TourAssignmentRequest tourAssignmentRequest);
    void updateTourAssignment(TourAssignmentRequest tourAssignmentRequest);
    void deleteTourAssignment(String id);
    Page<TourAssignmentDto> getAllTourAssignments(Pageable pageable);
    Page<TourAssignmentDto> getAllTourAssignmentsByTourInstance(Pageable pageable,Long tourInstanceId);
}
